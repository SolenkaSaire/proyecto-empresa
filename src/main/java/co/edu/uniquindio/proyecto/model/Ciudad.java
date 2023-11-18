package co.edu.uniquindio.proyecto.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "CIUDAD", schema = "PY_EP")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Ciudad implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "ID_CIUDAD", nullable = false)
    private int idCiudad;

    @Column(name = "PAIS_ID_PAIS", nullable = false)
    private int paisIdPais;

    @Column(name = "NOMBRE", nullable = false, length = 30)
    private String nombre;

    @Column(name = "CODIGO_POSTAL")
    private int codigoPostal;

    @ManyToOne
    @JoinColumn(name = "PAIS_ID_PAIS", referencedColumnName = "ID_PAIS", insertable = false, updatable = false)
    private Pais pais;


}
