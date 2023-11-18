package co.edu.uniquindio.proyecto.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "CANCELACION_PAQUETE", schema = "PY_EP")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class CancelacionPaquete implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "ID_CANCELACION", nullable = false)
    private int idCancelacion;

    @Column(name = "ID_COMPRA", nullable = false)
    private int idCompra;

    @Column(name = "FECHA_CANCELACION", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaCancelacion;

    @Column(name = "COSTO", nullable = false)
    private double costo;

    @Column(name = "MOTIVO", nullable = false, length = 110)
    private String motivo;

    @Column(name = "POL_CANCEL_ID", nullable = false)
    private int polCancelId;

    @ManyToOne
    @JoinColumn(name = "POL_CANCEL_ID", referencedColumnName = "POLITICA_IDPOLITICA", insertable = false, updatable = false)
    private PoliticaCancelacion politicaCancelacion;

    @ManyToOne
    @JoinColumn(name = "ID_COMPRA", referencedColumnName = "ID_COMPRA", insertable = false, updatable = false)
    private Compra compra;


}
