package cl.factogames.biblioteca.client;

import cl.factogames.biblioteca.dto.VideojuegoResponse;
import jakarta.validation.constraints.NotNull;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-catalogo")
public interface CatalogoClient {

    @GetMapping("/api/v1/videojuegos/{ean}")
    VideojuegoResponse obtenerVideojuegoPorEan(@PathVariable @NotNull String ean);
}
