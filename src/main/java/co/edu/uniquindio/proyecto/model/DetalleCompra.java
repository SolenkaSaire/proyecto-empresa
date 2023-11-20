package co.edu.uniquindio.proyecto.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "DETALLE_COMPRA", schema = "PY_EP")
@IdClass(DetalleCompraArticuloKey.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class DetalleCompra implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "ID_ARTICULO", nullable = false)
    @EqualsAndHashCode.Include
    private ArticuloTuristico articuloTuristico;


    @Id
    @ManyToOne
    @JoinColumn(name = "ID_COMPRA",nullable = false)
    @EqualsAndHashCode.Include
    private Compra compra;

    @Column(name = "PRECIO_UNIDAD", nullable = false)
    private double precioUnidad;

    @Column(name = "CANTIDAD", nullable = false)
    private int cantidad;



}
