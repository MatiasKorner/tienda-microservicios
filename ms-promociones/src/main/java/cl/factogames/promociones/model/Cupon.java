package cl.factogames.promociones.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "cupones", uniqueConstraints = {@UniqueConstraint(columnNames = "codigo_alfa")})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cupon")
    private Integer idCupon;

    @Column(name = "codigo_alfa", nullable = false, length = 15)
    private String codigoAlfa;

    @Column(name = "monto_fijo", precision = 10, scale = 2)
    private BigDecimal montoFijo;

    @Column(name = "es_activo")
    @Builder.Default
    private Boolean esActivo = true;

    @Column(name = "usos_maximos")
    @Builder.Default
    private Integer usosMaximos = 1;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cupon)) return false;
        return idCupon != null && idCupon.equals(((Cupon) o).getIdCupon());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}