package cl.factogames.opinion.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "opiniones", uniqueConstraints = {
    @UniqueConstraint(name = "uq_usuario_juego", columnNames = {"email", "ean"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Opinion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_opinion")
    private Integer idOpinion;

    @Column(name = "ean", nullable = false)
    private String ean;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "calificacion", nullable = false)
    private Integer calificacion;

    
    @Column(name = "comentario")
    private String comentario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_estado")
    @Builder.Default
    private ModeracionEstado estado = null;

    @OneToMany(mappedBy = "opinion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VotoUtilidad> votos;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Opinion)) return false;
        return idOpinion != null && idOpinion.equals(((Opinion) o).getIdOpinion());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}