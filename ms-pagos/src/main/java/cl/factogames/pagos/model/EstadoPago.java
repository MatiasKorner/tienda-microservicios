package cl.factogames.pagos.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "estados_pago", uniqueConstraints = {@UniqueConstraint(columnNames = "nombre")})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EstadoPago {

    @Id
    @Column(name = "id_estado")
    private Integer idEstado;

    @Column(name = "nombre", nullable = false, length = 20)
    private String nombre;

    @OneToMany(mappedBy = "estado")
    private List<Transaccion> transacciones;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EstadoPago)) return false;
        return idEstado != null && idEstado.equals(((EstadoPago) o).getIdEstado());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}