package cl.factogames.pagos.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.factogames.pagos.model.MetodoPago;

/**
 * Repositorio Spring Data JPA para la entidad MetodoPago.
 */
@Repository
public interface MetodoPagoRepository extends JpaRepository<MetodoPago, Integer> {

    /**
     * Obtiene todos los métodos de pago registrados por un usuario en particular.
     */
    List<MetodoPago> findByIdUsuario(Integer idUsuario);

    /**
     * Busca un método de pago específico basado en su restricción única (uq_usuario_metodo).
     */
    Optional<MetodoPago> findByIdUsuarioAndTipoAndUltimoRastro(Integer idUsuario, String tipo, String ultimoRastro);
}