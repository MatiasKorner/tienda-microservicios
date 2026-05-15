package cl.factogames.inventario.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "videojuegos_proyeccion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VideojuegoProyeccion {

    @Id
    @Column(name = "id", length = 20)
    private String id;

    @Column(name = "titulo", nullable = false, length = 100)
    private String titulo;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VideojuegoProyeccion)) return false;
        return id != null && id.equals(((VideojuegoProyeccion) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}