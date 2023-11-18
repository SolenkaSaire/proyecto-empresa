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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "ID_COMPRA", nullable = false)
    private int idCompra;

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


}
