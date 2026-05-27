package cl.factogames.biblioteca.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LicenciaActivaResponse {

    private Integer idLicencia;
    private String claveActivacion;
    private LocalDateTime activadaEn;
    private PlataformaAccesoResponse plataforma;
}
