package cl.factogames.inventario.dto;

import lombok.Data;

@Data
public class StockProductoResponse {

    private String ean;
    private Integer idJuego;
    private Integer idAlmacen;
    private String nombreAlmacen;
    private Integer cantidad;
}