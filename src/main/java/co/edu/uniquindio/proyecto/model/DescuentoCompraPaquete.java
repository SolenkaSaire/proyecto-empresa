package co.edu.uniquindio.proyecto.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "DESCUENTO_COMPRA_PAQUETE", schema = "PY_EP")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class DescuentoCompraPaquete implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "ID_DESCUENTO", nullable = false)
    private int idDescuento;

    @Column(name = "ID_COMPRA", nullable = false)
    private int idCompra;

    @Column(name = "CANTIDAD", nullable = false)
    private int cantidad;

    @Column(name = "FECHA", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fecha;

    @Column(name = "DESCRIPCION", nullable = false, length = 110)
    private String descripcion;

    @Column(name = "DNTO_IDPOLITICA", nullable = false)
    private int dntoIdPolitica;

    @ManyToOne
    @JoinColumn(name = "ID_COMPRA", referencedColumnName = "ID_COMPRA", insertable = false, updatable = false)
    private Compra compra;

    @ManyToOne
    @JoinColumn(name = "DNTO_IDPOLITICA", referencedColumnName = "POLITICA_IDPOLITICA", insertable = false, updatable = false)
    private PoliticaDescuento politicaDescuento;


}
