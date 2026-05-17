package cl.factogames.pedidos.client;

import cl.factogames.pedidos.dto.StockProductoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "ms-inventario")
public interface InventarioClient {

    @GetMapping("/api/v1/stocks/juego/{idJuego}")
    List<StockProductoResponse> findByIdJuego(@PathVariable Integer idJuego);
}