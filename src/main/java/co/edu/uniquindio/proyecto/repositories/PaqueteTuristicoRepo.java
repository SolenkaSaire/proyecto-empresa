package co.edu.uniquindio.proyecto.repositories;

import co.edu.uniquindio.proyecto.model.ArticuloTuristico;
import co.edu.uniquindio.proyecto.model.PaqueteTuristico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PaqueteTuristicoRepo extends JpaRepository<PaqueteTuristico, Integer> {


    @Query("select u from PaqueteTuristico u where u.idPaqueteTuristico= ?1")
    PaqueteTuristico obtener(int codigo);
}
