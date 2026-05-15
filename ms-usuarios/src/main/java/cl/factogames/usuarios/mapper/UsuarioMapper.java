package cl.factogames.usuarios.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import cl.factogames.usuarios.dto.UsuarioRequest;
import cl.factogames.usuarios.dto.UsuarioResponse;
import cl.factogames.usuarios.model.Usuario;

import java.util.List;


@Mapper(componentModel = "spring")
public interface UsuarioMapper {
    @Mapping(target = "id",         ignore = true)
    @Mapping(target = "activo",     ignore = true)
    @Mapping(target = "perfil",     ignore = true)
    @Mapping(target = "rol",     ignore = true)
    Usuario toEntity(UsuarioRequest request);

    @Mapping(source = "perfil.nombre", target = "nombre")
    @Mapping(source = "perfil.apellido", target = "apellido")
    @Mapping(source = "rol.nombreRol", target = "nombreRol")
    UsuarioResponse toResponse(Usuario ususario);

    List<UsuarioResponse> toResponseList(List<Usuario> usuario);

    @Mapping(target = "id",         ignore = true)
    @Mapping(target = "activo",     ignore = true)
    @Mapping(target = "perfil",     ignore = true)
    @Mapping(target = "rol",     ignore = true)
    void updateEntity(UsuarioRequest request, @MappingTarget  Usuario usuario);
}
