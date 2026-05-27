package cl.factogames.carrito.repository;
 
import cl.factogames.carrito.model.UsuarioProyeccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
 
@Repository
public interface UsuarioProyeccionRepository extends JpaRepository<UsuarioProyeccion, String> {
 
    // Verifica si un usuario ya fue sincronizado en la proyección local
    boolean existsByEmail(String email);
}