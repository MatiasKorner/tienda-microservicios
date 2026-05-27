package cl.factogames.notificaciones;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@EnableFeignClients(basePackages = "cl.factogames.notificaciones.client")
@SpringBootApplication
public class TiendaNotificacionesApplication {

	public static void main(String[] args) {
		SpringApplication.run(TiendaNotificacionesApplication.class, args);
	}

}
