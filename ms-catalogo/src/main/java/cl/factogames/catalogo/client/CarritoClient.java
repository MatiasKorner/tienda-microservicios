package cl.factogames.catalogo.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-carrito")
public interface CarritoClient {

    @GetMapping("/api/v1/carrito/ean/{ean}")
    boolean existsByEan(@PathVariable String ean);

}
