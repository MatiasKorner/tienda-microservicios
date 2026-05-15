package cl.factogames.notificaciones.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "plantillas", uniqueConstraints = {@UniqueConstraint(columnNames = "codigo_evento")})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Plantilla {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_plantilla")
    private Integer idPlantilla;

    @Column(name = "codigo_evento", nullable = false, length = 50)
    private String codigoEvento;

    @Column(name = "asunto_base", length = 100)
    private String asuntoBase;

    @Lob
    @Column(name = "cuerpo_base", nullable = false)
    private String cuerpoBase;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_canal")
    private Canal canal;

    @OneToMany(mappedBy = "plantilla")
    private List<Notificacion> notificaciones;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Plantilla)) return false;
        return idPlantilla != null && idPlantilla.equals(((Plantilla) o).getIdPlantilla());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}