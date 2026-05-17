package cl.factogames.pagos.client;

import cl.factogames.pagos.dto.PedidoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-pedidos")
public interface PedidoClient {

    @GetMapping("/api/v1/pedidos/{id}")
    PedidoResponse findById(@PathVariable Integer id);
}