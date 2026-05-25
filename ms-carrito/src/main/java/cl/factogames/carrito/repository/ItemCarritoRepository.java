package cl.factogames.carrito.repository;
 
import cl.factogames.carrito.model.ItemCarrito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
 
import java.util.List;
import java.util.Optional;
 
@Repository
public interface ItemCarritoRepository extends JpaRepository<ItemCarrito, Long> {
 
    // Obtiene todos los ítems de un carrito específico
    List<ItemCarrito> findByCarritoId(Long carritoId);
 
    // Busca un ítem específico dentro de un carrito por su EAN
    // Útil para verificar si un juego ya está en el carrito antes de agregarlo
    Optional<ItemCarrito> findByCarritoIdAndVideojuegoEan(Long carritoId, String ean);
 
    // Verifica si un videojuego ya existe en un carrito
    boolean existsByCarritoIdAndVideojuegoEan(Long carritoId, String ean);
 
    // Elimina todos los ítems de un carrito (útil al vaciar o convertir el carrito)
    void deleteByCarritoId(Long carritoId);
}
 