package cl.factogames.carrito.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "videojuegos_proyeccion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VideojuegoProyeccion {

    @Id
    @Column(name = "id", length = 20)
    private String id;

    @Column(name = "titulo", nullable = false, length = 100)
    private String titulo;

    @Column(name = "precio", nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;
}