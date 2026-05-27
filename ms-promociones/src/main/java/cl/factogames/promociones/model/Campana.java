package cl.factogames.promociones.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "campana")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Campana {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_campaña")
    private Integer idCampana;

    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;

    @Column(name = "descuento_porcentaje", nullable = false)
    private Integer descuentoPorcentaje;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin", nullable = false)
    private LocalDate fechaFin;

    @OneToMany(mappedBy = "campana", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AplicacionPromo> aplicaciones;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Campana)) return false;
        return idCampana != null && idCampana.equals(((Campana) o).getIdCampana());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}