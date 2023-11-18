package co.edu.uniquindio.proyecto.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReservaHotelData {
    private String id_reserva;
    private String cedula_cliente;
    private String hotel;
    private String regimen_hospedaje;
    private String fecha_reserva;
    private String fecha_checkin;
    private String fecha_checkout;
    private Double total_con_impuesto;

    private Integer cantidad_habitaciones;
    private String estado;

}
