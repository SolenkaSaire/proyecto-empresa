package co.edu.uniquindio.proyecto.controllers;

import co.edu.uniquindio.proyecto.dto.ReservaHotelData;
import co.edu.uniquindio.proyecto.model.Empleado;
import co.edu.uniquindio.proyecto.model.ReservaHotel;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Getter
@Setter
@Component
public class SceneController {
    @Autowired
    private ConfigurableApplicationContext springContext;

 //   private Empleado empleadoLogueado;
/*
    @Autowired
    public SceneController(FirmaAbogadosRepo firmaAbogadosRepo, AdminRepo adminRepo) {
        this.firmaAbogadosRepo = firmaAbogadosRepo;
        this.adminRepo = adminRepo;
        firmaAbogados = firmaAbogadosRepo.findById(1).orElse(null);
        if (firmaAbogados == null) {
            firmaAbogados = new FirmaAbogados(1, "Criterion");
            Admin admin = new Admin("Admin", "1", "1", "admin@gmail.com", "1", "1", "1", firmaAbogados);
            firmaAbogadosRepo.save(firmaAbogados);
            adminRepo.save(admin);
        }
    }*/


    public void cambiarAVentanaReservaHotel(ActionEvent event, Empleado empleadoLogueado) {


        try {
         //   FXMLLoader loader = new FXMLLoader(getClass().getResource("views/VistaReservaHotel.fxml"));
          //  FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/VistaReservaHotel.fxml"));

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/VistaReservaHotel.fxml"));
            loader.setControllerFactory(springContext::getBean);
            Parent root = loader.load();

            ReservaHotelController controlador = loader.getController();
            controlador.displayEmployeeIDUsername(empleadoLogueado);

            Stage stage = new Stage();
            Scene scene = new Scene(root);

            stage.setTitle("Turism Management System");
            stage.setMinHeight(700);
            stage.setMinWidth(1100);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {

            e.printStackTrace();
        }
    }


    public void cambiarAVentanaReservaAuto(ActionEvent event, Empleado empleadoLogueado) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/VistaReservaAuto.fxml"));
            loader.setControllerFactory(springContext::getBean);
            Parent root = loader.load();

            ReservaAutoController controlador = loader.getController();
            controlador.displayEmployeeIDUsername(empleadoLogueado);

            Stage stage = new Stage();
            Scene scene = new Scene(root);

            stage.setTitle("Turism Management System");
            stage.setMinHeight(700);
            stage.setMinWidth(1100);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {

            e.printStackTrace();
        }
    }


    public void cambiarAVentanaCompraPaquete(ActionEvent event, Empleado empleadoLogueado) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/VistaCompraPaquete.fxml"));
            loader.setControllerFactory(springContext::getBean);
            Parent root = loader.load();

            CompraPaqueteController controlador = loader.getController();
            controlador.displayEmployeeIDUsername(empleadoLogueado);

            Stage stage = new Stage();
            Scene scene = new Scene(root);

            stage.setTitle("Turism Management System");
            stage.setMinHeight(700);
            stage.setMinWidth(1100);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {

            e.printStackTrace();
        }
    }


    public void cambiarAVentanaCompraArticulo(ActionEvent event, Empleado empleadoLogueado) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/VistaCompraArticulo.fxml"));
            loader.setControllerFactory(springContext::getBean);
            Parent root = loader.load();

            CompraArticuloController controlador = loader.getController();
            controlador.displayEmployeeIDUsername(empleadoLogueado);

            Stage stage = new Stage();
            Scene scene = new Scene(root);

            stage.setTitle("Turism Management System");
            stage.setMinHeight(700);
            stage.setMinWidth(1100);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {

            e.printStackTrace();
        }
    }


    public void cambiarAVentanaCrearReservaHotel(ActionEvent event, Empleado empleadoLogueado) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/VistaCrearReservaHotel.fxml"));
            loader.setControllerFactory(springContext::getBean);
            Parent root = loader.load();

            CrearReservaHotelController controlador = loader.getController();
            controlador.displayEmployeeIDUsername(empleadoLogueado);

            Stage stage = new Stage();
            Scene scene = new Scene(root);

            stage.setTitle("Turism Management System - Crear Nueva Reserva Hotel");
            stage.setMinHeight(700);
            stage.setMinWidth(1100);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    public void cambiarAVentanaActualizarReservaHotel(ActionEvent event, Empleado empleadoLogin, ReservaHotelData reservaActualizar) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/VistaActualizarReservaHotel.fxml"));
            loader.setControllerFactory(springContext::getBean);
            Parent root = loader.load();

            ActualizarRvaHotelController controlador = loader.getController();
            controlador.displayEmployeeIDUsername(empleadoLogin, reservaActualizar);

            Stage stage = new Stage();
            Scene scene = new Scene(root);

            stage.setTitle("Turism Management System - Actualizar Reserva Hotel");
            stage.setMinHeight(700);
            stage.setMinWidth(1100);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {

            e.printStackTrace();
        }
    }
}
