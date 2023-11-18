package co.edu.uniquindio.proyecto.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "TIPO_HABITACION", schema = "PY_EP")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class TipoHabitacion implements Serializable {

    @Id
    @Column(name = "ID_TIPO_HABITACION", nullable = false)
    @EqualsAndHashCode.Include
    private int idTipoHabitacion;

    @Column(name = "CAPACIDAD", nullable = false)
    private int capacidad;

    @Column(name = "NOMBRE", length = 30, nullable = false)
    private String nombre;

}
