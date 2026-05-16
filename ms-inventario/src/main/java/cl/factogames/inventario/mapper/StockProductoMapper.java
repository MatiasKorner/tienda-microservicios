package cl.factogames.inventario.mapper;

import cl.factogames.inventario.dto.StockProductoRequest;
import cl.factogames.inventario.dto.StockProductoResponse;
import cl.factogames.inventario.model.StockProducto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StockProductoMapper {

    @Mapping(target = "idStock", ignore = true)
    @Mapping(target = "almacen", ignore = true)
    @Mapping(target = "movimientos", ignore = true)
    StockProducto toEntity(StockProductoRequest request);

    @Mapping(source = "almacen.idAlmacen", target = "idAlmacen")
    @Mapping(source = "almacen.nombre", target = "nombreAlmacen")
    StockProductoResponse toResponse(StockProducto stockProducto);

    List<StockProductoResponse> toResponseList(List<StockProducto> stockProductos);

    @Mapping(target = "idStock", ignore = true)
    @Mapping(target = "almacen", ignore = true)
    @Mapping(target = "movimientos", ignore = true)
    void updateEntity(StockProductoRequest request, @MappingTarget StockProducto stockProducto);
}