package cl.factogames.opinion.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import cl.factogames.opinion.dto.VideojuegoResponse;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;

@FeignClient(name = "ms-catalogo")
public interface CatalogoClient {

    @GetMapping("/api/v1/videojuegos/ean/{ean}")
    VideojuegoResponse findByEan(@PathVariable("ean") @NotBlank String ean);
}
