package cl.factogames.inventario;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = "cl.factogames.inventario")
@EnableFeignClients(basePackages = "cl.factogames.inventario.client")
public class TiendaInventarioApplication {

    public static void main(String[] args) {
        SpringApplication.run(TiendaInventarioApplication.class, args);
    }
}