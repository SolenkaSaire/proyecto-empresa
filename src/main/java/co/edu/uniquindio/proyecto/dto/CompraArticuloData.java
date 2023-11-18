package co.edu.uniquindio.proyecto.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CompraArticuloData {
    private String id_compra;
    private String cedula_cliente;
    private String categoria;
    private String articulo_turistico;
    private String fecha_compra;
    private String descripcion;
    private String metodo_pago;
    private int cantidad;
    private double precio_total;
    private String estado;

}
