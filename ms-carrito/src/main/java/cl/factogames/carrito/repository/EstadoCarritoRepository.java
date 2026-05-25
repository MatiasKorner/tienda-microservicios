package cl.factogames.carrito.repository;
 
import cl.factogames.carrito.model.EstadoCarrito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
 
import java.util.Optional;
 
@Repository
public interface EstadoCarritoRepository extends JpaRepository<EstadoCarrito, Integer> {
 
    // Busca un estado por su nombre (ej: "ACTIVO", "ABANDONADO", "CONVERTIDO")
    Optional<EstadoCarrito> findByNombre(String nombre);
}
 