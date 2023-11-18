package co.edu.uniquindio.proyecto.dto;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class HabitacionHotelData {
    private String id_habitacion;
    private String tipo;
    private String capacidad;
    private String nivel;
    private Double precio_noche;
    private String descripcion;
    private Integer cantidad_habitaciones;

    public HabitacionHotelData(String id_habitacion, String tipo, String capacidad, String nivel, Double precio_noche) {
        this.id_habitacion = id_habitacion;
        this.tipo = tipo;
        this.capacidad = capacidad;
        this.nivel = nivel;
        this.precio_noche = precio_noche;
    }

}
