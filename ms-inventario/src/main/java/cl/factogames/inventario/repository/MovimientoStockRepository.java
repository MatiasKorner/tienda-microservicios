package cl.factogames.inventario.repository;

import cl.factogames.inventario.model.MovimientoStock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovimientoStockRepository extends JpaRepository<MovimientoStock, Integer> {

    List<MovimientoStock> findByStock_IdStock(Integer idStock);
}