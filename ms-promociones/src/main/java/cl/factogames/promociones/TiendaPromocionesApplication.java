package cl.factogames.promociones;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


@EnableDiscoveryClient
@EnableFeignClients(basePackages = "cl.factogames.promociones.client")
@SpringBootApplication
public class TiendaPromocionesApplication {

	public static void main(String[] args) {
		SpringApplication.run(TiendaPromocionesApplication.class, args);
	}

}
