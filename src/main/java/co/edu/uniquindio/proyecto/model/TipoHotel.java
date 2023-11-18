package co.edu.uniquindio.proyecto.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "TIPO_HOTEL", schema = "PY_EP")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class TipoHotel implements Serializable {

    @Id
    @Column(name = "ID_TIPO", nullable = false)
    @EqualsAndHashCode.Include
    private int idTipo;

    @Column(name = "NOMBRE", length = 30, nullable = false)
    private String nombre;

    @Column(name = "DESCRIPCION", length = 110, nullable = false)
    private String descripcion;


}
