package co.edu.uniquindio.proyecto.repositories;

import co.edu.uniquindio.proyecto.model.ServiciosAdicionales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiciosAdicionalesRepo extends JpaRepository<ServiciosAdicionales, Integer> {
}
