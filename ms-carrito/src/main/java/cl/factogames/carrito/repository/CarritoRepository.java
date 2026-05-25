package cl.factogames.carrito.repository;
 
import cl.factogames.carrito.model.Carrito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
 
import java.util.List;
import java.util.Optional;
 
@Repository
public interface CarritoRepository extends JpaRepository<Carrito, Long> {
 
    // Busca todos los carritos de un usuario (historial completo)
    List<Carrito> findByUsuarioEmail(String email);
 
    // Busca el carrito de un usuario filtrado por nombre de estado (ej: "ACTIVO")
    Optional<Carrito> findByUsuarioEmailAndEstadoNombre(String email, String estadoNombre);
 
    // Verifica si un usuario ya tiene un carrito en un estado específico
    boolean existsByUsuarioEmailAndEstadoNombre(String email, String estadoNombre);
 
    // Busca todos los carritos en un estado dado (útil para tareas de limpieza o reportes)
    List<Carrito> findByEstadoId(Integer idEstado);
}
 