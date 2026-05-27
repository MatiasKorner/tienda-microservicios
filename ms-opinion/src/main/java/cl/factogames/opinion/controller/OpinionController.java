package cl.factogames.opinion.controller;

import cl.factogames.opinion.dto.OpinionRequest;
import cl.factogames.opinion.dto.OpinionResponse;
import cl.factogames.opinion.service.OpinionService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import java.util.List;

@RestController
@RequestMapping("/api/v1/opinion")
@RequiredArgsConstructor
public class OpinionController {

    private final OpinionService opinionService;

    @PostMapping
    public ResponseEntity<OpinionResponse> crear(@Valid @RequestBody OpinionRequest request) {
        
        return ResponseEntity.ok(
            opinionService.crearOpinion(request));
    }

    @GetMapping("/juego/{ean}")
    public ResponseEntity<List<OpinionResponse>> listarPorJuego(@PathVariable String ean) {
        List<OpinionResponse> opiniones = opinionService.listarPorJuego(ean);
        return ResponseEntity.ok(opiniones);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        opinionService.eliminarOpinion(id);
        return ResponseEntity.noContent().build();
    }
}
