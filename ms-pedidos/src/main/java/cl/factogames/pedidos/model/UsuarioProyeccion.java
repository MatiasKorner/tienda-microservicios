package cl.factogames.pedidos.model;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UsuarioProyeccion)) return false;
        return email != null && email.equals(((UsuarioProyeccion) o).getEmail());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}