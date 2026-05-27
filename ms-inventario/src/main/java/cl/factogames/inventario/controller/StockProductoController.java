package cl.factogames.inventario.controller;

import cl.factogames.inventario.dto.StockProductoRequest;
import cl.factogames.inventario.dto.StockProductoResponse;
import cl.factogames.inventario.service.StockProductoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/stocks")
@RequiredArgsConstructor
public class StockProductoController {

    private final StockProductoService stockProductoService;

    @GetMapping
    public ResponseEntity<List<StockProductoResponse>> findAll() {
        return ResponseEntity.ok(stockProductoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StockProductoResponse> findById(@PathVariable Long id) {
    return ResponseEntity.ok(stockProductoService.findById(id));
}

    @GetMapping("/ean/{ean}")
    public ResponseEntity<List<StockProductoResponse>> findByVideojuegoEan(@PathVariable String ean) {
        return ResponseEntity.ok(stockProductoService.findByVideojuegoEan(ean));
    }

    @GetMapping("/almacen/{idAlmacen}")
    public ResponseEntity<List<StockProductoResponse>> findByAlmacenId(@PathVariable Integer idAlmacen) {
        return ResponseEntity.ok(stockProductoService.findByAlmacenId(idAlmacen));
    }

    @PostMapping
    public ResponseEntity<StockProductoResponse> create(@Valid @RequestBody StockProductoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(stockProductoService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StockProductoResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody StockProductoRequest request) {
        return ResponseEntity.ok(stockProductoService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        stockProductoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}