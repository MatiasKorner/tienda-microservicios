package cl.factogames.carrito.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class CarritoResponse {

    private Long id;
    private UsuarioProyeccionResponse usuario;
    private String estadoNombre;
 
    private LocalDateTime fechaCreacion;
    private LocalDateTime ultimaActualizacion;
 
    private List<ItemCarritoResponse> items;
 
    // Campo calculado: suma de todos los subtotales
    private BigDecimal total;
}