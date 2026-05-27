package cl.factogames.pagos.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.factogames.pagos.model.EstadoPago;

/**
 * Repositorio Spring Data JPA para la entidad EstadoPago (Tabla Maestra).
 */
@Repository
public interface EstadoPagoRepository extends JpaRepository<EstadoPago, Integer> {

    /**
     * Busca un estado de pago por su nombre único.
     * Esencial para la lógica del Service al asignar estados a partir de cadenas de texto.
     */
    Optional<EstadoPago> findByNombre(String nombre);
}