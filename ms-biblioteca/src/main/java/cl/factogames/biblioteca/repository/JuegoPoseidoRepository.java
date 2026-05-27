package cl.factogames.biblioteca.repository;

import cl.factogames.biblioteca.model.JuegoPoseido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JuegoPoseidoRepository extends JpaRepository<JuegoPoseido, Integer> {

    List<JuegoPoseido> findByEmail(String email);

    Optional<JuegoPoseido> findByEmailAndEan(String email, String ean);
}
