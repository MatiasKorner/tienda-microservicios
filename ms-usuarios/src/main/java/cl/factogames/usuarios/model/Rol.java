package cl.factogames.usuarios.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(
    name = "roles"
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false, length = 50)
    private String nombreRol;

    @Column(name = "descripcion", length = 100)
    private String descripcion;

    @Column(name = "permiso_nivel")
    @Builder.Default
    private Integer permisoNivel = 1;

    @OneToMany(mappedBy = "rol")
    private List<Usuario> usuarios;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Rol)) return false;
        return id != null && id.equals(((Rol) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}