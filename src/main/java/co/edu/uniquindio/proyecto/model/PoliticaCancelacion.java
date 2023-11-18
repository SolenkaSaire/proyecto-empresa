package co.edu.uniquindio.proyecto.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "POLITICA_CANCELACION", schema = "PY_EP")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class PoliticaCancelacion implements Serializable {

    @Id
    @Column(name = "POLITICA_IDPOLITICA", nullable = false)
    @EqualsAndHashCode.Include
    private int politicaIdPolitica;

    @Column(name = "COSTO_CANCELACION", nullable = false)
    private Double costoCancelacion;

    @Column(name = "PLAZO_CANCELACION")
    private int plazoCancelacion;

    @OneToOne
    @MapsId
    @JoinColumn(name = "POLITICA_IDPOLITICA")
    private Politica politica;


    @ManyToOne
    @JoinColumn(name = "RVA_ID_RVA", nullable = false)
    private ReservaHotel reserva;

}
