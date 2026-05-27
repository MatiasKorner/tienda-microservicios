package cl.factogames.opinion.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import cl.factogames.opinion.dto.UsuarioResponse;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;

@FeignClient(name = "ms-usuarios")
public interface UsuarioClient {

    @GetMapping("/api/v1/usuarios/email/{email}")
    UsuarioResponse findByEmail(@PathVariable("email") @NotBlank String email);
}
