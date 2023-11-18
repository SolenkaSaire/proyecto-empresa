package co.edu.uniquindio.proyecto.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "DETALLE_COMPRA_PAQUETE", schema = "PY_EP")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class DetalleCompraPaquete implements Serializable {

    @EmbeddedId
    @EqualsAndHashCode.Include
    private DetalleCompraPaqueteId id;

    @Column(name = "DETALLE", nullable = false, length = 110)
    private String detalle;

    @Column(name = "CANTIDAD", nullable = false)
    private int cantidad;

    @Column(name = "ESTADO", nullable = false, length = 10)
    private String estado;

    @ManyToOne
    @JoinColumn(name = "ID_PAQTURIST", referencedColumnName = "ID_PAQ_TURIST", insertable = false, updatable = false)
    private PaqueteTuristico paqueteTuristico;

    @ManyToOne
    @JoinColumn(name = "CP_ID_COMPRA", referencedColumnName = "ID_COMPRA", insertable = false, updatable = false)
    private Compra compra;


    @Embeddable
    @EqualsAndHashCode
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DetalleCompraPaqueteId implements Serializable {
        @Column(name = "ID_PAQTURIST", nullable = false)
        private int idPaqturist;

        @Column(name = "CP_ID_COMPRA", nullable = false)
        private int cpIdCompra;
    }
}
