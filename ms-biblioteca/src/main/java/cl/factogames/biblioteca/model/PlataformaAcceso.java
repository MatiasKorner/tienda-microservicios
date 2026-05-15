package cl.factogames.biblioteca.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "plataformas_acceso", uniqueConstraints = {@UniqueConstraint(columnNames = "nombre")})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlataformaAcceso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_plataforma")
    private Integer idPlataforma;

    @Column(name = "nombre", nullable = false, length = 30)
    private String nombre;

    @Column(name = "requiere_launcher")
    @Builder.Default
    private Boolean requiereLauncher = true;

    @OneToMany(mappedBy = "plataforma")
    private List<LicenciaActiva> licencias;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlataformaAcceso)) return false;
        return idPlataforma != null && idPlataforma.equals(((PlataformaAcceso) o).getIdPlataforma());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}