package co.edu.uniquindio.proyecto.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "GAMA", schema = "PY_EP")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Gama implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_GAMA", nullable = false)
    @EqualsAndHashCode.Include
    private int idGama;

    @Column(name = "DESCRIPCION", nullable = false)
    private String descripcion;

    @Column(name = "NOMBRE", nullable = false)
    private String nombre;

    @Column(name = "MODELO")
    private String modelo;

}
