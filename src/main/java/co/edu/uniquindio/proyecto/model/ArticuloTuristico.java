package co.edu.uniquindio.proyecto.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "ARTICULO_TURISTICO", schema = "PY_EP")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ArticuloTuristico implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "ID_ARTICULO", nullable = false)
    private int idArticulo;

    @Column(name = "CAT_ART_CODIGO", nullable = false)
    private int catArtCodigo;

    @Column(name = "STOCK", nullable = false)
    private int stock;

    @Column(name = "NOMBRE", nullable = false, length = 30)
    private String nombre;

    @Column(name = "DESCRIPCION", nullable = false, length = 110)
    private String descripcion;

    @Column(name = "PRECIO", nullable = false)
    private double precio;

    @ManyToOne
    @JoinColumn(name = "CAT_ART_CODIGO", referencedColumnName = "CODIGO_CAT", insertable = false, updatable = false)
    private CategoriaArticulo categoriaArticulo;



    @Override
    public String toString() {
        return "ArticuloTuristico{" +
                "idArticulo=" + idArticulo +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                // other fields, but not categoriaArticulo
                '}';
    }
}
