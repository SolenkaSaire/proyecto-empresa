package co.edu.uniquindio.proyecto.repositories;

import co.edu.uniquindio.proyecto.model.CentroTuristico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CentroTuristicoRepo extends JpaRepository<CentroTuristico, Integer> {
    @Query("select u from CentroTuristico u where u.idCentro =: id")
    CentroTuristico obtener(Integer id);
}
