package co.edu.uniquindio.proyecto.repositories;

import co.edu.uniquindio.proyecto.model.Automovil;
import co.edu.uniquindio.proyecto.model.TipoServicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TipoServicioRepo extends JpaRepository<TipoServicio, Integer> {

   @Query("select t.idTipo as id, t.nombreServicio as nombre, t.descripcion as informacion, t.precio as precio from TipoServicio t")
    List<Map<String, Object>> obtenerHabitacionesData();

    @Query("select u from TipoServicio u where u.idTipo= ?1")
    TipoServicio findByIdTipo(String idTipo);

}
