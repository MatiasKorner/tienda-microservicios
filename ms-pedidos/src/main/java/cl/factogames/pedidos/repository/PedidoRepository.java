package cl.factogames.pedidos.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.factogames.pedidos.model.Pedido;

/**
 * Repositorio para la entidad Pedido.
 * Sigue el estándar de JpaRepository utilizado en el proyecto de usuarios.
 */
@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

    /**
     * Busca un pedido por su código de seguimiento único.
     */
    Optional<Pedido> findByCodigoSeguimiento(String codigoSeguimiento);

    /**
     * Recupera todos los pedidos asociados a un usuario específico.
     * Útil para el historial de compras del cliente.
     */
    List<Pedido> findByIdUsuario(Integer idUsuario);

    /**
     * Verifica si existe un pedido con un código de seguimiento específico.
     */
    boolean existsByCodigoSeguimiento(String codigoSeguimiento);
}