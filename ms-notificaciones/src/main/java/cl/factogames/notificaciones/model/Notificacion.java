package cl.factogames.notificaciones.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;

@Entity
@Table(name = "notificaciones")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_notificacion")
    private Long idNotificacion;

    @Column(name = "id_usuario", nullable = false)
    private Integer idUsuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_plantilla")
    private Plantilla plantilla;

    @Column(name = "leido")
    @Builder.Default
    private Boolean leido = false;

    @CreatedDate
    @Column(name = "fecha_envio", updatable = false)
    private LocalDateTime fechaEnvio;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Notificacion)) return false;
        return idNotificacion != null && idNotificacion.equals(((Notificacion) o).getIdNotificacion());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}