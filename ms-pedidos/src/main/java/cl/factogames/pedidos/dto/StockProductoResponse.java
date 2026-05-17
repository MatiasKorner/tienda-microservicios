package cl.factogames.pedidos.dto;

import lombok.Data;

@Data
public class StockProductoResponse {

    private Integer idStock;
    private Integer idJuego;
    private Integer idAlmacen;
    private String nombreAlmacen;
    private Integer cantidad;
}