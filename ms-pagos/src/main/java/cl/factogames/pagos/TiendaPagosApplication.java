package cl.factogames.pagos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class TiendaPagosApplication {

	public static void main(String[] args) {
		SpringApplication.run(TiendaPagosApplication.class, args);
	}

}
