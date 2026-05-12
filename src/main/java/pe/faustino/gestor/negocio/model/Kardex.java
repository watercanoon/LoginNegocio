package pe.faustino.gestor.negocio.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "kardex")
public class Kardex {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codikard")
    private Integer codiKard;

    @Column(name = "tipoOper", nullable = false)
    private Integer tipoOper; // 1 para Entrada, 0 para Salida

    @Column(name = "cantProd", nullable = false)
    private Integer cantProd;

    @Column(name = "saldProd", nullable = false)
    private Integer saldProd;

    // Relación con Producto
    @ManyToOne
    @JoinColumn(name = "codiProd", nullable = false)
    private Producto producto;
}