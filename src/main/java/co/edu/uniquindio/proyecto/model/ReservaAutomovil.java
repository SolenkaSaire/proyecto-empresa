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
    @Column(name = "ID_RVA_AUTO", nullable = false)
    @EqualsAndHashCode.Include
    private int idReservaAutomovil;

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

}
