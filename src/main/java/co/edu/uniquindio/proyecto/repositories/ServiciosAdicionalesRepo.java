package co.edu.uniquindio.proyecto.repositories;

import co.edu.uniquindio.proyecto.model.ServiciosAdicionales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiciosAdicionalesRepo extends JpaRepository<ServiciosAdicionales, Integer> {


    @Query("select u from ServiciosAdicionales u where u.detalleReservaAutomovil.reservaAutomovil.idReservaAutomovil = ?1 and u.detalleReservaAutomovil.automovil.idAutomovil= ?2")
    List<ServiciosAdicionales> findByIdRvaIdAuto(int idRva, int idAuto);
}
