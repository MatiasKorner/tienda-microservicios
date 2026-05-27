package cl.factogames.biblioteca.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JuegoPoseidoRequest {

    @NotBlank(message = "El email del usuario es obligatorio")
    @Size(max = 100, message = "El email no puede superar los 100 caracteres")
    private String email;

    @NotBlank(message = "El código EAN del juego es obligatorio")
    @Size(min = 10, max = 13, message = "El código EAN debe tener entre 10 y 13 caracteres")
    private String ean;

}
