package co.edu.uniquindio.proyecto.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "POLITICA_DESCUENTO", schema = "PY_EP")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class PoliticaDescuento implements Serializable {

    @Id
    @Column(name = "POLITICA_IDPOLITICA", nullable = false)
    @EqualsAndHashCode.Include
    private int politicaIdPolitica;

    @ManyToOne
    @JoinColumn(name = "ID_PAQ_TURIST", nullable = false)
    private PaqueteTuristico paqueteTuristico;

    @Column(name = "NUM_PERSONAS", nullable = false)
    private int numPersonas;

    @Column(name = "PORCENTAJE_DTO", nullable = false)
    private int porcentajeDescuento;

    @Column(name = "PRECIO", nullable = false)
    private Double precio;

    @Column(name = "DESCRIPCION", nullable = false)
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "POLITICA_IDPOLITICA", insertable = false, updatable = false)
    private Politica politica;

}
