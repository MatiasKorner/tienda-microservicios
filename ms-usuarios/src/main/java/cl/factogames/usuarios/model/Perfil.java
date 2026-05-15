package cl.factogames.usuarios.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(
    name = "perfiles",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_perfil_usuario_email", columnNames = "usuario_email")
    },
    indexes = {
        @Index(name = "idx_perfiles_usuario_email", columnList = "usuario_email")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Perfil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", length = 100)
    private String nombre;

    @Column(name = "apellido", length = 100)
    private String apellido;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario_email", referencedColumnName = "email", nullable = false)
    private Usuario usuario;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @Column(name = "pais_origen", length = 50)
    @Builder.Default
    private String paisOrigen = "Chile";

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Perfil)) return false;
        return id != null && id.equals(((Perfil) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}