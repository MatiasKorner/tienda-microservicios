package cl.factogames.biblioteca.repository;

import cl.factogames.biblioteca.model.LicenciaActiva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LicenciaActivaRepository extends JpaRepository<LicenciaActiva, Integer> {

    Optional<LicenciaActiva> findByClaveActivacion(String claveActivacion);
}
