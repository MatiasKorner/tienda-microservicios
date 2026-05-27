package cl.factogames.opinion.repository;

import cl.factogames.opinion.model.Opinion;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface OpinionRepository extends JpaRepository<Opinion, Integer> {

    List<Opinion> findByEan(String ean);

    List<Opinion> findByEmail(String email);

    

}
