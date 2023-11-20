package co.edu.uniquindio.proyecto.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ArticuloData {
    private String id_articulo;
    private String categoria;
    private String nombre;
    private String descripcion;
    private double precio;
    private int cantidad_articulos;

    public ArticuloData(String id_articulo, String categoria, String nombre, String descripcion, double precio) {
        this.id_articulo = id_articulo;
        this.categoria = categoria;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
    }



}
