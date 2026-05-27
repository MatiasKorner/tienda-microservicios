package cl.factogames.biblioteca.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlataformaAccesoResponse {

    private Integer idPlataforma;
    private String nombre;
    private Boolean requiereLauncher;
}
