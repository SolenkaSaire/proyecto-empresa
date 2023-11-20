package co.edu.uniquindio.proyecto.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PaqueteData {

    private String id_paquete;
    private String nombre;
    private String descripcion;
    private double precio;
    private String fecha_paquete;
    private int cantidad_boletas;
    private String detalle;

    public PaqueteData(String id_paquete, String nombre, String descripcion, double precio, String fecha_paquete) {
        this.id_paquete = id_paquete;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.fecha_paquete = fecha_paquete;
    }



}
