package cl.factogames.carrito.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import cl.factogames.carrito.dto.UsuarioProyeccionRequest;
import cl.factogames.carrito.dto.UsuarioProyeccionResponse;
import cl.factogames.carrito.model.UsuarioProyeccion;

@Mapper(componentModel = "spring")
public interface UsuarioProyeccionMapper {

    UsuarioProyeccion toEntity(UsuarioProyeccionRequest request);

    UsuarioProyeccionResponse toResponse(UsuarioProyeccion usuario);

    List<UsuarioProyeccionResponse> toResponseList(List<UsuarioProyeccion> usuarios);

    // El email es PK, no se actualiza
    @Mapping(target = "email", ignore = true)
    void updateEntity(UsuarioProyeccionRequest request, @MappingTarget UsuarioProyeccion usuario);
}