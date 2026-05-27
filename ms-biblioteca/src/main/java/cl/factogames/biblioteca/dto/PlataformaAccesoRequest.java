package cl.factogames.biblioteca.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlataformaAccesoRequest {

    @NotBlank(message = "El nombre de la plataforma no puede estar vacío")
    @Size(max = 30, message = "El nombre no puede superar los 30 caracteres")
    private String nombre;

    private Boolean requiereLauncher;
}
