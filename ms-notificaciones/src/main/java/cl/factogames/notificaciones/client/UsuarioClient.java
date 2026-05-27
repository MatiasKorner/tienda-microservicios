package cl.factogames.notificaciones.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import cl.factogames.notificaciones.dto.UsuarioResponse;

@FeignClient(name = "ms-usuarios")
public interface UsuarioClient {

    @GetMapping("/api/v1/usuarios/email/{email}")
    UsuarioResponse findByEmail(@PathVariable String email);
;
}
