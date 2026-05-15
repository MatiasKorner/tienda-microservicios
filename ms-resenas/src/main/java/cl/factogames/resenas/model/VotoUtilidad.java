package cl.factogames.resenas.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDate;

@Entity
@Table(name = "votos_utilidad")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VotoUtilidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_voto")
    private Long idVoto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_opinion")
    private Opinion opinion;

    @Column(name = "id_usuario_votante", nullable = false)
    private Integer idUsuarioVotante;

    @Column(name = "es_util", nullable = false)
    private Boolean esUtil;

    @CreatedDate
    @Column(name = "fecha_voto", updatable = false)
    private LocalDate fechaVoto;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VotoUtilidad)) return false;
        return idVoto != null && idVoto.equals(((VotoUtilidad) o).getIdVoto());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}