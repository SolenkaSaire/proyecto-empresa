package co.edu.uniquindio.proyecto.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "PERSONA", schema = "PY_EP")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Persona implements Serializable {

    @Id
    @Column(name = "CEDULA_PERSONA", nullable = false)
    @EqualsAndHashCode.Include
    private int cedulaPersona;

    @Column(name = "NOMBRE", length = 30)
    private String nombre;

    @Column(name = "DIRECCION", length = 110)
    private String direccion;

}
