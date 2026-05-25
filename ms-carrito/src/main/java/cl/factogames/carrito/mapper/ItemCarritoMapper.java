package cl.factogames.carrito.mapper;

import java.math.BigDecimal;
import java.util.List;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import cl.factogames.carrito.dto.ItemCarritoResponse;
import cl.factogames.carrito.model.ItemCarrito;

@Mapper(componentModel = "spring", uses = {VideojuegoProyeccionMapper.class})
public interface ItemCarritoMapper {

    // "subtotal" es calculado, "videojuego" lo delega a VideojuegoProyeccionMapper
    @Mapping(target = "subtotal", ignore = true)
    ItemCarritoResponse toResponse(ItemCarrito item);

    List<ItemCarritoResponse> toResponseList(List<ItemCarrito> items);

    @AfterMapping
    default void calcularSubtotal(ItemCarrito item, @MappingTarget ItemCarritoResponse response) {
        if (item.getCantidad() != null && item.getPrecioUnitarioMomento() != null) {
            response.setSubtotal(
                item.getPrecioUnitarioMomento()
                    .multiply(new BigDecimal(item.getCantidad()))
            );
        }
    }
}