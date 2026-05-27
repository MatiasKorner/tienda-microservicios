package cl.factogames.biblioteca.mapper;

import cl.factogames.biblioteca.dto.*;
import cl.factogames.biblioteca.model.JuegoPoseido;
import cl.factogames.biblioteca.model.LicenciaActiva;
import cl.factogames.biblioteca.model.PlataformaAcceso;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BibliotecaMapper {

    PlataformaAccesoResponse toResponse(PlataformaAcceso entity);
    PlataformaAcceso toEntity(PlataformaAccesoRequest request);
    List<PlataformaAccesoResponse> toPlataformaResponseList(List<PlataformaAcceso> entities);

    LicenciaActivaResponse toResponse(LicenciaActiva entity);

    @Mapping(target = "idLicencia", ignore = true)
    @Mapping(target = "juegoPoseido", ignore = true)
    @Mapping(target = "plataforma", ignore = true)
    @Mapping(target = "activadaEn", ignore = true)
    LicenciaActiva toEntity(LicenciaActivaRequest request);
    List<LicenciaActivaResponse> toLicenciaResponseList(List<LicenciaActiva> entities);

    JuegoPoseidoResponse toResponse(JuegoPoseido entity);

    @Mapping(target = "idPropiedad", ignore = true)
    @Mapping(target = "fechaAdquisicion", ignore = true)
    @Mapping(target = "horasJugadas", ignore = true)
    @Mapping(target = "licencias", ignore = true)
    JuegoPoseido toEntity(JuegoPoseidoRequest request);
    List<JuegoPoseidoResponse> toJuegoPoseidoResponseList(List<JuegoPoseido> entities);
}
