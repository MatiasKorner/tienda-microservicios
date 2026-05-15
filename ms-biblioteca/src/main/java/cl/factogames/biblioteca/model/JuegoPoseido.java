package cl.factogames.biblioteca.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "juegos_poseidos", uniqueConstraints = {
    @UniqueConstraint(name = "uq_usuario_biblioteca", columnNames = {"id_usuario", "id_juego"})
})
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JuegoPoseido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_propiedad")
    private Integer idPropiedad;

    @Column(name = "id_usuario", nullable = false)
    private Integer idUsuario;

    @Column(name = "id_juego", nullable = false)
    private Integer idJuego;

    @CreatedDate
    @Column(name = "fecha_adquisicion", updatable = false)
    private LocalDateTime fechaAdquisicion;

    @Column(name = "horas_jugadas", precision = 7, scale = 1)
    @Builder.Default
    private BigDecimal horasJugadas = BigDecimal.ZERO;

    @OneToMany(mappedBy = "juegoPoseido", cascade = CascadeType.ALL)
    private List<LicenciaActiva> licencias;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JuegoPoseido)) return false;
        return idPropiedad != null && idPropiedad.equals(((JuegoPoseido) o).getIdPropiedad());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}