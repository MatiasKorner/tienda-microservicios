package cl.factogames.biblioteca.repository;

import cl.factogames.biblioteca.model.PlataformaAcceso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlataformaAccesoRepository extends JpaRepository<PlataformaAcceso, Integer> {

    Optional<PlataformaAcceso> findByNombre(String nombre);
}
