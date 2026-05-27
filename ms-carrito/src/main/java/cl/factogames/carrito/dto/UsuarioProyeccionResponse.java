package cl.factogames.carrito.dto;

import lombok.Data;

@Data
public class UsuarioProyeccionResponse {

    private String email;
    private String nombre;
    private String apellido;
    private String rol;
}
