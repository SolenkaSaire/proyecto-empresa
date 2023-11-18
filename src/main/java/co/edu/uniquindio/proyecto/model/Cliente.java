package co.edu.uniquindio.proyecto.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "CLIENTE", schema = "PY_EP")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Cliente implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "ID_CLIENTE", nullable = false)
    private int idCliente;

    @Column(name = "PE_CEDULA_PSNA", nullable = false)
    private int peCedulaPsna;

    @Column(name = "CORREO_ELECTRONICO", length = 30)
    private String correoElectronico;

    @Column(name = "TELEFONO", length = 10)
    private String telefono;

    @ManyToOne
    @JoinColumn(name = "PE_CEDULA_PSNA", referencedColumnName = "CEDULA_PERSONA", insertable = false, updatable = false)
    private Persona persona;


}
