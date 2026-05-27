package cl.factogames.promociones.model;

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
    @Column(name = "ean", nullable = false, length = 13, unique = true)
    private String ean;

    @Column(name = "titulo", nullable = false, length = 100)
    private String titulo;

    @Column(name = "precio", nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;
}