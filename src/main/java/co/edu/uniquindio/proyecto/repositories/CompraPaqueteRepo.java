package co.edu.uniquindio.proyecto.repositories;

import co.edu.uniquindio.proyecto.model.Compra;
import co.edu.uniquindio.proyecto.model.MetodoPago;
import co.edu.uniquindio.proyecto.model.PaqueteTuristico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface CompraPaqueteRepo extends JpaRepository<Compra, Integer> {

    /*
    @Query("select p from Compra p where p.idCompra =: id")
    Compra obtener (Integer id);*/

    @Query("SELECT c FROM Compra c WHERE c.idCompra = :id")
    Compra obtener(@Param("id") Integer id);

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


    @Query("SELECT new map(c.idCliente as id, c.peCedulaPsna as cedula, p.nombre as nombre) FROM Cliente c JOIN Persona p ON c.peCedulaPsna = p.cedulaPersona")
    List<Map<String, Object>> obtenerClientes();

    @Query("SELECT new map(p.idPaqueteTuristico as id_paquete, p.nombre as nombre, p.descripcion as descripcion, p.precio as precio, p.fechaPaquete as fecha_paquete) FROM PaqueteTuristico p")
    List<Map<String, Object>> obtenerPaquetesData();


    @Query("SELECT new map(m.idMetodo as id_metodo, m.nombre as nombre) FROM MetodoPago m")
    List<Map<String, Object>>  obtenerMetodosPago();


    /*
    @Query("SELECT m from MetodoPago m where m.idMetodo =: idMetodo ")
    MetodoPago findMetodoById(Integer idMetodo);*/

    @Query("SELECT m FROM MetodoPago m WHERE m.idMetodo = :idMetodo")
    MetodoPago findMetodoById(@Param("idMetodo") Integer idMetodo);

}


