package cl.factogames.carrito.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import cl.factogames.carrito.dto.VideojuegoProyeccionRequest;
import cl.factogames.carrito.dto.VideojuegoProyeccionResponse;
import cl.factogames.carrito.model.VideojuegoProyeccion;

@Mapper(componentModel = "spring")
public interface VideojuegoProyeccionMapper {

    VideojuegoProyeccion toEntity(VideojuegoProyeccionRequest request);

    VideojuegoProyeccionResponse toResponse(VideojuegoProyeccion videojuego);

    List<VideojuegoProyeccionResponse> toResponseList(List<VideojuegoProyeccion> videojuegos);

    // El EAN es PK, no se actualiza
    @Mapping(target = "ean", ignore = true)
    void updateEntity(VideojuegoProyeccionRequest request, @MappingTarget VideojuegoProyeccion videojuego);
}