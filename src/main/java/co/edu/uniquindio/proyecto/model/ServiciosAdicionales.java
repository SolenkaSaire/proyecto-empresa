package co.edu.uniquindio.proyecto.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "SERVICIOS_ADICIONALES", schema = "PY_EP")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

public class ServiciosAdicionales implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "serviciosadicionales_seq")
    @SequenceGenerator(name = "serviciosadicionales_seq", sequenceName = "serviciosadicionales_SEQ", allocationSize = 1)
    @Column(name = "CODIGO_SERVICIO")
    @EqualsAndHashCode.Include
    private Integer codigoServicio;

    @Column(name = "DESCRIPCION", length = 110, nullable = false)
    private String descripcion;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "ID_AUTO", referencedColumnName = "ID_AUTO", nullable = false),
            @JoinColumn(name = "ID_RVA_AUTO", referencedColumnName = "ID_RVA_AUTO", nullable = false)
    })
    private DetalleReservaAutomovil detalleReservaAutomovil;

    @ManyToOne
    @JoinColumn(name = "TIPO_SERVICIO_ID", nullable = false)
    private TipoServicio tipoServicio;


    public ServiciosAdicionales(String descripcion, DetalleReservaAutomovil detalleReservaAutomovil, TipoServicio tipoServicio) {
        this.descripcion = descripcion;
        this.detalleReservaAutomovil = detalleReservaAutomovil;
        this.tipoServicio = tipoServicio;
    }

    public String toString() {
        return "ServiciosAdicionales: "+
                "codigoServicio = " + codigoServicio + ",\n" +
                "descripcion = '" + descripcion + "',\n" +
                "detalleReservaAutomovil = " + detalleReservaAutomovil.getAutomovil().getIdAutomovil() + ",\n" +
                "tipoServicio = " + tipoServicio.getNombreServicio() + "\n";
    }

}
