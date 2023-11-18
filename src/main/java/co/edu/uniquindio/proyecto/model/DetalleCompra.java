package co.edu.uniquindio.proyecto.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "DETALLE_COMPRA", schema = "PY_EP")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class DetalleCompra implements Serializable {

    @EmbeddedId
    @EqualsAndHashCode.Include
    private DetalleCompraId id;

    @Column(name = "PRECIO_UNIDAD", nullable = false)
    private double precioUnidad;

    @Column(name = "CANTIDAD", nullable = false)
    private int cantidad;

    @ManyToOne
    @JoinColumn(name = "ID_ARTICULO", referencedColumnName = "ID_ARTICULO", insertable = false, updatable = false)
    private ArticuloTuristico articuloTuristico;

    @ManyToOne
    @JoinColumn(name = "ID_COMPRA", referencedColumnName = "ID_COMPRA", insertable = false, updatable = false)
    private Compra compra;


    @Embeddable
    @EqualsAndHashCode
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DetalleCompraId implements Serializable {
        @Column(name = "ID_ARTICULO", nullable = false)
        private int idArticulo;

        @Column(name = "ID_COMPRA", nullable = false)
        private int idCompra;
    }
}
