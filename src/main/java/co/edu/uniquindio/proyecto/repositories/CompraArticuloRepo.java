package co.edu.uniquindio.proyecto.repositories;


import co.edu.uniquindio.proyecto.model.Compra;
import org.springframework.data.jpa.repository.Query;
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
}
