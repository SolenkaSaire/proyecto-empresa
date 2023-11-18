package co.edu.uniquindio.proyecto.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "HABITACION", schema = "PY_EP")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Habitacion implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_HABITACION", nullable = false)
    @EqualsAndHashCode.Include
    private int idHabitacion;

    @Column(name = "ESTADO", nullable = false)
    private String estado;

    @Column(name = "PRECIO_NOCHE", nullable = false)
    private Double precioNoche;

    @Column(name = "DESCRIPCION", nullable = false)
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "HOTEL_ID_HOTEL", nullable = false)
    private Hotel hotel;

    @ManyToOne
    @JoinColumn(name = "TH_ID_TIPO_HAB", nullable = false)
    private TipoHabitacion tipoHabitacion;

    @ManyToOne
    @JoinColumn(name = "NIVEL_ID_NIVEL", nullable = false)
    private Nivel nivel;

}
