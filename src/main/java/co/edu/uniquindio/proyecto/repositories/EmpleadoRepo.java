package co.edu.uniquindio.proyecto.repositories;

import co.edu.uniquindio.proyecto.model.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpleadoRepo extends JpaRepository<Empleado, Integer> {


/*
    @Query("select e.codigoEmpleado, e.cedula, p.nombre, p.direccion, e.agencia.id_agencia, e.salario, e.telefono, e.correoElectronico " +
            "from Empleado e " +
            "join Persona p on e.cedula=p.cedulaPersona " +
            "where e.cedula= ?1 and e.correoElectronico= ?2")
    Empleado findByCuentaArreglo(String cedula, String email);*/


    @Query("select e from Empleado e " +
            "join Persona p on e.cedula=p.cedulaPersona " +
            "where e.cedula= ?1 and e.correoElectronico= ?2")
    Empleado findByCuenta(String cedula, String email);
}
