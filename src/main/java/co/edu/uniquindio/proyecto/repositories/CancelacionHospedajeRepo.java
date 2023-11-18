package co.edu.uniquindio.proyecto.repositories;

import co.edu.uniquindio.proyecto.model.CancelacionHospedaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CancelacionHospedajeRepo extends JpaRepository<CancelacionHospedaje, Integer> {
    @Query("select u from CancelacionHospedaje u where u.idCancelacion = :id")
    CancelacionHospedaje obtener (Integer id );
}
