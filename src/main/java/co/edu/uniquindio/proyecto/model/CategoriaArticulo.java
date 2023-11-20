package co.edu.uniquindio.proyecto.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "CATEGORIA_ARTICULO", schema = "PY_EP")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CategoriaArticulo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "CODIGO_CAT", nullable = false)
    private int codigoCat;

    @Column(name = "NOMBRE", nullable = false, length = 30)
    private String nombre;

    @Column(name = "DESCRIPCION", nullable = false, length = 110)
    private String descripcion;

    /*
    @OneToMany(mappedBy = "categoriaArticulo", cascade = CascadeType.ALL)
    private List<ArticuloTuristico> articulosTuristicos;
*/

    @OneToMany(mappedBy = "categoriaArticulo", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ArticuloTuristico> articulosTuristicos;


    @Override
    public String toString() {
        return "CategoriaArticulo{" +
                "codigoCat=" + codigoCat +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }

}
