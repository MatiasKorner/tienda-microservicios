package cl.factogames.pedidos.client;

import cl.factogames.pedidos.dto.UsuarioResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-usuarios")
public interface UsuarioClient {

    @GetMapping("/api/v1/usuarios/{email}")
    UsuarioResponse findByEmail(@PathVariable String email);
}