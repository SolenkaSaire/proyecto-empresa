package co.edu.uniquindio.proyecto.repositories;

import co.edu.uniquindio.proyecto.model.HabitacionReserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HabitacionReservaRepo extends JpaRepository<HabitacionReserva, Integer> {

    @Query("select u from HabitacionReserva u where u.reserva.idReserva= ?1 and u.habitacion.idHabitacion= ?2")
    HabitacionReserva findByBothId(int idReserva, int idHabitacion);


    @Query("select u from HabitacionReserva u where u.reserva.idReserva= ?1")
    List<HabitacionReserva> findByReservaId(Integer reservaId);


    @Query("select u from HabitacionReserva u where u.reserva.idReserva= ?1")
    List<HabitacionReserva> findByReserva_IdReserva(String idReserva);
}
