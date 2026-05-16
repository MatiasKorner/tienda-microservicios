package cl.factogames.pagos.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.factogames.pagos.dto.TransaccionRequest;
import cl.factogames.pagos.dto.TransaccionResponse;
import cl.factogames.pagos.service.TransaccionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/transacciones")
public class TransaccionController {

    private final TransaccionService transaccionService;

    @GetMapping
    public ResponseEntity<List<TransaccionResponse>> findAll() {

        return ResponseEntity.ok(transaccionService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransaccionResponse> findById(
            @PathVariable @NonNull UUID id) {

        return ResponseEntity.ok(transaccionService.findById(id));
    }

    @GetMapping("/pedido/{idPedido}")
    public ResponseEntity<List<TransaccionResponse>> findByPedido(
            @PathVariable Integer idPedido) {

        return ResponseEntity.ok(
                transaccionService.findByPedido(idPedido));
    }

    @PostMapping
    public ResponseEntity<TransaccionResponse> create(
            @Valid @RequestBody TransaccionRequest request) {

        TransaccionResponse creada =
                transaccionService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(creada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransaccionResponse> update(
            @PathVariable @NonNull UUID id,
            @Valid @RequestBody TransaccionRequest request) {

        return ResponseEntity.ok(
                transaccionService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(
            @PathVariable @NonNull UUID id) {

        transaccionService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}