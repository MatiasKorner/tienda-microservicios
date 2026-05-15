package cl.factogames.carrito.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "usuarios_proyeccion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioProyeccion {

    @Id
    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "username", nullable = false, length = 50)
    private String username;
}
