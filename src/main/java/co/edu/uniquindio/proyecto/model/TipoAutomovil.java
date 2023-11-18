package co.edu.uniquindio.proyecto.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "TIPO_AUTOMOVIL", schema = "PY_EP")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class TipoAutomovil implements Serializable {

    @Id
    @Column(name = "ID_TIPO_AUTOMOVIL", nullable = false)
    @EqualsAndHashCode.Include
    private int idTipoAutomovil;

    @Column(name = "DESCRIPCION", length = 110, nullable = false)
    private String descripcion;

}
