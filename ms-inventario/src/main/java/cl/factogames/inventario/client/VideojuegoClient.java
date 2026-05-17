package cl.factogames.inventario.client;

import cl.factogames.inventario.dto.VideojuegoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-catalogo")
public interface VideojuegoClient {

    @GetMapping("/api/v1/videojuegos/{id}")
    VideojuegoResponse findById(@PathVariable Integer id);
}