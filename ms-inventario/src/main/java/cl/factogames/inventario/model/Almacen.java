package cl.factogames.inventario.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "almacenes", uniqueConstraints = {@UniqueConstraint(columnNames = "nombre")})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Almacen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_almacen")
    private Integer idAlmacen;

    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;

    @Column(name = "tipo", nullable = false, length = 20)
    private String tipo;

    @Column(name = "ubicacion_codigo", nullable = false, length = 3)
    private String ubicacionCodigo;

    @OneToMany(mappedBy = "almacen")
    private List<StockProducto> stocks;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Almacen)) return false;
        return idAlmacen != null && idAlmacen.equals(((Almacen) o).getIdAlmacen());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}