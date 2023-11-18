package co.edu.uniquindio.proyecto.repositories;

import co.edu.uniquindio.proyecto.model.Compra;
import co.edu.uniquindio.proyecto.model.PaqueteTuristico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CompraPaqueteRepo extends JpaRepository<Compra, Integer> {

    @Query("select p from Compra p where p.idCompra =: id")
    Compra obtener (Integer id);

    @Query("SELECT " +
            "co.idCompra AS id_compra, " +
            "cl.peCedulaPsna AS cedula_cliente, " +
            "paq.descripcion AS paquete, " +
            "paq.fechaPaquete AS fecha_paquete, " +
            "co.fecha AS fecha_compra, " +
            "ce.nombre AS centro_turistico, " +
            "me.nombre AS metodo_pago, " +
            "dc.cantidad AS cantidad_boletas, " +
            "paq.precio AS precio_total, " +
            "dc.estado AS estado " +
            "FROM Compra co " +
            "JOIN Cliente cl ON co.idCliente = cl.idCliente " +
            "JOIN MetodoPago me ON co.idMetodo = me.idMetodo " +
            "JOIN DetalleCompraPaquete dc ON co.idCompra = dc.compra.idCompra " +
            "JOIN PaqueteTuristico paq ON dc.paqueteTuristico.idPaqueteTuristico = paq.idPaqueteTuristico " +
            "JOIN CentroTuristico ce ON paq.centroTuristico.idCentro = ce.idCentro")
    List<Object[]> buscarCompraPaquete();
}


