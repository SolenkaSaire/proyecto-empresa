package co.edu.uniquindio.proyecto.repositories;

import co.edu.uniquindio.proyecto.model.RegimenHospedaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository

public interface RegimenHospedajeRepo extends JpaRepository<RegimenHospedaje, Integer> {

    @Query("select u from RegimenHospedaje u where u.idRegimen= ?1")
    RegimenHospedaje findById(String idRegimen);
}
