package co.edu.uniquindio.proyecto.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "HOTEL_INSTALACION", schema = "PY_EP")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class HotelInstalacion implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "HOTEL_ID_HOTEL", nullable = false)
    @EqualsAndHashCode.Include
    private Hotel hotel;

    @ManyToOne
    @JoinColumn(name = "INSTALACION_ID", nullable = false)
    private Instalacion instalacion;

    @Column(name = "DESCRIPCION", nullable = false)
    private String descripcion;

}
