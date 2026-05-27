package cl.factogames.carrito.repository;
 
import cl.factogames.carrito.model.VideojuegoProyeccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
 
@Repository
public interface VideojuegoProyeccionRepository extends JpaRepository<VideojuegoProyeccion, String> {
 
    // Verifica si un videojuego ya fue sincronizado en la proyección local
    boolean existsByEan(String ean);
}
 