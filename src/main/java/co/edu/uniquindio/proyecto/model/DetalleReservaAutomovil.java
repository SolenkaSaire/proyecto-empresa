package co.edu.uniquindio.proyecto.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "DETALLE_RESERVA_AUTOMOVIL", schema = "PY_EP")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class DetalleReservaAutomovil implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "ID_RVA_AUTO", nullable = false)
    @EqualsAndHashCode.Include
    private ReservaAutomovil reservaAutomovil;

    @Id
    @ManyToOne
    @JoinColumn(name = "ID_AUTO", nullable = false)
    @EqualsAndHashCode.Include
    private Automovil automovil;

    @Column(name = "CANTIDAD", nullable = false)
    private int cantidad;

    @Column(name = "PRECIO_DIA", nullable = false)
    private double precioDia;

    @Column(name = "FECHA", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fecha;

}
