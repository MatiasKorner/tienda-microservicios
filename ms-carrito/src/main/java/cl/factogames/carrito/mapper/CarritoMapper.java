package cl.factogames.carrito.mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import cl.factogames.carrito.dto.CarritoResponse;
import cl.factogames.carrito.model.Carrito;

@Mapper(componentModel = "spring", uses = {UsuarioProyeccionMapper.class, ItemCarritoMapper.class})
public interface CarritoMapper {

    // "estado.nombre" navega el objeto anidado EstadoCarrito → nombre
    // "total" es calculado a partir de los subtotales de los items
    @Mapping(source = "estado.nombre", target = "estadoNombre")
    @Mapping(target = "total", ignore = true)
    CarritoResponse toResponse(Carrito carrito);

    List<CarritoResponse> toResponseList(List<Carrito> carritos);

    @AfterMapping
    default void calcularTotal(Carrito carrito, @MappingTarget CarritoResponse response) {
        if (response.getItems() != null) {
            BigDecimal total = response.getItems().stream()
                .map(item -> item.getSubtotal())
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
            response.setTotal(total);
        }
    }
}