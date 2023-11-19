package co.edu.uniquindio.proyecto.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "EMPLEADO", schema = "PY_EP")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Empleado implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CODIGO_EMPLEADO", nullable = false)
    @EqualsAndHashCode.Include
    private int codigoEmpleado;

    @Column(name = "CEDULA", nullable = false)
    private int cedula;

    @Column(name = "SALARIO", nullable = false)
    private double salario;

    @Column(name = "TELEFONO", nullable = false)
    private String telefono;

    @Column(name = "CORREO_ELECTRONICO", nullable = false)
    private String correoElectronico;

    @ManyToOne
    @JoinColumn(name = "AGENCIA_ID_AGENCIA", referencedColumnName = "ID_AGENCIA", nullable = false)
    private Agencia agencia;

    @OneToOne
    @JoinColumn(name = "CEDULA", referencedColumnName = "CEDULA_PERSONA", insertable = false, updatable = false)
    private Persona persona;


    @Override
    public String toString() {
        return "Empleado {" + "\n" +
                "   codigoEmpleado = " + codigoEmpleado + ",\n" +
                "   cedula = " + cedula + ",\n" +
                "   salario = " + salario + ",\n" +
                "   telefono = '" + telefono + "',\n" +
                "   correoElectronico = '" + correoElectronico + "',\n" +
                "   agencia = " + agencia.getNombre() + ",\n" +
                "   persona = " + persona.getNombre() + "\n" +
                "}";
    }
}
