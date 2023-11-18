package co.edu.uniquindio.proyecto.repositories;

import co.edu.uniquindio.proyecto.model.Habitacion;
import co.edu.uniquindio.proyecto.model.RegimenHospedaje;
import co.edu.uniquindio.proyecto.model.ReservaHotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface HabitacionRepo extends JpaRepository<Habitacion, Integer> {

    @Query("select u from Habitacion u where u.idHabitacion= ?1")
    Habitacion findById(String idHabitacion);
}
