package co.edu.uniquindio.proyecto.repositories;

import co.edu.uniquindio.proyecto.model.DetalleCompraPaquete;
import jakarta.validation.constraints.Max;
import jdk.dynalink.linker.LinkerServices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetalleCompraPaqueteRepo extends JpaRepository<DetalleCompraPaquete, Integer> {


    @Query("select u from DetalleCompraPaquete u where u.compra.idCompra = ?1")
    List<DetalleCompraPaquete> findByCompraId(Integer idCompra);


    @Query("select u from DetalleCompraPaquete u where u.paqueteTuristico.idPaqueteTuristico = ?1 and u.compra.idCompra = ?2")
    DetalleCompraPaquete findByBothId(Integer IdCompra, Integer IdPaquete);
}
