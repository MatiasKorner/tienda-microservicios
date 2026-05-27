package cl.factogames.biblioteca.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "licencias_activas", uniqueConstraints = {@UniqueConstraint(columnNames = "clave_activacion")})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LicenciaActiva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_licencia")
    private Integer idLicencia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_propiedad")
    private JuegoPoseido juegoPoseido;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_plataforma")
    private PlataformaAcceso plataforma;

    @Column(name = "clave_activacion", nullable = false, length = 50)
    private String claveActivacion;

    @Column(name = "activada_en")
    private LocalDateTime activadaEn;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LicenciaActiva)) return false;
        return idLicencia != null && idLicencia.equals(((LicenciaActiva) o).getIdLicencia());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
