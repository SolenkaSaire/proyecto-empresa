package co.edu.uniquindio.proyecto.repositories;


import co.edu.uniquindio.proyecto.model.DetalleReservaAutomovil;
import co.edu.uniquindio.proyecto.model.HabitacionReserva;
import co.edu.uniquindio.proyecto.model.ReservaAutomovil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetalleReservaAutoRepo extends JpaRepository<DetalleReservaAutomovil, Integer> {



    @Query("select u from DetalleReservaAutomovil u where u.reservaAutomovil.idReservaAutomovil = ?1")
    List<DetalleReservaAutomovil> findByReserva_IdReserva(String idReserva);

    /*
    @Query("select u from DetalleReservaAutomovil u where u.automovil.idAutomovil = ?1 ")
    DetalleReservaAutomovil findByBothId(int parseInt, int parseInt1);
    */

    @Query("select u from DetalleReservaAutomovil u where u.reservaAutomovil.idReservaAutomovil = ?1 and u.automovil.idAutomovil = ?2")
    DetalleReservaAutomovil findByBothId(int idReserva, int idAutomovil);

}
