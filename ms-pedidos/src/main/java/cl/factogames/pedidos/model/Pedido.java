package cl.factogames.pedidos.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "pedidos", uniqueConstraints = {@UniqueConstraint(columnNames = "codigo_seguimiento")})
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario_email", nullable = false)
    private UsuarioProyeccion usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_estado")
    private EstadoPedido estado;

    @CreatedDate
    @Column(name = "fecha_pedido", updatable = false)
    private LocalDateTime fechaPedido;

    @Column(name = "total_venta", nullable = false, precision = 12, scale = 2)
    private BigDecimal totalVenta;

    @Column(name = "codigo_seguimiento", length = 50)
    private String codigoSeguimiento;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetallePedido> detalles;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pedido)) return false;
        return id != null && id.equals(((Pedido) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}