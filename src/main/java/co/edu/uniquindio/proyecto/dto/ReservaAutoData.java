package co.edu.uniquindio.proyecto.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReservaAutoData {
    private String id_reserva;
    private String cedula_cliente;
    private String auto;
    private String servicios_adicionales;
    private String fecha_reserva;
    private String lugar_origen;
    private String lugar_destino;
    private double precio_total;
    private int cantidad_autos;


}
