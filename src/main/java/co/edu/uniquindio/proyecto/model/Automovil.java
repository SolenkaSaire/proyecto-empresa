package co.edu.uniquindio.proyecto.model;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "AUTOMOVIL", schema = "PY_EP")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Automovil implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "ID_AUTOMOVIL", nullable = false)
    private int idAutomovil;

    @Column(name = "TP_AUTO_ID", nullable = false)
    private int tipoAutoId;

    @Column(name = "MARCA_ID_MARCA", nullable = false)
    private int marcaIdMarca;

    @Column(name = "GAMA_ID_GAMA", nullable = false)
    private int gamaIdGama;

    @Column(name = "PRECIO_DIA", nullable = false)
    private double precioDia;

    @Column(name = "DISPONIBILIDAD", nullable = false, length = 15)
    private String disponibilidad;

    @ManyToOne
    @JoinColumn(name = "TP_AUTO_ID", referencedColumnName = "ID_TIPO_AUTOMOVIL", insertable = false, updatable = false)
    private TipoAutomovil tipoAutomovil;

    @ManyToOne
    @JoinColumn(name = "MARCA_ID_MARCA", referencedColumnName = "ID_MARCA", insertable = false, updatable = false)
    private Marca marca;

    @ManyToOne
    @JoinColumn(name = "GAMA_ID_GAMA", referencedColumnName = "ID_GAMA", insertable = false, updatable = false)
    private Gama gama;



}