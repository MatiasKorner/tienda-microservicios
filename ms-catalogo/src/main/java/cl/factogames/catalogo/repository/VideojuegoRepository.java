package cl.factogames.catalogo.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.factogames.catalogo.model.Videojuego;

@Repository
public interface VideojuegoRepository extends JpaRepository<Videojuego, Long>{

    Optional<Videojuego> findByTitulo(String titulo);

    boolean existsByTitulo(String titulo);
}
