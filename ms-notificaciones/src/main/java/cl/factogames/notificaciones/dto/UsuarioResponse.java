package cl.factogames.notificaciones.dto;

import lombok.Data;

@Data
public class UsuarioResponse {

    private Long id;
    private String nombre;
    private String apellido;
    private String email;
}