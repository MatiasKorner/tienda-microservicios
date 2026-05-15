package cl.factogames.resenas.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "moderacion_estados", uniqueConstraints = {@UniqueConstraint(columnNames = "descripcion")})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ModeracionEstado {

    @Id
    @Column(name = "id_estado")
    private Integer idEstado;

    @Column(name = "descripcion", nullable = false, length = 20)
    private String descripcion;

    @OneToMany(mappedBy = "estado")
    private List<Opinion> opiniones;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ModeracionEstado)) return false;
        return idEstado != null && idEstado.equals(((ModeracionEstado) o).getIdEstado());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}