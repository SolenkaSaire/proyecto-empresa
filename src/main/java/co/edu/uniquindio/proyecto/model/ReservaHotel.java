package co.edu.uniquindio.proyecto.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import static javafx.scene.input.KeyCode.T;

@Entity
@Table(name = "RESERVA", schema = "PY_EP")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ReservaHotel implements Serializable {


    /*
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_RESERVA", nullable = false)
    @EqualsAndHashCode.Include*/
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reserva_seq")
    @SequenceGenerator(name = "reserva_seq", sequenceName = "reserva_SEQ", allocationSize = 1)
    @Column(name = "ID_RESERVA")
    @EqualsAndHashCode.Include
    private Integer idReserva;

    @Column(name = "FECHA_RESERVA", nullable = false)
    private LocalDate fechaReserva;

    @Column(name = "FECHA_CHECKIN", nullable = false)
    private LocalDate fechaCheckin;

    @Column(name = "FECHA_CHECKOUT", nullable = false)
    private LocalDate fechaCheckout;

    @Column(name = "IMPUESTO", nullable = false)
    private Double impuesto;

    @Column(name = "ESTADO", nullable = false)
    private String estado;

    @Column(name = "TOTAL", nullable = false)
    private Double total;

    @ManyToOne
    @JoinColumn(name = "CLIENTE_ID_CLIENTE", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "CODIGO_EMPLEADO", nullable = false)
    private Empleado empleado;

    @ManyToOne
    @JoinColumn(name = "HOTEL_ID_HOTEL", nullable = false)
    private Hotel hotel;

    @ManyToOne
    @JoinColumn(name = "REGIMEN_HOSP_ID", nullable = false)
    private RegimenHospedaje regimenHospedaje;
/*
    @ManyToOne
    @JoinColumn(name = "POL_CANCELACION_ID", nullable = false)
    private PoliticaCancelacion politicaCancelacion;
*/

    public ReservaHotel( LocalDate fechaReserva, LocalDate fechaCheckin, LocalDate fechaCheckout, Double impuesto,
    String estado, Double total, Cliente cliente, Empleado empleado, Hotel hotel,
    RegimenHospedaje regimenHospedaje) {
        this.fechaReserva = fechaReserva;
        this.fechaCheckin = fechaCheckin;
        this.fechaCheckout = fechaCheckout;
        this.impuesto = impuesto;
        this.estado = estado;
        this.total = total;
        this.cliente = cliente;
        this.empleado = empleado;
        this.hotel = hotel;
        this.regimenHospedaje = regimenHospedaje;

    }

    @Override
    public String toString() {
        return "ReservaHotel {" + "\n" +
                "   idReserva = " + idReserva + ",\n" +
                "   fechaReserva = " + fechaReserva + ",\n" +
                "   fechaCheckin = " + fechaCheckin + ",\n" +
                "   fechaCheckout = " + fechaCheckout + ",\n" +
                "   impuesto = " + impuesto + ",\n" +
                "   estado = '" + estado + "',\n" +
                "   total = " + total + "\n" +
                "}";
    }

}
