package cl.factogames.pagos.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.factogames.pagos.model.Transaccion;

/**
 * Repositorio Spring Data JPA para la entidad Transaccion.
 * Utiliza UUID como tipo de dato identificador.
 */
@Repository
public interface TransaccionRepository extends JpaRepository<Transaccion, UUID> {

    /**
     * Recupera el historial de transacciones asociadas a un pedido.
     * Útil para comprobar el estado financiero de una orden de compra.
     */
    List<Transaccion> findByIdPedido(Long idPedido);
}