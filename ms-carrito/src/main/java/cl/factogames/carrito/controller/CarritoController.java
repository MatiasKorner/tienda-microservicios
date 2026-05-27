package cl.factogames.carrito.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cl.factogames.carrito.dto.CarritoRequest;
import cl.factogames.carrito.dto.CarritoResponse;
import cl.factogames.carrito.dto.ItemCarritoRequest;
import cl.factogames.carrito.dto.UsuarioProyeccionRequest;
import cl.factogames.carrito.dto.VideojuegoProyeccionRequest;
import cl.factogames.carrito.service.CarritoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/carritos")
public class CarritoController {

    private final CarritoService carritoService;

    // =========================================================
    // CARRITO
    // =========================================================

    // Obtiene el carrito ACTIVO de un usuario
    // GET /api/v1/carritos/activo?email=user@mail.com
    @GetMapping("/activo")
    public ResponseEntity<CarritoResponse> findCarritoActivo(@RequestParam String email) {
        return ResponseEntity.ok(carritoService.findCarritoActivo(email));
    }

    // Historial completo de carritos de un usuario
    // GET /api/v1/carritos/historial?email=user@mail.com
    @GetMapping("/historial")
    public ResponseEntity<List<CarritoResponse>> findHistorialByUsuario(@RequestParam String email) {
        return ResponseEntity.ok(carritoService.findHistorialByUsuario(email));
    }

    // Obtiene un carrito específico por su ID
    // GET /api/v1/carritos/{id}
    @GetMapping("/{id}")
    public ResponseEntity<CarritoResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(carritoService.findById(id));
    }

    // Crea un carrito ACTIVO para un usuario (solo necesita el email)
    // POST /api/v1/carritos
    @PostMapping
    public ResponseEntity<CarritoResponse> crearCarrito(@Valid @RequestBody CarritoRequest request) {
        CarritoResponse creado = carritoService.crearCarrito(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    // Cambia el estado del carrito (ej: ACTIVO → ABANDONADO o CONVERTIDO)
    // PUT /api/v1/carritos/{id}/estado/{nombreEstado}
    @PutMapping("/{id}/estado/{nombreEstado}")
    public ResponseEntity<CarritoResponse> cambiarEstado(
            @PathVariable Long id,
            @PathVariable String nombreEstado) {
        return ResponseEntity.ok(carritoService.cambiarEstado(id, nombreEstado));
    }

    // Vacía todos los ítems del carrito sin eliminarlo
    // DELETE /api/v1/carritos/{id}/items
    @DeleteMapping("/{id}/items")
    public ResponseEntity<Void> vaciarCarrito(@PathVariable Long id) {
        carritoService.vaciarCarrito(id);
        return ResponseEntity.noContent().build();
    }

    // =========================================================
    // ÍTEMS DEL CARRITO
    // =========================================================

    // Agrega un videojuego al carrito (o incrementa cantidad si ya existe)
    // POST /api/v1/carritos/{id}/items
    @PostMapping("/{id}/items")
    public ResponseEntity<CarritoResponse> agregarItem(
            @PathVariable Long id,
            @Valid @RequestBody ItemCarritoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(carritoService.agregarItem(id, request));
    }

    // Actualiza la cantidad de un ítem específico
    // PUT /api/v1/carritos/{id}/items/{ean}
    @PutMapping("/{id}/items/{ean}")
    public ResponseEntity<CarritoResponse> actualizarCantidadItem(
            @PathVariable Long id,
            @PathVariable String ean,
            @Valid @RequestBody ItemCarritoRequest request) {
        return ResponseEntity.ok(carritoService.actualizarCantidadItem(id, ean, request));
    }

    // Elimina un ítem específico del carrito por su EAN
    // DELETE /api/v1/carritos/{id}/items/{ean}
    @DeleteMapping("/{id}/items/{ean}")
    public ResponseEntity<Void> eliminarItem(
            @PathVariable Long id,
            @PathVariable String ean) {
        carritoService.eliminarItem(id, ean);
        return ResponseEntity.noContent().build();
    }

    // =========================================================
    // SINCRONIZACIÓN DE PROYECCIONES (llamadas desde otros microservicios)
    // =========================================================

    // Sincroniza o actualiza un usuario en la proyección local
    // POST /api/v1/carritos/proyecciones/usuarios
    @PostMapping("/proyecciones/usuarios")
    public ResponseEntity<Void> sincronizarUsuario(@Valid @RequestBody UsuarioProyeccionRequest request) {
        carritoService.sincronizarUsuario(request);
        return ResponseEntity.noContent().build();
    }

    // Sincroniza o actualiza un videojuego en la proyección local
    // POST /api/v1/carritos/proyecciones/videojuegos
    @PostMapping("/proyecciones/videojuegos")
    public ResponseEntity<Void> sincronizarVideojuego(@Valid @RequestBody VideojuegoProyeccionRequest request) {
        carritoService.sincronizarVideojuego(request);
        return ResponseEntity.noContent().build();
    }
}