package cl.factogames.biblioteca.event;

import cl.factogames.biblioteca.dto.JuegoPoseidoRequest;
import cl.factogames.biblioteca.service.BibliotecaService;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class BibliotecaConsumerEvent {

    private final BibliotecaService bibliotecaService;
    private final ObjectMapper objectMapper;

    @KafkaListener(
        topics = "transaccion-completada", 
        groupId = "ms-biblioteca-group"
    )
    public void escucharTransaccionCompra(String mensajeJson) {
        log.info(" [Kafka Event] Recibido evento de compra en ms-biblioteca: {}", mensajeJson);
        
        try {
            Map<?, ?> payload = objectMapper.readValue(mensajeJson, Map.class);
            
            String email = (String) payload.get("email");
            String ean = (String) payload.get("ean");

            if (email == null || ean == null) {
                log.warn(" Payload incompleto. Faltan datos esenciales (idUsuario o idJuego).");
                return;
            }

            log.info(" Procesando asignación asíncrona: Usuario {} ➔ Juego {}", email, ean);

            JuegoPoseidoRequest request = JuegoPoseidoRequest.builder()
                    .email(email)
                    .ean(ean)
                    .build();

            bibliotecaService.registrarAdquisicion(request);
            log.info(" Propiedad de videojuego registrada de forma automatizada.");

        } catch (RuntimeException e) {
            log.warn(" Validación de negocio en evento: {}", e.getMessage());
        } catch (Exception e) {
            log.error(" Error crítico en el procesamiento del mensaje de Kafka: {}", e.getMessage(), e);
        }
    }
}
