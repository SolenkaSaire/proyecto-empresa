package co.edu.uniquindio.proyecto.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "HABITACION_RESERVA", schema = "PY_EP")
@IdClass(HabitacionReservaKey.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class HabitacionReserva implements Serializable {

    @Id
    @ManyToOne
    //@JoinColumn(name = "RVA_ID_RESERVA", nullable = false)
    @JoinColumn(name = "RVA_ID_RESERVA")
    @EqualsAndHashCode.Include
    private ReservaHotel reserva;

    @Id
    @ManyToOne
    @JoinColumn(name = "HAB_ID_HAB", nullable = false)
    @EqualsAndHashCode.Include
    private Habitacion habitacion;

    @Column(name = "PRECIO_TOTAL", nullable = false)
    private Double precioTotal;

    @Column(name = "ESTADO", nullable = false)
    private String estado;

    @Column(name = "DESCRIPCION", nullable = false)
    private String descripcion;

    @Column(name = "CANTIDAD", nullable = false)
    private Integer cantidad;


    @Override
    public String toString() {
        return "Reserva Habitacion {" + "\n" +
                "   reserva = " + reserva.getIdReserva() + ",\n" +
                "   habitacion = " + habitacion.getIdHabitacion() + ",\n" +
                "   precioTotal = " + precioTotal + ",\n" +
                "   estado = '" + estado + "',\n" +
                "   descripcion = '" + descripcion + "',\n" +
                "   cantidad = " + cantidad + "\n" +
                "}";
    }

}
