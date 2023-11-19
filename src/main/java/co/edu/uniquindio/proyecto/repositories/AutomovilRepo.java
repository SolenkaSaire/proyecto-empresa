package co.edu.uniquindio.proyecto.repositories;

import co.edu.uniquindio.proyecto.model.Automovil;
import co.edu.uniquindio.proyecto.model.Habitacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface AutomovilRepo extends JpaRepository<Automovil, Integer> {

    @Query("select u from Automovil u where u.idAutomovil= ?1")
    Automovil findById(String idAutomovil);
}

