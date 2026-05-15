package cl.factogames.pagos.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "metodos_pago", uniqueConstraints = {
    @UniqueConstraint(name = "uq_usuario_metodo", columnNames = {"id_usuario", "tipo", "ultimo_rastro"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MetodoPago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_metodo")
    private Integer idMetodo;

    @Column(name = "id_usuario", nullable = false)
    private Integer idUsuario;

    @Column(name = "tipo", nullable = false, length = 20)
    private String tipo;

    @Column(name = "ultimo_rastro", length = 4)
    private String ultimoRastro;

    @OneToMany(mappedBy = "metodoPago")
    private List<Transaccion> transacciones;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MetodoPago)) return false;
        return idMetodo != null && idMetodo.equals(((MetodoPago) o).getIdMetodo());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}