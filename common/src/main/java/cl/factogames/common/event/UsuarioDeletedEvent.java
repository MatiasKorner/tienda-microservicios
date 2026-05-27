package cl.factogames.common.event;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioDeletedEvent implements UsuarioEvent {
    private String email;
}