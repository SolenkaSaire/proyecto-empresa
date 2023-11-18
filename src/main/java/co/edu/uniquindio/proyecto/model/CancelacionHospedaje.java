package co.edu.uniquindio.proyecto.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "CANCELACION_HOSPEDAJE", schema = "PY_EP")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class CancelacionHospedaje implements Serializable {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @EqualsAndHashCode.Include
        @Column(name = "ID_CANCELACION", nullable = false)
        private int idCancelacion;

        @Column(name = "RVA_ID_RVA", nullable = false)
        private int rvaIdRva;

        @Column(name = "FECHA_CANCELACION", nullable = false)
        @Temporal(TemporalType.DATE)
        private Date fechaCancelacion;

        @Column(name = "COSTO", nullable = false)
        private double costo;

        @Column(name = "MOTIVO", nullable = false, length = 110)
        private String motivo;

        @Column(name = "POL_CANCELACION_ID", nullable = false)
        private int polCancelacionId;


        @ManyToOne
        @JoinColumn(name = "POL_CANCELACION_ID", referencedColumnName = "POLITICA_IDPOLITICA", insertable = false, updatable = false)
        private PoliticaCancelacion politicaCancelacion;

        @ManyToOne
        @JoinColumn(name = "RVA_ID_RVA", referencedColumnName = "ID_RESERVA", insertable = false, updatable = false)
        private ReservaHotel reserva;


}
