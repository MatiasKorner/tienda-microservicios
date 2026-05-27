package cl.factogames.common.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OpinionCreatedEvent implements OpinionEvent{

    private Integer idOpinion;
    private String email;
    private String ean;
    private Integer calificacion;
    
}
