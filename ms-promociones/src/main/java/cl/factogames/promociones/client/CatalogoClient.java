package cl.factogames.promociones.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import cl.factogames.promociones.dto.VideojuegoResponse;
import jakarta.validation.constraints.NotNull;

@FeignClient(name = "ms-catalogo")
public interface CatalogoClient {

    @GetMapping("/api/v1/videojuegos/{ean}")
    VideojuegoResponse findByEan(@PathVariable @NotNull String ean);
}
