package cl.factogames.inventario.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StockProductoRequest {

    @NotNull(message = "El id del juego es obligatorio")
    private Integer idJuego;

    @NotNull(message = "El id del almacén es obligatorio")
    private Integer idAlmacen;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 0, message = "La cantidad no puede ser negativa")
    private Integer cantidad;
}