package cl.factogames.catalogo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import cl.factogames.catalogo.dto.VideojuegoRequest;
import cl.factogames.catalogo.dto.VideojuegoResponse;
import cl.factogames.common.exception.*;
import cl.factogames.catalogo.mapper.VideojuegoMapper;
import cl.factogames.catalogo.model.Categoria;
import cl.factogames.catalogo.model.Videojuego;
import cl.factogames.catalogo.repository.CategoriaRepository;
import cl.factogames.catalogo.repository.VideojuegoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VideojuegoService {

    private final VideojuegoRepository videojuegoRepository;
    private final CategoriaRepository categoriaRepository;
    private final VideojuegoMapper videojuegoMapper;

    public List<VideojuegoResponse> findAll() {
        return videojuegoMapper.toResponseList(videojuegoRepository.findAll());
    }

    public VideojuegoResponse findById(long id) {
        return videojuegoMapper.toResponse(getVideojuegoById(id));
    }

    public VideojuegoResponse findByTitulo(String titulo) {
        return videojuegoMapper.toResponse(getVideojuegoByTitulo(titulo));
    }

    @Transactional
    public VideojuegoResponse create(VideojuegoRequest request) {
        Videojuego videojuego = new Videojuego(); 
        videojuegoMapper.updateEntity(request, videojuego);
        return videojuegoMapper.toResponse(videojuegoRepository.save(videojuego));
    }

    private Videojuego getVideojuegoById(long id) {
        return videojuegoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Videojuegos", "ID", id));  
    }

    private Videojuego getVideojuegoByTitulo(String titulo) {
        return videojuegoRepository.findByTitulo(titulo).orElseThrow(() -> new EntityNotFoundException("Videojuegos", "TITULO", titulo));  
    }

    private Categoria getCategoriaById(long id) {
        return categoriaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Categorías", "ID", id));
    }

    public boolean existsByTitulo(String titulo) {
        return videojuegoRepository.existsByTitulo(titulo);
    }

    @Transactional
    public VideojuegoResponse update(Long id, VideojuegoRequest request) {
        Videojuego videojuego = new Videojuego();
        videojuegoMapper.updateEntity(request, videojuego);
        return videojuegoMapper.toResponse(videojuegoRepository.save(videojuego));
    }

    @Transactional
    public void deleteById(Long id) {
        Videojuego videojuego = getVideojuegoById(id);
        List<String> tablasAsociadas = new ArrayList<>();
        if (!videojuego.getCategorias().isEmpty()) tablasAsociadas.add("Categorías");
        if (!tablasAsociadas.isEmpty()) throw new ReferentialIntegrityException("Videojuegos", id, String.join(", ", tablasAsociadas));
        videojuegoRepository.delete(videojuego);
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
