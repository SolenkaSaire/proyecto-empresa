package co.edu.uniquindio.proyecto.repositories;

import co.edu.uniquindio.proyecto.model.DetalleReservaAutomovil;
import co.edu.uniquindio.proyecto.model.ReservaAutomovil;
import co.edu.uniquindio.proyecto.model.ReservaHotel;
import co.edu.uniquindio.proyecto.model.ServiciosAdicionales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ReservaAutoRepo extends JpaRepository<ReservaAutomovil, Integer> {

    @Query("select n from ReservaAutomovil n where n.idReservaAutomovil=?1 ")
    ReservaAutomovil findByIdReserva(String idReserva);



    @Query("SELECT new map(c.idCliente as id, c.peCedulaPsna as cedula, p.nombre as nombre) FROM Cliente c JOIN Persona p ON c.peCedulaPsna = p.cedulaPersona")
    List<Map<String, Object>> obtenerClientes();


    /*
    @Query("SELECT rva.idReservaAutomovil AS id_reserva, cl.peCedulaPsna AS cedula_cliente," +
            " au.idAutomovil AS auto, sa.descripcion AS servicios_adicionales, rva.fechaInicio as fecha_reserva," +
            " rva.origen AS lugar_origen, rva.destino AS lugar_destino," +
            " (dra.precioDia * dra.cantidad) AS precio_total, dra.cantidad AS cantidad_autos" +
            " FROM ReservaAutomovil rva" +
            " JOIN Cliente cl ON rva.cliente.idCliente = cl.idCliente" +
            " JOIN DetalleReservaAutomovil dra ON rva.idReservaAutomovil=dra.reservaAutomovil.idReservaAutomovil" +
            " JOIN ServiciosAdicionales sa ON dra.reservaAutomovil.idReservaAutomovil = sa.detalleReservaAutomovil.reservaAutomovil.idReservaAutomovil" +
            " JOIN dra.automovil au ON dra.automovil.idAutomovil = au.idAutomovil")
    List<Object[]> buscarReservasAutomovil();*/

    @Query("SELECT " +
            "    rva.idReservaAutomovil AS id_reserva, " +
            "    cl.peCedulaPsna AS cedula_cliente, " +
            "    au.idAutomovil AS auto, " +
            "    nvl(sa.descripcion, 'Sin Servicio Adicional') AS servicios_adicionales, " +
            "    rva.fechaInicio AS fecha_reserva, " +
            "    rva.origen AS lugar_origen, " +
            "    rva.destino AS lugar_destino, " +
            "    (dra.precioDia * dra.cantidad) AS precio_total, " +
            "    dra.cantidad AS cantidad_autos " +
            "FROM " +
            "    ReservaAutomovil rva " +
            "LEFT JOIN " +
            "    Cliente cl ON rva.cliente.idCliente = cl.idCliente " +
            "LEFT JOIN " +
            "    DetalleReservaAutomovil dra ON rva.idReservaAutomovil = dra.reservaAutomovil.idReservaAutomovil " +
            "LEFT JOIN " +
            "    ServiciosAdicionales sa ON dra.reservaAutomovil.idReservaAutomovil = sa.detalleReservaAutomovil.reservaAutomovil.idReservaAutomovil " +
            "LEFT JOIN " +
            "    Automovil au ON dra.automovil.idAutomovil = au.idAutomovil " +
            "GROUP BY rva.idReservaAutomovil, cl.peCedulaPsna, au.idAutomovil, sa.descripcion, rva.fechaInicio, rva.origen, rva.destino,dra.precioDia, dra.cantidad")
    List<Object[]> buscarReservasAutomovil();

    @Query("select a.idAutomovil as id_automovil, t.descripcion as tipo, m.descripcion as marca, g.descripcion || ' año '|| g.modelo  as gama, a.precioDia as precio_diario " +
            "from Automovil a " +
            "join TipoAutomovil t on a.tipoAutomovil.idTipoAutomovil=t.idTipoAutomovil " +
            "join Marca m  on a.marca.idMarca =m.idMarca " +
            "join Gama g on a.gama.idGama= g.idGama")
    List<Map<String, Object>> obtenerAutomovilesData();


    @Query("select dra from DetalleReservaAutomovil dra where dra.reservaAutomovil.idReservaAutomovil=?1")
    List<DetalleReservaAutomovil> getDetallesReservaAutomovil(int idReservaAutomovil);


    @Query("select sa from ServiciosAdicionales sa where sa.detalleReservaAutomovil.reservaAutomovil.idReservaAutomovil=?1")
    List<ServiciosAdicionales>getServiciosAdicionales(int idReservaAutomovil);

    /*
    @Query("SELECT " +
                    "rv.idReservaAutomovil AS id_reserva_auto, " +
                    "cl.idCliente AS id_cliente, " +
                    "n.idAutomovil AS auto, " +
                    "sa.descripcion AS Servicios_Adicopmales, "+
                    "rv.fechaDevolucion AS fecha_devolucion, " +
                    "rv.fechaInicio AS fechainicio,"+
                    "rv.origen AS origen, " +
                    "rv.destino AS destino " +
                    "FROM " +
                    "ReservaAutomovil rv " +
                    "JOIN ServiciosAdicionales sa ON rv.serviciosAdicionales.descripcion "+
                    "JOIN Cliente cl ON rv.cliente.idCliente = cl.idCliente " +
                    "JOIN Empleado m ON rv.empleado.codigoEmpleado = m.codigoEmpleado " +
                    "JOIN Automovil n ON rv.automovil.idAutomovil = n.idAutomovil " +
                    "GROUP BY " +
                    "rv.idReservaAutomovil, " +
                    "cl.idCliente, " +
                    "n.idAutomovil, "+
                    "sa.descripcion, " +
                    "rv.fechaInicio, " +
                    "rv.fechaDevolucion, " +
                    "rv.origen, " +
                    "rv.destino")
    List<Object[]> buscarReservaAuto();*/


    //@Query("select new map(p.) from ")
}
