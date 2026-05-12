package pe.faustino.gestor.negocio.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codiUsua")
    private Integer codiUsua;

    @Column(name = "logiUsua", length = 45)
    private String logiUsua;

    @Column(name = "passUsua", length = 45)
    private String passUsua;
}