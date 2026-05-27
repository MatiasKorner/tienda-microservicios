package cl.factogames.opinion.repository;

import cl.factogames.opinion.model.VotoUtilidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface VotoUtilidadRepository extends JpaRepository<VotoUtilidad, Long> {

    // Para validar que un usuarion no vote dos veces por la misma reseña
    Optional<VotoUtilidad> findByOpinionIdOpinionAndUsuarioEmail(Integer idOpinion, String usuarioEmail);

    // Para contar rápidamente los votos útiles (True) de una opinión
    long countByOpinionIdOpinionAndEsUtilTrue(Integer idOpinion);
}
