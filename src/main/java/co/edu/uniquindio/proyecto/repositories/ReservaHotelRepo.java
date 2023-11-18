package co.edu.uniquindio.proyecto.repositories;

import co.edu.uniquindio.proyecto.dto.ReservaHotelData;
import co.edu.uniquindio.proyecto.model.CancelacionHospedaje;
import co.edu.uniquindio.proyecto.model.Empleado;
import co.edu.uniquindio.proyecto.model.ReservaHotel;
import javafx.collections.ObservableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public interface ReservaHotelRepo extends JpaRepository<ReservaHotel, Integer> {
    @Query("select e from ReservaHotel e " +
            "where e.idReserva= ?1")
    ReservaHotel findByIdReserva(String idReserva);



    @Query("SELECT p.plazoCancelacion FROM PoliticaCancelacion p JOIN Hotel h ON p.politicaIdPolitica=h.politicaCancelacion.politicaIdPolitica WHERE h.idHotel = :idHotel")
    String obtenerPlazoCancelacion(@Param("idHotel") String idHotel);

    @Query("SELECT p.politicaIdPolitica FROM PoliticaCancelacion p JOIN Hotel h ON p.politicaIdPolitica=h.politicaCancelacion.politicaIdPolitica WHERE h.idHotel = :idHotel")
    String obtenerIDPlazoCancelacionByHotel(@Param("idHotel") String idHotel);
/*
    @Query("SELECT rv.idReserva AS id_reserva," +
            "cl.peCedulaPsna AS cedula_cliente, " +
            "h.nombre AS hotel, " +
            "re.descripcion AS regimen_hospedaje, " +
            "rv.fechaReserva AS fecha_reserva, " +
            "rv.fechaCheckin AS fecha_checkin, " +
            "rv.fechaCheckout AS fecha_checkout, " +
            "SUM(rv.total * (1 + rv.impuesto / 100)) AS total_con_impuesto, " +
            "MAX(hr.cantidad) AS cantidad_habitaciones, " +
            "MAX(hr.estado) AS estado, " +
            "hr.descripcion AS descripcion, " +
            "hr.habitacion.idHabitacion AS id_habitacion " +
            "FROM ReservaHotel rv " +
            "JOIN Cliente cl ON rv.cliente.idCliente = cl.idCliente " +
            "JOIN Hotel h ON rv.hotel.idHotel = h.idHotel " +
            "JOIN RegimenHospedaje re ON rv.regimenHospedaje.idRegimen = re.idRegimen " +
            "JOIN HabitacionReserva hr ON rv.idReserva = hr.reserva.idReserva " +
            "GROUP BY rv.idReserva, cl.peCedulaPsna, h.nombre, re.descripcion, rv.fechaReserva, rv.fechaCheckin, rv.fechaCheckout, hr.cantidad, hr.descripcion, hr.habitacion.idHabitacion")
    List<Object[]> buscarReservaHotel();*/

    @Query("SELECT rv.idReserva AS id_reserva," +
            "cl.peCedulaPsna AS cedula_cliente, " +
            "h.nombre AS hotel, " +
            "re.descripcion AS regimen_hospedaje, " +
            "rv.fechaReserva AS fecha_reserva, " +
            "rv.fechaCheckin AS fecha_checkin, " +
            "rv.fechaCheckout AS fecha_checkout, " +
            "SUM(rv.total * (1 + rv.impuesto / 100)) AS total_con_impuesto, " +
            "(SELECT SUM(hrDetalle.cantidad) FROM HabitacionReserva hrDetalle WHERE hrDetalle.reserva.idReserva = rv.idReserva) AS cantidad_habitaciones, " +
            "MAX(hr.estado) AS estado " +
            "FROM ReservaHotel rv " +
            "JOIN Cliente cl ON rv.cliente.idCliente = cl.idCliente " +
            "JOIN Hotel h ON rv.hotel.idHotel = h.idHotel " +
            "JOIN RegimenHospedaje re ON rv.regimenHospedaje.idRegimen = re.idRegimen " +
            "JOIN HabitacionReserva hr ON rv.idReserva = hr.reserva.idReserva " +
            "GROUP BY rv.idReserva, cl.peCedulaPsna, h.nombre, re.descripcion, rv.fechaReserva, rv.fechaCheckin, rv.fechaCheckout")
    List<Object[]> buscarReservaHotel();

    @Query("SELECT new map(p.idPais as id, p.nombre as nombre) FROM Pais p")
    List<Map<String, Object>> obtenerPaises();


    @Query("SELECT new map(c.idCiudad as id, c.nombre as nombre) FROM Ciudad c WHERE c.pais.idPais = :idPais")
    List<Map<String, Object>> obtenerCiudades(@Param("idPais") Integer idPais);


    @Query("SELECT new map(h.idHotel as id, h.nombre as nombre, h.precioNoche as precioNoche) FROM Hotel h")
    List<Map<String, Object>> obtenerHoteles();

    @Query("SELECT REHO.idRegimen as id, REHO.descripcion as descripcion, HR.precio as precio FROM Hotel H JOIN HotelRegimen HR ON H.idHotel=HR.hotel.idHotel JOIN RegimenHospedaje REHO ON HR.regimenHospedaje.idRegimen= REHO.idRegimen WHERE H.idHotel = :idHotel")
    List<Map<String, Object>> obtenerRegimenHospedaje(@Param("idHotel") Integer idHotel);

    @Query("SELECT new map(c.idCliente as id, c.peCedulaPsna as cedula, p.nombre as nombre) FROM Cliente c JOIN Persona p ON c.peCedulaPsna = p.cedulaPersona")
    List<Map<String, Object>> obtenerClientes();


    @Query("SELECT new map(h.idHabitacion as id_habitacion, tp.nombre as tipo, tp.capacidad as capacidad, n.nombre as nivel, h.precioNoche as precio_noche) FROM Habitacion h JOIN TipoHabitacion tp ON h.tipoHabitacion.idTipoHabitacion = tp.idTipoHabitacion JOIN Nivel n ON h.nivel.idNivel = n.idNivel")
    List<Map<String, Object>> obtenerHabitacionesData();

/*
    @Query("SELECT new map(h.idHabitacion as id_habitacion, tp.nombre as tipo, tp.capacidad as capacidad, n.nombre as nivel, h.precioNoche as precio_noche) " +
            "FROM Habitacion h JOIN TipoHabitacion tp ON h.tipoHabitacion.idTipoHabitacion = tp.idTipoHabitacion JOIN Nivel n ON h.nivel.idNivel = n.idNivel " +
            "WHERE h.hotel.idHotel = :idHotel " +
            "AND h.idHabitacion NOT IN ( " +
            "    SELECT DISTINCT hr.habitacion.idHabitacion " +
            "    FROM HabitacionReserva hr " +
            "    JOIN ReservaHotel r ON hr.reserva.idReserva= r.idReserva " +
            "    WHERE r.fechaCheckin = :checkin " +
            "    AND r.estado = 'Activa')")
    List<Map<String, Object>> obtenerHabitacionesDisponiblesData(@Param("idHotel") String idHotel, @Param("checkin") Date checkin);
*/

    @Query("SELECT new map(h.idHabitacion as id_habitacion, tp.nombre as tipo," +
            " tp.capacidad as capacidad, n.nombre as nivel, h.precioNoche as precio_noche) " +
            "FROM Habitacion h " +
            "JOIN TipoHabitacion tp ON h.tipoHabitacion.idTipoHabitacion = tp.idTipoHabitacion " +
            "JOIN Nivel n ON h.nivel.idNivel = n.idNivel " +
            "WHERE h.hotel.idHotel = :idHotel " +
            "AND h.idHabitacion NOT IN ( " +
            "    SELECT DISTINCT hr.habitacion.idHabitacion " +
            "    FROM HabitacionReserva hr " +
            "    JOIN ReservaHotel r ON hr.reserva.idReserva= r.idReserva " +
            "    WHERE r.fechaCheckin = :checkin " +
            "    AND r.estado = 'Activa')")
    List<Map<String, Object>> obtenerHabitacionesDisponiblesData(@Param("idHotel") String idHotel, @Param("checkin") LocalDate checkin);


    @Query("SELECT u FROM CancelacionHospedaje u JOIN ReservaHotel r ON u.reserva.idReserva = r.idReserva WHERE r.idReserva = :idReserva")
    CancelacionHospedaje buscarCancelacionHospedaje(Integer idReserva);
}
