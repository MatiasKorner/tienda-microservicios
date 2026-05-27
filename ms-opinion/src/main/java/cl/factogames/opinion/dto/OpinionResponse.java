package cl.factogames.opinion.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OpinionResponse {

    private Integer idOpinion;
    private String ean;
    private String email;
    private Integer calificacion;
    private String comentario;
    private String estadoDescripcion;
    private Integer totalVotosUtiles;
}
