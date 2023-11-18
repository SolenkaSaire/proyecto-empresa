package co.edu.uniquindio.proyecto.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "HOTEL", schema = "PY_EP")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Hotel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_HOTEL", nullable = false, unique = true)
    @EqualsAndHashCode.Include
    private int idHotel;

    @ManyToOne
    @JoinColumn(name = "TIPO_HOTEL_ID_TIPO", nullable = false)
    private TipoHotel tipoHotel;

    @ManyToOne
    @JoinColumn(name = "CIUDAD_ID_CIUDAD", nullable = false)
    private Ciudad ciudad;

    @ManyToOne
    @JoinColumn(name = "POL_CANCELACION_ID", nullable = false)
    private PoliticaCancelacion politicaCancelacion;

    @Column(name = "PRECIO_NOCHE", nullable = false)
    private Double precioNoche;

    @Column(name = "NOMBRE", nullable = false)
    private String nombre;

}
