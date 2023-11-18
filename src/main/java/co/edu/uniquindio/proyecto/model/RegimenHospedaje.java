package co.edu.uniquindio.proyecto.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "REGIMEN_HOSPEDAJE", schema = "PY_EP")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class RegimenHospedaje implements Serializable {

    @Id
    @Column(name = "ID_REGIMEN", nullable = false)
    @EqualsAndHashCode.Include
    private int idRegimen;

    @Column(name = "DESCRIPCION", nullable = false)
    private String descripcion;

    @Column(name = "TIPO_REGIMEN", nullable = false)
    private String tipoRegimen;

    @Column(name = "FECHA", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fecha;

}
