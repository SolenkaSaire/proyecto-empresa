package co.edu.uniquindio.proyecto.repositories;


import co.edu.uniquindio.proyecto.model.Compra;
import co.edu.uniquindio.proyecto.model.MetodoPago;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Map;


@Repository
public interface CompraArticuloRepo extends JpaRepository<Compra, Integer>{

    @Query("select n from Compra n where n.idCompra =: id")
    Compra obtener (Integer id);

    @Query("SELECT co.idCompra as id_compra, cl.peCedulaPsna as cedula_cliente," +
            " ca.nombre as categoria, at.nombre as articulo_turistico, co.fecha as fecha_compra," +
            " at.descripcion as descripcion, me.nombre as metodo_pago, dc.cantidad as cantidad," +
            " (dc.precioUnidad * dc.cantidad) as precio_total, me.estado as estado " +
            "FROM Compra co " +
            "JOIN Cliente cl ON co.idCliente = cl.idCliente " +
            "JOIN MetodoPago me ON co.idMetodo = me.idMetodo " +
            "JOIN DetalleCompra dc ON co.idCompra = dc.compra.idCompra " +
            "JOIN ArticuloTuristico at ON dc.articuloTuristico.idArticulo = at.idArticulo " +
            "JOIN CategoriaArticulo ca ON at.catArtCodigo = ca.codigoCat")
    List<Object[]> buscarComprasArticulo();



    @Query("SELECT new map(c.idCliente as id, c.peCedulaPsna as cedula, p.nombre as nombre) FROM Cliente c JOIN Persona p ON c.peCedulaPsna = p.cedulaPersona")
    List<Map<String, Object>> obtenerClientes();

    @Query("SELECT new map(a.idArticulo as id_articulo, c.nombre as categoria, a.nombre as nombre, a.descripcion as descripcion, a.precio as precio) FROM ArticuloTuristico a JOIN CategoriaArticulo c ON a.catArtCodigo = c.codigoCat ")
    List<Map<String, Object>> obtenerArticulodData();


    @Query("SELECT new map(m.idMetodo as id_metodo, m.nombre as nombre) FROM MetodoPago m")
    List<Map<String, Object>>  obtenerMetodosPago();

    /*
    @Query("SELECT nombre from MetodoPago")
    List<String> obtenerMetodosPago();*/
/*
    @Query("SELECT m FROM MetodoPago m WHERE m.nombre = :nombreMetodo")
    MetodoPago findMetodoByNombre(@Param("nombreMetodo") String nombreMetodo);
*/

    @Query("SELECT m from MetodoPago m where m.idMetodo =: idMetodo ")
    MetodoPago findMetodoById(Integer idMetodo);

    @Query("SELECT m.idMetodo from MetodoPago m where m.nombre =: nombreMetodo ")
    String findIdMetodoByNombre(@Param("nombreMetodo") String nombreMetodo);
}
