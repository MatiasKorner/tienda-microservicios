package cl.factogames.opinion.mapper;

import cl.factogames.opinion.dto.OpinionRequest;
import cl.factogames.opinion.dto.OpinionResponse;
import cl.factogames.opinion.model.ModeracionEstado;
import cl.factogames.opinion.model.Opinion;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OpinionMapper {

        @Mapping(target = "idOpinion", ignore = true)
        @Mapping(target = "estado", source = "idEstado", qualifiedByName = "mapEstado")
        @Mapping(target = "votos", ignore = true)
        Opinion toEntity(OpinionRequest request);

        @Mapping(target = "estadoDescripcion", source = "estado.descripcion")
        @Mapping(target = "totalVotosUtiles", ignore = true)
        OpinionResponse toResponse(Opinion entity);

        @Mapping(target = "idEstado", source = "estado.idEstado")
        OpinionRequest toRequestDto(Opinion entity);

        List<OpinionResponse> toResponseList(List<Opinion> entities);

        @Named("mapEstado")
        default ModeracionEstado mapEstado(Integer idEstado) {
            if (idEstado == null) {
                return ModeracionEstado.builder().idEstado(1).build();
            }
            return ModeracionEstado.builder().idEstado(idEstado).build();
        }

}
