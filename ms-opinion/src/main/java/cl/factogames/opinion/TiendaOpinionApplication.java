package cl.factogames.opinion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@EnableFeignClients(basePackages = "cl.factogames.opinion.client")
@SpringBootApplication
public class TiendaOpinionApplication {

	public static void main(String[] args) {
		SpringApplication.run(TiendaOpinionApplication.class, args);
	}

}
