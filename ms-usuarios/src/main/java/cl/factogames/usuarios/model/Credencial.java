package cl.factogames.usuarios.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
    name = "credenciales",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_credenciales_usuario_email", columnNames = "usuario_email")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Credencial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario_email", referencedColumnName = "email", nullable = false)
    private Usuario usuario;

    @Column(name = "ultimo_cambio_password")
    private LocalDateTime ultimoCambioPassword;

    @Column(name = "bloqueado", nullable = false)
    private Boolean bloqueado;

    @Column(name = "intentos_fallidos", nullable = false)
    private Integer intentosFallidos;
}