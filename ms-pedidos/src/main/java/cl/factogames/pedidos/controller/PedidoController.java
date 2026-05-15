package cl.factogames.pedidos.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import cl.factogames.pedidos.dto.PedidoRequest;
import cl.factogames.pedidos.dto.PedidoResponse;
import cl.factogames.pedidos.service.PedidoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    @GetMapping
    public ResponseEntity<List<PedidoResponse>> findAll() {
        return ResponseEntity.ok(pedidoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponse> findById(@PathVariable @NonNull Integer id) {
        return ResponseEntity.ok(pedidoService.findById(id));
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<PedidoResponse>> findByUsuarioId(@PathVariable Integer idUsuario) {
        return ResponseEntity.ok(pedidoService.findByUsuarioId(idUsuario));
    }

    @GetMapping("/seguimiento/{codigo}")
    public ResponseEntity<PedidoResponse> findByCodigo(@PathVariable String codigo) {
        return ResponseEntity.ok(pedidoService.findByCodigoSeguimiento(codigo));
    }

    @PostMapping
    public ResponseEntity<PedidoResponse> create(@Valid @RequestBody PedidoRequest request) {
        PedidoResponse creado = pedidoService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PedidoResponse> update(
            @PathVariable @NonNull Integer id,
            @Valid @RequestBody PedidoRequest request) {
        return ResponseEntity.ok(pedidoService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable @NonNull Integer id) {
        pedidoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
