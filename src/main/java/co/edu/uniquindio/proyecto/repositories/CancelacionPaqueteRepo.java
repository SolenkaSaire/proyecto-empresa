package co.edu.uniquindio.proyecto.repositories;


import co.edu.uniquindio.proyecto.model.CancelacionPaquete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CancelacionPaqueteRepo extends JpaRepository<CancelacionPaquete, Integer> {
   @Query("select  u from CancelacionPaquete u where u.idCancelacion = :id")
    CancelacionPaquete obtener(Integer id);
}
