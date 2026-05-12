package pe.faustino.gestor.negocio.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cliente")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codiClie")
    private Integer codiClie;

    @Column(name = "ndniClie", length = 8)
    private String ndniClie;

    @Column(name = "appaClie", length = 45)
    private String appaClie;

    @Column(name = "apmaClie", length = 45)
    private String apmaClie;

    @Column(name = "nombClie", length = 50)
    private String nombClie;

    @Column(name = "fechNaci")
    private LocalDate fechNaci;
}