package cl.factogames.catalogo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import cl.factogames.catalogo.client.CarritoClient;
import cl.factogames.catalogo.dto.*;
import cl.factogames.catalogo.event.VideojuegoEventProducer;
import cl.factogames.common.event.VideojuegoDeletedEvent;
import cl.factogames.common.event.VideojuegoUpdatedEvent;
import cl.factogames.common.exception.*;
import cl.factogames.catalogo.mapper.VideojuegoMapper;
import cl.factogames.catalogo.model.*;
import cl.factogames.catalogo.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VideojuegoService {

    private final VideojuegoRepository videojuegoRepository;
    private final CategoriaRepository categoriaRepository;
    private final VideojuegoMapper videojuegoMapper;
    private final VideojuegoEventProducer videojuegoEventProducer;
    private final CarritoClient carritoClient;

    public List<VideojuegoResponse> findAll() {
        return videojuegoMapper.toResponseList(videojuegoRepository.findAll());
    }

    public VideojuegoResponse findById(long id) {
        return videojuegoMapper.toResponse(getVideojuegoById(id));
    }

    public VideojuegoResponse findByEan(String ean) {
        return videojuegoMapper.toResponse(getVideojuegoByEan(ean));
    }

    @Transactional
    public VideojuegoResponse create(VideojuegoRequest request) {
        validateEanUnico(request.getEan());
        Videojuego videojuego = new Videojuego(); 
        videojuegoMapper.updateEntity(request, videojuego);
        return videojuegoMapper.toResponse(videojuegoRepository.save(videojuego));
    }

    private void validateEanUnico(String ean) {
        videojuegoRepository.findByEan(ean).ifPresent(l -> { throw new DuplicateResourceException("Un Videojuego", "EAN", ean, l.getTitulo()); });
    }

    private Videojuego getVideojuegoById(long id) {
        return videojuegoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Videojuegos", "ID", id));  
    }

    private Videojuego getVideojuegoByEan(String ean) {
        return videojuegoRepository.findByEan(ean).orElseThrow(() -> new EntityNotFoundException("Videojuegos", "EAN", ean));  
    }

    private boolean checkMismoEan(Long id, String ean) {
        Videojuego videojuego = getVideojuegoById(id);
        return ean.equalsIgnoreCase(videojuego.getEan());
    }

    private Categoria getCategoriaById(long id) {
        return categoriaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Categorías", "ID", id));
    }

    public boolean existsByEan(String ean) {
        return videojuegoRepository.existsByEan(ean);
    }

    @Transactional
    public VideojuegoResponse update(Long id, VideojuegoRequest request) {
        if (!checkMismoEan(id, request.getEan())) validateEanUnico(request.getEan());
        Videojuego videojuego = new Videojuego();
        videojuegoMapper.updateEntity(request, videojuego);
        videojuegoRepository.save(videojuego);
        VideojuegoUpdatedEvent event = new VideojuegoUpdatedEvent(videojuego.getEan(), videojuego.getTitulo(), videojuego.getPrecio());
        videojuegoEventProducer.sendUpdated(event);
        return videojuegoMapper.toResponse(videojuego);
    }

    @Transactional
    public void deleteById(Long id) {
        Videojuego videojuego = getVideojuegoById(id);
        List<String> tablasAsociadas = new ArrayList<>();
        if (!videojuego.getCategorias().isEmpty()) tablasAsociadas.add("Categorías");
        if (carritoClient.existsByEan(videojuego.getEan())) tablasAsociadas.add("Recursos Físicos");
        if (!tablasAsociadas.isEmpty()) throw new ReferentialIntegrityException("Videojuegos", id, String.join(", ", tablasAsociadas));
        videojuegoRepository.delete(videojuego);
        VideojuegoDeletedEvent event = new VideojuegoDeletedEvent(videojuego.getEan());
        videojuegoEventProducer.sendDeleted(event);
    }

    @Transactional
    public void addCategoriaAVideojuego(Long videojuegoId, Long categoriaId) {
        Videojuego videojuego = getVideojuegoById(videojuegoId);
        Categoria categoria = getCategoriaById(categoriaId);
        if (!videojuego.getCategorias().contains(categoria)) {
            videojuego.getCategorias().add(categoria);
            videojuegoRepository.save(videojuego); 
        }
    }
}
