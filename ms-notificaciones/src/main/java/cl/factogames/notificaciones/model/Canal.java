package cl.factogames.notificaciones.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "canales", uniqueConstraints = {@UniqueConstraint(columnNames = "nombre")})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Canal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_canal")
    private Integer idCanal;

    @Column(name = "nombre", nullable = false, length = 20)
    private String nombre;

    @Column(name = "activo")
    @Builder.Default
    private Boolean activo = true;

    @OneToMany(mappedBy = "canal")
    private List<Plantilla> plantillas;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Canal)) return false;
        return idCanal != null && idCanal.equals(((Canal) o).getIdCanal());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}