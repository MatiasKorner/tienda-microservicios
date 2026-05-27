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
public class OpinionUpdateEvent implements OpinionEvent{

    private Integer idOpinion;
    private Integer calificacion;

}
