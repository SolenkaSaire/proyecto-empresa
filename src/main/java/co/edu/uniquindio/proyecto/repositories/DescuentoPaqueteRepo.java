package co.edu.uniquindio.proyecto.repositories;

import co.edu.uniquindio.proyecto.model.DescuentoCompraPaquete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DescuentoPaqueteRepo extends JpaRepository<DescuentoCompraPaquete, Integer> {
}
