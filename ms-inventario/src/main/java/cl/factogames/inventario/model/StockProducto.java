package cl.factogames.inventario.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "stock_productos", uniqueConstraints = {
    @UniqueConstraint(name = "uq_juego_almacen", columnNames = {"id_juego", "id_almacen"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_stock")
    private Integer idStock;

    @Column(name = "id_juego", nullable = false)
    private Integer idJuego;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_almacen")
    private Almacen almacen;

    @Column(name = "cantidad", nullable = false)
    @Builder.Default
    private Integer cantidad = 0;

    @OneToMany(mappedBy = "stock")
    private List<MovimientoStock> movimientos;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StockProducto)) return false;
        return idStock != null && idStock.equals(((StockProducto) o).getIdStock());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}