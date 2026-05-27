package cl.factogames.inventario.repository;

import cl.factogames.inventario.model.StockProducto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StockProductoRepository extends JpaRepository<StockProducto, Long> {

    List<StockProducto> findByVideojuegoEan(String Ean);

    List<StockProducto> findByAlmacen_IdAlmacen(Integer idAlmacen);

    Optional<StockProducto> findByVideojuegoEanAndAlmacen_IdAlmacen(String ean, Integer idAlmacen);
}
