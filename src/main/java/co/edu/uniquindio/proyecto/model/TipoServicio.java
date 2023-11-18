package co.edu.uniquindio.proyecto.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "TIPO_SERVICIO", schema = "PY_EP")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class TipoServicio implements Serializable {

    @Id
    @Column(name = "ID_TIPO", nullable = false)
    @EqualsAndHashCode.Include
    private int idTipo;

    @Column(name = "NOMBRE_SERVICIO", length = 30, nullable = false)
    private String nombreServicio;

    @Column(name = "DESCRIPCION", length = 110, nullable = false)
    private String descripcion;

    @Column(name = "PRECIO", nullable = false)
    private Double precio;

}
