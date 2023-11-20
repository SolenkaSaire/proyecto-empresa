package co.edu.uniquindio.proyecto.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "COMPRA", schema = "PY_EP")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Compra implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "compra_seq")
    @SequenceGenerator(name = "compra_seq", sequenceName = "compra_SEQ", allocationSize = 1)
    @Column(name = "ID_COMPRA", nullable = false)
    @EqualsAndHashCode.Include
    private Integer idCompra;

    @Column(name = "ID_METODO", nullable = false)
    private int idMetodo;

    @Column(name = "ID_CLIENTE", nullable = false)
    private int idCliente;

    @Column(name = "CODIGO_EMPLEADO", nullable = false)
    private int codigoEmpleado;

    @Column(name = "FECHA", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fecha;

    @Column(name = "DESCRIPCION", nullable = false, length = 110)
    private String descripcion;

    @Column(name = "TIPO_COMPRA", nullable = false, length = 110)
    private String tipoCompra;

    @ManyToOne
    @JoinColumn(name = "ID_CLIENTE", referencedColumnName = "ID_CLIENTE", insertable = false, updatable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "CODIGO_EMPLEADO", referencedColumnName = "CODIGO_EMPLEADO", insertable = false, updatable = false)
    private Empleado empleado;

    @ManyToOne
    @JoinColumn(name = "ID_METODO", referencedColumnName = "ID_METODO", insertable = false, updatable = false)
    private MetodoPago metodoPago;


    public Compra(int idMetodo, int idCliente, int codigoEmpleado, Date fecha, String descripcion, String tipoCompra) {
        this.idMetodo = idMetodo;
        this.idCliente = idCliente;
        this.codigoEmpleado = codigoEmpleado;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.tipoCompra = tipoCompra;
    }
    /*
    public Compra(MetodoPago metodoPago, Cliente cliente, Empleado empleado, Date fecha, String descripcion, String tipoCompra) {
        this.metodoPago = metodoPago;
        this.cliente = cliente;
        this.empleado = empleado;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.tipoCompra = tipoCompra;
    }*/


}
