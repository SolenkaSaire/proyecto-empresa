package co.edu.uniquindio.proyecto.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "RESERVA_AUTOMOVIL", schema = "PY_EP")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class ReservaAutomovil implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reservaAutomovil_seq")
    @SequenceGenerator(name = "reservaAutomovil_seq", sequenceName = "reservaAutomovil_SEQ", allocationSize = 1)
    @Column(name = "ID_RVA_AUTO", nullable = false)
    @EqualsAndHashCode.Include
    private Integer idReservaAutomovil;

    @Column(name = "FECHA_INICIO", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;

    @Column(name = "FECHA_DEVOLUCION", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaDevolucion;

    @Column(name = "ORIGEN", length = 30)
    private String origen;

    @Column(name = "DESTINO", length = 30)
    private String destino;

    @ManyToOne
    @JoinColumn(name = "CODIGO_EMPLEADO", nullable = false)
    private Empleado empleado;

    @ManyToOne
    @JoinColumn(name = "ID_CLIENTE", nullable = false)
    private Cliente cliente;

    public ReservaAutomovil(Date fechaInicio, Date fechaDevolucion, String origen, String destino, Empleado empleado, Cliente cliente) {
        this.fechaInicio = fechaInicio;
        this.fechaDevolucion = fechaDevolucion;
        this.origen = origen;
        this.destino = destino;
        this.empleado = empleado;
        this.cliente = cliente;
    }

}
