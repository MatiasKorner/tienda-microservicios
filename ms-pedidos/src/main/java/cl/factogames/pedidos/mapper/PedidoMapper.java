package cl.factogames.pedidos.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import cl.factogames.pedidos.dto.PedidoRequest;
import cl.factogames.pedidos.dto.PedidoResponse;
import cl.factogames.pedidos.model.Pedido;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PedidoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "estado", ignore = true)
    @Mapping(target = "fechaPedido", ignore = true)
    @Mapping(target = "codigoSeguimiento", ignore = true)
    @Mapping(target = "detalles", ignore = true)
    Pedido toEntity(PedidoRequest request);

    @Mapping(source = "estado.nombre", target = "estado")
    PedidoResponse toResponse(Pedido pedido);

    List<PedidoResponse> toResponseList(List<Pedido> pedidos);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "estado", ignore = true)
    @Mapping(target = "fechaPedido", ignore = true)
    @Mapping(target = "codigoSeguimiento", ignore = true)
    @Mapping(target = "detalles", ignore = true)
    void updateEntity(PedidoRequest request, @MappingTarget Pedido pedido);
}
