package cl.factogames.common.event;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioCreatedEvent implements UsuarioEvent {
    private String email;
    private String nombre;
    private String apellido;
    private String rol;
}