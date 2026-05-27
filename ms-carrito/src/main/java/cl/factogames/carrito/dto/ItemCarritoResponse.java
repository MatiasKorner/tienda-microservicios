package cl.factogames.carrito.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ItemCarritoResponse {

    private Long id;
    private VideojuegoProyeccionResponse videojuego;
    private Integer cantidad;
    private BigDecimal precioUnitarioMomento;
 
    // Campo calculado: cantidad * precioUnitarioMomento
    private BigDecimal subtotal;
    
}
