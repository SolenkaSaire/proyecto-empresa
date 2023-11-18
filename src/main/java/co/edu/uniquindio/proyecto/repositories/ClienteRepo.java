package co.edu.uniquindio.proyecto.repositories;


import co.edu.uniquindio.proyecto.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static javafx.scene.input.KeyCode.T;

@Repository
public interface ClienteRepo extends JpaRepository<Cliente, Integer> {

    @Query("select u from Cliente u where u.idCliente= ?1")
    Cliente obtener(Integer id);


    @Query("select u.idCliente from Cliente u where u.peCedulaPsna= ?1")
    String buscarByCedula(String cedula);


}
