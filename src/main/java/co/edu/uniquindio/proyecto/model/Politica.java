package co.edu.uniquindio.proyecto.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "POLITICA", schema = "PY_EP")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Politica implements Serializable {

    @Id
    @Column(name = "IDPOLITICA", nullable = false)
    @EqualsAndHashCode.Include
    private int idPolitica;

    @Column(name = "NOMBRE", length = 60, nullable = false)
    private String nombre;

    @Column(name = "DESCRIPCION", length = 110, nullable = false)
    private String descripcion;

}
