package cl.factogames.inventario.repository;

import cl.factogames.inventario.model.VideojuegoProyeccion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideojuegoProyeccionRepository extends JpaRepository<VideojuegoProyeccion, String> {
}