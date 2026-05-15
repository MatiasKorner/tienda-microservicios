package cl.factogames.usuarios.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
    name = "usuarios", 
    uniqueConstraints = {
        @UniqueConstraint(name= "uk_usuarios_username", columnNames = "username"),
        @UniqueConstraint(name = "uk_usuarios_email", columnNames = "email")
    },
    indexes = {
        @Index(name = "idx_usuarios_activo", columnList = "activo")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false, length = 50, unique = true)
    private String username;

    @Column(name = "email", nullable = false, length = 100, unique = true)
    private String email;

    @Column(name = "password", nullable = false, length = 60)
    private String password;

    @Column(name = "activo")
    @Builder.Default
    private Boolean activo = true;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_rol", referencedColumnName = "id", nullable = false)
    private Rol rol;

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private Perfil perfil;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Usuario)) return false;
        return id != null && id.equals(((Usuario) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}