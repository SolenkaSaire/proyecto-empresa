package co.edu.uniquindio.proyecto.repositories;

import co.edu.uniquindio.proyecto.model.DetalleCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalleCompraArticuloRepo extends JpaRepository<DetalleCompra, Integer> {

}
