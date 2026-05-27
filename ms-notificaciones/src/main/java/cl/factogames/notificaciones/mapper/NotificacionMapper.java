package cl.factogames.notificaciones.mapper;

import cl.factogames.notificaciones.dto.NotificacionRequest;
import cl.factogames.notificaciones.dto.NotificacionResponse;
import cl.factogames.notificaciones.model.Notificacion;
import cl.factogames.notificaciones.model.Plantilla;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NotificacionMapper {

    @Mapping(target = "idNotificacion", ignore = true)
    @Mapping(target = "fechaEnvio", ignore = true)
    @Mapping(target = "plantilla", source = "idPlantilla", qualifiedByName = "mapPlantillaById")
    Notificacion toEntity(NotificacionRequest request);

    @Mapping(target = "idNotificacion", source = "idNotificacion")
    @Mapping(target = "asuntoBase", source = "plantilla.asuntoBase")
    @Mapping(target = "cuerpoBase", source = "plantilla.cuerpoBase")
    @Mapping(target = "nombreCanal", source = "plantilla.canal.nombre")
    @Mapping(target = "fechaEnvio", source = "fechaEnvio")
    @Mapping(target = "leido", source = "leido")
    @Mapping(target = "detalleNotificacion", source = "entity")
    NotificacionResponse toResponse(Notificacion entity);

    List<NotificacionResponse> toResponseList(List<Notificacion> lista);

    @Mapping(target = "email", source = "email")
    @Mapping(target = "idPlantilla", source = "plantilla.idPlantilla")
    NotificacionRequest toRequestDto(Notificacion entity);

    @Named("mapPlantillaById")
    default Plantilla mapPlantillaById(Integer idPlantilla) {
        if (idPlantilla == null) return null;
        Plantilla plantilla = new Plantilla();
        plantilla.setIdPlantilla(idPlantilla);
        return plantilla;
    }
}
