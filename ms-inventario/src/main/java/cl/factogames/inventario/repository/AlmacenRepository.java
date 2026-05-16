package cl.factogames.inventario.repository;

import cl.factogames.inventario.model.Almacen;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AlmacenRepository extends JpaRepository<Almacen, Integer> {

    Optional<Almacen> findByNombre(String nombre);
}