package cl.factogames.promociones.repository;

import cl.factogames.promociones.model.Cupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CuponRepository extends JpaRepository<Cupon, Integer> {

    Optional<Cupon> findByCodigoAlfa(String codigoAlfa);
}
