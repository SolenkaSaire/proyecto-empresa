package co.edu.uniquindio.proyecto.repositories;

import co.edu.uniquindio.proyecto.model.Agencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AgenciaRepo extends JpaRepository<Agencia,Integer> {

}
