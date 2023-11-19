package co.edu.uniquindio.proyecto.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ServiciosAdicionalesData {

    private String id;
    private String nombre;
    private String informacion;
    private Double precio;
    private String descripcion;

    public ServiciosAdicionalesData(String id, String nombre, String informacion, Double precio) {
        this.id = id;
        this.nombre = nombre;
        this.informacion = informacion;
        this.precio = precio;
    }

}
