package pe.faustino.gestor.negocio.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal; // Importación necesaria para manejar decimales exactos

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codiProd")
    private Integer codiProd;

    @Column(name = "nombProd", nullable = false, length = 150)
    private String nombProd;

    // Cambiamos Double por BigDecimal para que soporte precision y scale correctamente
    @Column(name = "precioProd", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioProd;

    @Column(name = "stock")
    private Integer stock = 0;
}