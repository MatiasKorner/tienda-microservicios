package cl.factogames.opinion.service;

import cl.factogames.opinion.dto.OpinionRequest;
import cl.factogames.opinion.dto.OpinionResponse;
import cl.factogames.opinion.mapper.OpinionMapper;
import cl.factogames.opinion.model.Opinion;
import cl.factogames.opinion.repository.OpinionRepository;
import cl.factogames.opinion.repository.VotoUtilidadRepository;
import cl.factogames.opinion.client.CatalogoClient;
import cl.factogames.opinion.client.UsuarioClient;
import cl.factogames.opinion.dto.VideojuegoResponse;
import cl.factogames.opinion.dto.UsuarioResponse;
import cl.factogames.opinion.event.OpinionEventProducer;
import cl.factogames.common.event.OpinionCreatedEvent;
import cl.factogames.common.event.OpinionDeletedEvent;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OpinionService {

    private final OpinionRepository opinionRepository;
    private final VotoUtilidadRepository votoUtilidadRepository;
    private final OpinionMapper mapper;

    private final CatalogoClient catalogoClient;
    private final UsuarioClient usuarioClient;

    private final OpinionEventProducer opinionEventProducer;

    @Transactional
    public OpinionResponse crearOpinion(OpinionRequest request) {

        // Validación:  ¿Existe el usuario que intenta dejar la reseña?
        try {
            UsuarioResponse usuario = usuarioClient.findByEmail((request.getEmail()));
            if (usuario == null) {
                throw new RuntimeException("El usuario con email " + request.getEmail() + " no existe");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error de validación: El usuario no existe o el servicio de usuarios está caído.");
        }

        // Validación:  ¿Existe el videojuego en la tienda?
        try {
            VideojuegoResponse juego = catalogoClient.findByEan(request.getEan());
            if (juego == null) {
                throw new RuntimeException("El videojuego con EAN " + request.getEan() + " no existe.");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error de validación: El videojuego no existe o el servicio de catálogo no responde.");
        }

        Opinion opinion = mapper.toEntity(request);
        Opinion opinionGuardada = opinionRepository.save(opinion);
        OpinionResponse response = mapper.toResponse(opinionGuardada);

        if(response.getEstadoDescripcion() == null) {
            response.setEstadoDescripcion("PENDIENTE");
        }

        response.setTotalVotosUtiles(0);

        OpinionCreatedEvent event = OpinionCreatedEvent.builder()
                .idOpinion(opinionGuardada.getIdOpinion())
                .ean(opinionGuardada.getEan())
                .calificacion(opinionGuardada.getCalificacion())
                .build();
        
        opinionEventProducer.sendCreated(event);

        return response;
    }


    @Transactional(readOnly = true)
    public List<OpinionResponse> listarPorJuego(String ean) {
        List<Opinion> entities = opinionRepository.findByEan(ean);
        return entities.stream().map(opinion -> {
            OpinionResponse response = mapper.toResponse(opinion);
            long votos = votoUtilidadRepository.countByOpinionIdOpinionAndEsUtilTrue(opinion.getIdOpinion());
            response.setTotalVotosUtiles((int) votos);
            return response;
        })
        .collect(Collectors.toList());
    }


    @Transactional
    public void eliminarOpinion(Integer idOpinion) {
        if (!opinionRepository.existsById(idOpinion)) {
            throw new RuntimeException("No se encontró la opinion con el ID: " + idOpinion);
        }
        opinionRepository.deleteById(idOpinion);

        OpinionDeletedEvent event = OpinionDeletedEvent.builder()
                .idOpinion(idOpinion)
                .build();

        opinionEventProducer.sendDeleted(event);
    }

}
