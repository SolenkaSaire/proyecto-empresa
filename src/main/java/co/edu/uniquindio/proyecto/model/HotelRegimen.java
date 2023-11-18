package co.edu.uniquindio.proyecto.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "HOTEL_REGIMEN", schema = "PY_EP")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class HotelRegimen implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "HOTEL_ID_HOTEL", nullable = false)
    @EqualsAndHashCode.Include
    private Hotel hotel;

    @ManyToOne
    @JoinColumn(name = "REG_HOSP_ID", nullable = false)
    private RegimenHospedaje regimenHospedaje;

    @Column(name = "PRECIO", nullable = false)
    private Double precio;

}
