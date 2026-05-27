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

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario_email", referencedColumnName = "email", nullable = false)
    private Usuario usuario;

    @Column(name = "fecha_registro", nullable = false)
    private LocalDate fechaRegistro;

    @Column(name = "pais_origen", length = 50)
    @Builder.Default
    private String paisOrigen = "Chile";

    @Column(name = "direccion", length = 180)
    private String direccion;
}