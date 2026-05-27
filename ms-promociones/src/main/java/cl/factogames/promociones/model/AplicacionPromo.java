package cl.factogames.promociones.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "aplicacion_promos", uniqueConstraints = {
    @UniqueConstraint(name = "uq_juego_campaña", columnNames = {"id_juego", "id_campaña"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AplicacionPromo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_aplicacion")
    private Integer idAplicacion;

    @Column(name = "ean", nullable = false)
    private String ean;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_campaña")
    private Campana campana;

    @Column(name = "prioridad")
    @Builder.Default
    private Integer prioridad = 1;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AplicacionPromo)) return false;
        return idAplicacion != null && idAplicacion.equals(((AplicacionPromo) o).getIdAplicacion());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}