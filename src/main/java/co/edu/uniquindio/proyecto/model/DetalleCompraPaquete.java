package co.edu.uniquindio.proyecto.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "DETALLE_COMPRA_PAQUETE", schema = "PY_EP")
@IdClass(DetalleCompraPaqueteKey.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class DetalleCompraPaquete implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "ID_PAQTURIST", nullable = false)
    @EqualsAndHashCode.Include
    private PaqueteTuristico paqueteTuristico;

    @Id
    @ManyToOne
    @JoinColumn(name = "CP_ID_COMPRA",nullable = false)
    @EqualsAndHashCode.Include
    private Compra compra;


    @Column(name = "DETALLE", nullable = false, length = 110)
    private String detalle;

    @Column(name = "CANTIDAD", nullable = false)
    private int cantidad;

    @Column(name = "ESTADO", nullable = false, length = 10)
    private String estado;


}
