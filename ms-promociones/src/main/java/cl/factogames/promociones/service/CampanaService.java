package cl.factogames.promociones.service;

import cl.factogames.promociones.dto.CampanaRequest;
import cl.factogames.promociones.dto.CampanaResponse;
import cl.factogames.promociones.dto.CuponRequest;
import cl.factogames.promociones.dto.CuponResponse;
import cl.factogames.promociones.mapper.CampanaMapper;
import cl.factogames.promociones.model.AplicacionPromo;
import cl.factogames.promociones.model.Campana;
import cl.factogames.promociones.model.Cupon;
import cl.factogames.promociones.repository.CampanaRepository;
import cl.factogames.promociones.repository.CuponRepository;
import cl.factogames.common.exception.EntityNotFoundException;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CampanaService {

    private final CampanaRepository campanaRepository;
    private final CampanaMapper campanaMapper;
    private final CuponRepository cuponRepository;

    @Transactional
    public CampanaResponse crearCampana(CampanaRequest request) {

        if (!request.tieneFechasValidas()) {
        throw new IllegalArgumentException("La fecha de fin debe ser posterior a la fecha de inicio.");
        }

        Campana campana = campanaMapper.toEntity(request);

        if (request.getEansJuegos() != null && !request.getEansJuegos().isEmpty()) {
            List<AplicacionPromo> aplicaciones = new ArrayList<>();
            for (String eanJuego : request.getEansJuegos()) {
                AplicacionPromo promo = AplicacionPromo.builder().ean(eanJuego).campana(campana).prioridad(1).build();
                aplicaciones.add(promo);
            }
            campana.setAplicaciones(aplicaciones);
        }

        return campanaMapper.toResponse(campanaRepository.save(campana));
    }

    @Transactional(readOnly = true)
    public List<CampanaResponse> listarVigentes(){
        return campanaMapper.toResponseList(campanaRepository.findVigentes(LocalDate.now()));
    }

    @Transactional
    public CuponResponse crearCupon(CuponRequest request) {
        Cupon cupon = campanaMapper.toCuponEntity(request);

        if (cupon.getCodigoAlfa() != null) {
            cupon.setCodigoAlfa(cupon.getCodigoAlfa().toUpperCase().trim());
        }

        if (cupon.getEsActivo() == null) {
            cupon.setEsActivo(true);
        }

        return campanaMapper.toCuponResponse(cuponRepository.save(cupon));

    }

    @Transactional(readOnly = true)
    public CuponResponse buscarCupon(String codigo) {
        return cuponRepository.findByCodigoAlfa(codigo.toUpperCase().trim()).map(campanaMapper::toCuponResponse)
            .orElseThrow(() -> new EntityNotFoundException("Cupón", "código", codigo));
    }

    @Transactional
    public void eliminar(Integer id) {
        if (!campanaRepository.existsById(id)) {
            throw new EntityNotFoundException("Campaña", "ID: ", id);
        }
        campanaRepository.deleteById(id);
    }
}