package co.edu.uniquindio.proyecto.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "DETALLE_RESERVA_AUTOMOVIL", schema = "PY_EP")
@IdClass(DetalleReservaAutoKey.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class DetalleReservaAutomovil implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "ID_RVA_AUTO")
    @EqualsAndHashCode.Include
    private ReservaAutomovil reservaAutomovil;

    @Id
    @ManyToOne
    @JoinColumn(name = "ID_AUTO", nullable = false)
    @EqualsAndHashCode.Include
    private Automovil automovil;

    @Column(name = "CANTIDAD", nullable = false)
    private Integer cantidad;

    @Column(name = "PRECIO_DIA", nullable = false)
    private Double precioDia;
/*
    @Column(name = "FECHA", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fecha;
*/
    @Column(name = "FECHA", nullable = false)
    private LocalDate fecha;


    @Override
    public String toString() {
        return "Detalle Reserva Automovil: " + "\n" +
                "   reservaAutomovil = " + reservaAutomovil.getIdReservaAutomovil() + ",\n" +
                "   automovil = " + automovil.getIdAutomovil() + ",\n" +
                "   cantidad = " + cantidad + ",\n" +
                "   precioDia = " + precioDia + ",\n" +
                "   fecha = " + fecha + "\n" +
                "";
    }
}
