package co.edu.uniquindio.proyecto.repositories;


import co.edu.uniquindio.proyecto.model.DetalleReservaAutomovil;
import co.edu.uniquindio.proyecto.model.ReservaAutomovil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalleReservaAutoRepo extends JpaRepository<DetalleReservaAutomovil, Integer> {
}
