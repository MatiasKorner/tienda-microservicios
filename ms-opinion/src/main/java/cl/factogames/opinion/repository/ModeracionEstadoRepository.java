package cl.factogames.opinion.repository;

import cl.factogames.opinion.model.ModeracionEstado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModeracionEstadoRepository extends JpaRepository<ModeracionEstado, Integer> {

}