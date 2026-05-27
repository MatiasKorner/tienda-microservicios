package cl.factogames.biblioteca.client;

import cl.factogames.biblioteca.dto.UsuarioResponse;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name ="ms-usuarios")
public interface UsuarioClient {

    @GetMapping("/api/v1/usuarios/email/{email}")
    UsuarioResponse obtenUsuarioPorEmail(@PathVariable String email);
}
