package cl.factogames.usuarios.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
    name = "usuarios", 
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_usuarios_email", columnNames = "email")
    },
    indexes = {
        @Index(name = "idx_usuarios_rol", columnList = "rol"),
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

    @Column(name = "nombre", nullable = false, length = 150)
    private String nombre;

    @Column(name = "apellido", nullable = false, length = 150)
    private String apellido;

    @Column(name = "email", nullable = false, length = 100, unique = true)
    private String email;

    @Column(name = "password", nullable = false, length = 60)
    private String password;

    @Column(name = "rol", nullable = false, length = 50)
    private String rol;

    @Column(name = "activo")
    @Builder.Default
    private Boolean activo = true;

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private Perfil perfil;

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private Credencial credencial;
   
}