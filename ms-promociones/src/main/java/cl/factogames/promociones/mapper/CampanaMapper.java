package cl.factogames.promociones.mapper;

import cl.factogames.promociones.dto.CampanaRequest;
import cl.factogames.promociones.dto.CampanaResponse;
import cl.factogames.promociones.dto.CuponRequest;
import cl.factogames.promociones.dto.CuponResponse;
import cl.factogames.promociones.model.AplicacionPromo;
import cl.factogames.promociones.model.Campana;
import cl.factogames.promociones.model.Cupon;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CampanaMapper {

    @Mapping(target = "idCampana", ignore = true)
    @Mapping(target = "aplicaciones", ignore = true)
    Campana toEntity(CampanaRequest request);

    @Mapping(target = "idCampana", source = "idCampana")
    @Mapping(target = "estaVigente", expression = "java(isVigente(entity))")
    @Mapping(target = "juegosAsociados", source = "aplicaciones", qualifiedByName = "mapAplicacionesToIds")
    CampanaResponse toResponse(Campana entity);

    Cupon toCuponEntity(CuponRequest request);
    CuponResponse toCuponResponse(Cupon entity);

    List<CampanaResponse> toResponseList(List<Campana> entities);

    default boolean isVigente(Campana entity) {
        if (entity == null || entity.getFechaInicio() == null || entity.getFechaFin() == null) {
            return false;
        }
        LocalDate hoy = LocalDate.now();
        return !hoy.isBefore(entity.getFechaInicio()) && !hoy.isAfter(entity.getFechaFin());
    }

    @Named("mapAplicacionesToIds")
    default List<String> mapAplicacionesToIds(List<AplicacionPromo> aplicaciones) {
        if (aplicaciones == null) {
            return Collections.emptyList();
        }
        return aplicaciones.stream().map(AplicacionPromo::getEan).collect(Collectors.toList());
    }

}
