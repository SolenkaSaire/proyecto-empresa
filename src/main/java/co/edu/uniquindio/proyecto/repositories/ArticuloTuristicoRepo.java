package co.edu.uniquindio.proyecto.repositories;

import co.edu.uniquindio.proyecto.model.ArticuloTuristico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticuloTuristicoRepo extends JpaRepository<ArticuloTuristico, Integer> {
}
