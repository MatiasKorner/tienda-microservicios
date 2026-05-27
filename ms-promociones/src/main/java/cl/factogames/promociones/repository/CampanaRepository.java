package cl.factogames.promociones.repository;

import cl.factogames.promociones.model.Campana;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CampanaRepository extends JpaRepository<Campana, Integer> {

    @Query("SELECT c FROM Campana c WHERE :hoy BETWEEN c.fechaInicio AND c.fechaFin")
    List<Campana> findVigentes(LocalDate hoy);
}
