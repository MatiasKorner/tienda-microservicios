package cl.factogames.inventario;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@EnableFeignClients(basePackages = "cl.factogames.inventario.client")
@SpringBootApplication
public class TiendaInventarioApplication {

	public static void main(String[] args) {
		SpringApplication.run(TiendaInventarioApplication.class, args);
	}

}
