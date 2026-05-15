package cl.factogames.catalogo.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
    name = "categorias", 
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_categorias_nombre", columnNames = "nombre")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "nombre", nullable = false, length = 100, unique = true)
    private String nombre;

    @Lob
    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "restriccion_edad", nullable = false)
    private Integer restriccionEdad;

    @Column(name = "tags", length = 100)
    private String tags;

    @Builder.Default
    @ManyToMany(mappedBy = "categorias") 
    private List<Videojuego> videojuegos = new ArrayList<>();
}