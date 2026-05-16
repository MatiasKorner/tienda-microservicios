package cl.factogames.inventario.repository;

import cl.factogames.inventario.model.StockProducto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StockProductoRepository extends JpaRepository<StockProducto, Integer> {

    List<StockProducto> findByIdJuego(Integer idJuego);

    List<StockProducto> findByAlmacen_IdAlmacen(Integer idAlmacen);

    Optional<StockProducto> findByIdJuegoAndAlmacen_IdAlmacen(Integer idJuego, Integer idAlmacen);
}
