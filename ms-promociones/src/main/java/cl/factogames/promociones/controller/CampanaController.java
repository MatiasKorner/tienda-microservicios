package cl.factogames.promociones.controller;

import cl.factogames.promociones.dto.CampanaRequest;
import cl.factogames.promociones.dto.CampanaResponse;
import cl.factogames.promociones.dto.CuponRequest;
import cl.factogames.promociones.dto.CuponResponse;
import cl.factogames.promociones.service.CampanaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/promociones")
@RequiredArgsConstructor
public class CampanaController {

    private final CampanaService campanaService;

    @PostMapping("/campanas")
    public ResponseEntity<CampanaResponse> crearCampana(@Valid @RequestBody CampanaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(campanaService.crearCampana(request));
    }

    @PostMapping("/cupones")
    public ResponseEntity<CuponResponse> crearCupon(@Valid @RequestBody CuponRequest request) {
        return ResponseEntity.ok(campanaService.crearCupon(request));
    }

    @GetMapping("/cupones/{codigo}")
    public ResponseEntity<CuponResponse> validarCupon(@PathVariable String codigo) {
        return ResponseEntity.ok(campanaService.buscarCupon(codigo));
    }

    @GetMapping("/vigentes")
    public ResponseEntity<List<CampanaResponse>> listarVigentes() {
        List<CampanaResponse> lista = campanaService.listarVigentes();
        return lista.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(lista);
    }

    @DeleteMapping("/campanas/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        campanaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
