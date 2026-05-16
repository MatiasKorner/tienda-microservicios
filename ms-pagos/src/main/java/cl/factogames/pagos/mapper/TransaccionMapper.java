package cl.factogames.pagos.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import cl.factogames.pagos.dto.TransaccionRequest;
import cl.factogames.pagos.dto.TransaccionResponse;
import cl.factogames.pagos.model.Transaccion;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TransaccionMapper {

    /**
     * Convierte una petición Request en la entidad Transaccion.
     * Se ignoran el ID autogenerado (UUID) y las entidades relacionales de JPA 
     * para que sean gestionadas en la capa Service.
     */
    @Mapping(target = "idTransaccion", ignore = true)
    @Mapping(target = "metodoPago", ignore = true)
    @Mapping(target = "estado", ignore = true)
    Transaccion toEntity(TransaccionRequest request);

    /**
     * Convierte la entidad de base de datos en un objeto DTO Response.
     * Mapea y aplana las propiedades anidadas de forma segura.
     */
    @Mapping(source = "metodoPago.idMetodo", target = "idMetodo")
    @Mapping(source = "metodoPago.tipo", target = "tipoMetodo")
    @Mapping(source = "estado.nombre", target = "estado")
    TransaccionResponse toResponse(Transaccion transaccion);

    /**
     * Convierte una lista de entidades en una lista de respuestas DTO.
     */
    List<TransaccionResponse> toResponseList(List<Transaccion> transacciones);

    /**
     * Actualiza una entidad existente a partir de los datos del Request.
     * Crucial para mantener la integridad del UUID original.
     */
    @Mapping(target = "idTransaccion", ignore = true)
    @Mapping(target = "metodoPago", ignore = true)
    @Mapping(target = "estado", ignore = true)
    void updateEntity(TransaccionRequest request, @MappingTarget Transaccion transaccion);
}