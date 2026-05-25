package cl.factogames.catalogo.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import cl.factogames.catalogo.dto.VideojuegoRequest;
import cl.factogames.catalogo.dto.VideojuegoResponse;
import cl.factogames.catalogo.model.Videojuego;

@Mapper(componentModel = "spring")
public interface VideojuegoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "categorias", ignore = true)
    Videojuego toEntity(VideojuegoRequest request);

    VideojuegoResponse toResponse(Videojuego videojuego);

    List<VideojuegoResponse> toResponseList(List<Videojuego> videojuegos);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "categorias", ignore = true)
    void updateEntity(VideojuegoRequest request, @MappingTarget Videojuego videojuego);
}
