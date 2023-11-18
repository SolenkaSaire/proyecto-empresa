package co.edu.uniquindio.proyecto.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import co.edu.uniquindio.proyecto.model.Empleado;
import co.edu.uniquindio.proyecto.repositories.EmpleadoRepo;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component

public class LoginController implements Initializable{
    @FXML
    private Button btn_crearCuenta;

    @FXML
    private Button btn_ingresar;

    @FXML
    private Button btn_registrar;

    @FXML
    private Button btn_yatengo;

    @FXML
    private Label lbl_notienes;

    @FXML
    private Label lbl_yatienes;

    @FXML
    private TextField lo_cedula;

    @FXML
    private TextField lo_email;

    @FXML
    private PasswordField lo_password;

    @FXML
    private ComboBox<String> lo_rol;

    @FXML
    private AnchorPane loginForm;

    @FXML
    private TextField re_cedula;

    @FXML
    private TextField re_direccion;

    @FXML
    private TextField re_email;

    @FXML
    private TextField re_nombre;

    @FXML
    private PasswordField re_password;

    @FXML
    private ComboBox<String> re_rol;

    @FXML
    private TextField re_telefono;

    @FXML
    private AnchorPane side_form;

    @FXML
    private AnchorPane signupform;

    @Autowired
    private EmpleadoRepo empleadoRepo;

    private String[] rolesList = {"Cliente", "Empleado"};

    private Alert alert;

    @Autowired
    private SceneController sceneController;


    @FXML
    void loginBtn(ActionEvent event){
        if(lo_cedula.getText().isEmpty() || lo_email.getText().isEmpty() || lo_password.getText().isEmpty() ||
                lo_rol.getSelectionModel().getSelectedItem() == null){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error al iniciar sesión");
            alert.setContentText("Por favor llene todos los campos");
            alert.showAndWait();
        }else if(lo_rol.getSelectionModel().getSelectedItem() == "Empleado"){//CONSULTAS PARA LOGIN EMPLEADO
            try{

                Empleado empleado = empleadoRepo.findByCuenta(lo_cedula.getText(), lo_email.getText());
                if(empleado==null || empleado.getPersona().getNombre() == null || !lo_password.getText().equals("12345")){
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Error al iniciar sesión");
                    alert.setContentText("Contraseña o usuario incorrecto");
                    alert.showAndWait();
                    return;
                }else if(empleado.getPersona().getNombre() != null){
                    // Do something with empleadoDTO
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Información");
                    alert.setHeaderText("Inicio de sesión exitoso");
                    alert.setContentText("Bienvenido "+empleado.getPersona().getNombre());
                    alert.showAndWait();

                    abrirVentanaReservaHotel(event, empleado);
                    btn_ingresar.getScene().getWindow().hide();




                } else {
                    // Handle case where empleadoDTO is null
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Error al iniciar sesión");
                    alert.setContentText("Por favor verifique sus datos");
                    alert.showAndWait();
                }
            }catch(Exception e){
                e.printStackTrace();
            }

        }else{ //CONSULTAS PARA LOGIN CLIENTE
            //String loginData = "SELECT * FROM ";

        }
    }

    private void abrirVentanaReservaHotel(ActionEvent event, Empleado empleado) {
        sceneController.cambiarAVentanaReservaHotel(event, empleado);
    }


    public void registroBtn(){
        if(re_cedula.getText().isEmpty() || re_direccion.getText().isEmpty() || re_email.getText().isEmpty() ||
                re_nombre.getText().isEmpty() || re_password.getText().isEmpty() || re_telefono.getText().isEmpty() ||
                re_rol.getSelectionModel().getSelectedItem() == null){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error al registrar");
            alert.setContentText("Por favor llene todos los campos");
            alert.showAndWait();
        }else{
            //CONSULTAS PARA REGISTRO
            // String regData ="INSERT INTO ";

        }
    }


    /**
     * Método que solicita la lista de roles y la asigna a los elementos de la interfaz gráfica correspondientes.
     * FILEPATH: /src/main/java/co/edu/uniquindio/empresa/controllers/LoginController.java
     */
    public void requestRolesList(){
        List<String> listR = new ArrayList<String>();

        for(String dato: rolesList){
            listR.add(dato);
        }

        ObservableList<String> listData = FXCollections.observableArrayList(listR);
        lo_rol.setItems(listData);
        re_rol.setItems(listData);
    }



    @FXML
    void switchForm(ActionEvent event){

        TranslateTransition slider = new TranslateTransition();

        if(event.getSource() == btn_crearCuenta){
            slider.setNode(side_form);
            slider.setToX(400);
            slider.setDuration(Duration.seconds(0.5));

            slider.setOnFinished((ActionEvent e) ->{
                btn_yatengo.setVisible(true);

                lbl_yatienes.setVisible(true);

                lbl_notienes.setVisible(false);

                btn_crearCuenta.setVisible(false);

            });

            slider.play();
        }else if(event.getSource() == btn_yatengo){
            slider.setNode(side_form);
            slider.setToX(0);
            slider.setDuration(Duration.seconds(0.5));

            slider.setOnFinished((ActionEvent e) ->{
                btn_yatengo.setVisible(false);

                lbl_yatienes.setVisible(false);

                lbl_notienes.setVisible(true);

                btn_crearCuenta.setVisible(true);
            });

            slider.play();
        }

    }


    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        if (empleadoRepo == null) {
            System.err.println("El repositorio es nulo.");
            // Puedes agregar lógica para manejar este caso, como lanzar una excepción o salir.
            return;
        }
        System.out.println("EmpleadoRepo: " + empleadoRepo);

        requestRolesList();
    }

}
