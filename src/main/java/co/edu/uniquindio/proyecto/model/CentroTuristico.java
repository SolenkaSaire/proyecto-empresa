package co.edu.uniquindio.proyecto.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "CENTRO_TURISTICO", schema = "PY_EP")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class CentroTuristico implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "ID_CENTRO", nullable = false)
    private int idCentro;

    @Column(name = "CIUDAD_ID_CIUDAD", nullable = false)
    private int ciudadIdCiudad;

    @Column(name = "NOMBRE", nullable = false, length = 30)
    private String nombre;

    @Column(name = "DIRECCION", nullable = false, length = 110)
    private String direccion;

    @ManyToOne
    @JoinColumn(name = "CIUDAD_ID_CIUDAD", referencedColumnName = "ID_CIUDAD", insertable = false, updatable = false)
    private Ciudad ciudad;


}
