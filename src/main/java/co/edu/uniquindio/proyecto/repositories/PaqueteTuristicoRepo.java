package co.edu.uniquindio.proyecto.repositories;

import co.edu.uniquindio.proyecto.model.ArticuloTuristico;
import co.edu.uniquindio.proyecto.model.PaqueteTuristico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PaqueteTuristicoRepo extends JpaRepository<PaqueteTuristico, Integer> {


}
