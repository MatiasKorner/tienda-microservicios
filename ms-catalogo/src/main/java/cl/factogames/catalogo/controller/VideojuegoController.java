package cl.factogames.catalogo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.factogames.catalogo.dto.VideojuegoRequest;
import cl.factogames.catalogo.dto.VideojuegoResponse;
import cl.factogames.catalogo.service.VideojuegoService;
import io.micrometer.common.lang.NonNull;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/videojuegos")
public class VideojuegoController {

    private final VideojuegoService videojuegoService;

    @GetMapping
    public ResponseEntity<List<VideojuegoResponse>> findAll() {
        return ResponseEntity.ok(videojuegoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VideojuegoResponse> findById(@PathVariable @NonNull Long id) {
        return ResponseEntity.ok(videojuegoService.findById(id));
    }

    @GetMapping("/ean/{ean}")
    public ResponseEntity<VideojuegoResponse> findByEan(@PathVariable String ean) {
        return ResponseEntity.ok(videojuegoService.findByEan(ean));
    }

    @PostMapping
    public ResponseEntity<VideojuegoResponse> create(@Valid @RequestBody VideojuegoRequest request) {
        VideojuegoResponse creado = videojuegoService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VideojuegoResponse> update(
            @PathVariable @NonNull Long id,
            @Valid @RequestBody VideojuegoRequest request) {
        return ResponseEntity.ok(videojuegoService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable @NonNull Long id) {
        videojuegoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/videojuego/{videojuego_id}/categoria/{categoria_id}")
    public void addCategoriaAVideojuego(@PathVariable Long videojuego_id, @PathVariable Long categoria_id) {
        videojuegoService.addCategoriaAVideojuego(categoria_id, videojuego_id);
    }

    @GetMapping("/existe/ean/{ean}")
    public ResponseEntity<Boolean> existByEan(@PathVariable String ean) {
        return ResponseEntity.ok(videojuegoService.existsByEan(ean));
    }
    
}
