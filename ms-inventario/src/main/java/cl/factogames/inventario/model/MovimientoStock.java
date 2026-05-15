package cl.factogames.inventario.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;

@Entity
@Table(name = "movimientos_stock")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovimientoStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_movimiento")
    private Long idMovimiento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_stock")
    private StockProducto stock;

    @Column(name = "tipo_movimiento", length = 10)
    private String tipoMovimiento;

    @Column(name = "cantidad_afectada", nullable = false)
    private Integer cantidadAfectada;

    @CreatedDate
    @Column(name = "fecha_movimiento", updatable = false)
    private LocalDateTime fechaMovimiento;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MovimientoStock)) return false;
        return idMovimiento != null && idMovimiento.equals(((MovimientoStock) o).getIdMovimiento());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
