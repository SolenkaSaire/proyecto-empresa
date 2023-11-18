package co.edu.uniquindio.proyecto.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "PAQUETE_TURISTICO", schema = "PY_EP")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class PaqueteTuristico implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PAQ_TURIST", nullable = false)
    @EqualsAndHashCode.Include
    private int idPaqueteTuristico;

    @Column(name = "CENTRO_ID", nullable = false)
    private int centroId;

    @Column(name = "POL_CANCELACION_ID", nullable = false)
    private int politicaCancelacionId;

    @Column(name = "NOMBRE", nullable = false, length = 30)
    private String nombre;

    @Column(name = "DESCRIPCION", nullable = false, length = 110)
    private String descripcion;

    @Column(name = "PRECIO", nullable = false)
    private Double precio;

    @Column(name = "FECHA_PAQUETE", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaPaquete;


    @ManyToOne
    @JoinColumn(name = "CENTRO_ID", insertable = false, updatable = false)
    private CentroTuristico centroTuristico;

    @ManyToOne
    @JoinColumn(name = "POL_CANCELACION_ID", insertable = false, updatable = false)
    private PoliticaCancelacion politicaCancelacion;

}
