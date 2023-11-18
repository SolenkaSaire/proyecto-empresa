package co.edu.uniquindio.proyecto.repositories;

import co.edu.uniquindio.proyecto.model.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonaRepo extends JpaRepository<Persona, String> {
}
