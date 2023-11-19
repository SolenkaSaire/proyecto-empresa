package co.edu.uniquindio.proyecto.dto;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AutomovilData {
    private String id_automovil;
    private String tipo;
    private String marca;
    private String gama;
    private Double precio_diario;
    private Integer cantidad_autos;

    public AutomovilData(String id_automovil, String tipo, String marca, String gama, Double precio_diario) {
        this.id_automovil = id_automovil;
        this.tipo = tipo;
        this.marca = marca;
        this.gama = gama;
        this.precio_diario = precio_diario;
    }

}
