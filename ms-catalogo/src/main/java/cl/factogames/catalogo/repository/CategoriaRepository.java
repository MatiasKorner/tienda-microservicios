package cl.factogames.catalogo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.factogames.catalogo.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long>{

}
