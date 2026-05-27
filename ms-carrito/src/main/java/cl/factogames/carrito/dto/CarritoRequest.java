package cl.factogames.carrito.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CarritoRequest {

    @NotBlank(message = "El email del usuario es obligatorio")
    @Email(message = "Debe proporcionar un formato de email válido")
    @Size(max = 100, message = "El email no puede superar los 100 caracteres")
    private String email;

}
