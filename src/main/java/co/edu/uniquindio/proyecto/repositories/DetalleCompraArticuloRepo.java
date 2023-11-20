package co.edu.uniquindio.proyecto.repositories;

import co.edu.uniquindio.proyecto.model.DetalleCompra;
import co.edu.uniquindio.proyecto.model.DetalleCompraPaquete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetalleCompraArticuloRepo extends JpaRepository<DetalleCompra, Integer> {


    @Query("select u from DetalleCompra u where u.compra.idCompra = ?1")
    List<DetalleCompra> findByCompraId(Integer idCompra);

}
