package cl.factogames.inventario.dto;

import lombok.Data;

public class InventarioResponse {

    private Integer id;
    private Integer idVideojuego;
    private String nombreVideojuego;
    private Integer stock;
    private Integer stockMinimo;
    private Boolean disponible;

}
