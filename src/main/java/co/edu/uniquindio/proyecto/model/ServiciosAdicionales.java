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
@ToString
public class ServiciosAdicionales implements Serializable {

    @Id
    @Column(name = "CODIGO_SERVICIO", nullable = false)
    @EqualsAndHashCode.Include
    private int codigoServicio;

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

}
