package co.edu.uniquindio.proyecto.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CompraPaqueteData {
    private String id_compra;
    private String cedula_cliente;
    private String paquete;
    private String fecha_paquete;
    private String fecha_compra;
    private String centro_turistico;
    private String metodo_pago;
    private int cantidad_boletas;
    private double precio_total;
    private String estado;

}
