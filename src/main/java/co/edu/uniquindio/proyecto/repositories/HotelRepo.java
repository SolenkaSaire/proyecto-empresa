package co.edu.uniquindio.proyecto.repositories;

import co.edu.uniquindio.proyecto.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository

public interface HotelRepo extends JpaRepository<Hotel, Integer> {

    @Query("select u from Hotel u where u.idHotel= ?1")
    Hotel findById(String idHotel);
}
