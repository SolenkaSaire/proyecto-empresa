package co.edu.uniquindio.proyecto.repositories;

import co.edu.uniquindio.proyecto.model.PoliticaCancelacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface PoliticaCancelacionRepo extends JpaRepository<PoliticaCancelacion, Integer> {


    @Query("select u from PoliticaCancelacion u where u.politicaIdPolitica= ?1")
    PoliticaCancelacion findById(int idHotel);
}
