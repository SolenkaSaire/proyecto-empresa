package co.edu.uniquindio.proyecto.controllers;

import co.edu.uniquindio.proyecto.dto.ArticuloData;
import co.edu.uniquindio.proyecto.dto.CompraArticuloData;
import co.edu.uniquindio.proyecto.dto.ReservaHotelData;
import co.edu.uniquindio.proyecto.model.Compra;
import co.edu.uniquindio.proyecto.model.Empleado;
import co.edu.uniquindio.proyecto.repositories.CompraArticuloRepo;
import co.edu.uniquindio.proyecto.repositories.ReservaHotelRepo;
import com.sun.source.tree.ContinueTree;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

@Controller
public class CompraArticuloController implements Initializable {



    @Autowired
    private CompraArticuloRepo compraArticuloRepo;

    @Autowired
    private SceneController sceneController;

    Empleado empleadoLogin;
    private static final String VALIDACION_DATOS = "Validacion de datos";


    private CompraArticuloData compraArtData;

    private Compra compraSeleccionada;

    public ObservableList<CompraArticuloData> compraArticuloData = FXCollections.observableArrayList();

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");


    @FXML
    private TableColumn<CompraArticuloData, String> carticulos_articulo;

    @FXML
    private TableColumn<CompraArticuloData, Integer> carticulos_cantidad;

    @FXML
    private TableColumn<CompraArticuloData, String> carticulos_categoria;

    @FXML
    private TableColumn<CompraArticuloData, String> carticulos_cliente;

    @FXML
    private TableColumn<CompraArticuloData, String> carticulos_descripcion;

    @FXML
    private TableColumn<CompraArticuloData, String> carticulos_estado;

    @FXML
    private TableColumn<CompraArticuloData, String> carticulos_fechacompra;

    @FXML
    private TableColumn<CompraArticuloData, String> carticulos_id;

    @FXML
    private TableColumn<CompraArticuloData, String> carticulos_metodopago;

    @FXML
    private TableView<CompraArticuloData> carticulos_tableview;

    @FXML
    private TableColumn<CompraArticuloData, Double> carticulos_total;

    @FXML
    private AnchorPane compra_articulo_form;

    @FXML
    private Label current_ventana_lbl;

    @FXML
    private Label fecha_actual_lbl;

    @FXML
    private ComboBox<String> ga_articulo_cbx;

    @FXML
    private Button ga_buscar_btn;

    @FXML
    private TextField ga_cantidad_lbl;

    @FXML
    private ComboBox<String> ga_categoria_cbx;

    @FXML
    private ComboBox<String> ga_cliente_cbx;

    @FXML
    private Button ga_crear_btn;

    @FXML
    private TextArea ga_desc_lbl;

    @FXML
    private Button ga_eliminar_btn;

    @FXML
    private ComboBox<String> ga_metodopago_cbx;

    @FXML
    private Button ga_modificar_btn;

    @FXML
    private Label ga_preciototal_lbl;

    @FXML
    private Label ga_precioun_lbl;

    @FXML
    private TextField ga_search_field;

    @FXML
    private Button menu_cpa_arti_btn;

    @FXML
    private Button menu_cpa_paq_btn;

    @FXML
    private Button menu_rva_autos_btn;

    @FXML
    private Button menu_rva_hotel_btn;

    @FXML
    private Label menu_userID_lbl;

    @FXML
    private Label menu_user_lbl;

    @FXML
    private Button rep_cpa;

    @FXML
    private Button rep_rva;

    @FXML
    private Label username_lbl;


    /**
     * Obtiene los datos de compra de artículos y los muestra en una tabla.
     *
     * @throws Exception si ocurre un error al obtener los datos de compra de artículos.
     */
    public void compraArticuloShowTableData() throws Exception{
        List<Object[]> results = compraArticuloRepo.buscarComprasArticulo();
        List<CompraArticuloData> compraArticuloDataList = new ArrayList<>();

        for (Object[] result : results) {
            CompraArticuloData data = new CompraArticuloData(
                    result[0].toString(),
                    result[1].toString(),
                    result[2].toString(),
                    result[3].toString(),
                    (result[4]).toString(),
                    result[5].toString(),
                    result[6].toString(),
                    (Integer) result[7],
                    (Double) result[8],
                    result[9].toString()
            );
            compraArticuloDataList.add(data);
        }

        carticulos_id.setCellValueFactory(new PropertyValueFactory<>("id_compra"));
        carticulos_cliente.setCellValueFactory(new PropertyValueFactory<>("cedula_cliente"));
        carticulos_categoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        carticulos_articulo.setCellValueFactory(new PropertyValueFactory<>("articulo_turistico"));
        carticulos_fechacompra.setCellValueFactory(new PropertyValueFactory<>("fecha_compra"));
        carticulos_descripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        carticulos_metodopago.setCellValueFactory(new PropertyValueFactory<>("metodo_pago"));
        carticulos_cantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        carticulos_total.setCellValueFactory(new PropertyValueFactory<>("precio_total"));
        carticulos_estado.setCellValueFactory(new PropertyValueFactory<>("estado"));

        ObservableList<CompraArticuloData> compraArticuloData = FXCollections.observableArrayList(compraArticuloDataList);
        carticulos_tableview.setItems(compraArticuloData);
    }




    /**
     * Método que se encarga de cambiar la ventana de acuerdo al botón que se presione.
     *
     * @param event el evento que se genera al presionar un botón.
     * @throws Exception
     */
    /*
    @FXML
    void switchForm(ActionEvent event)  throws Exception{

        if(event.getSource() == menu_rva_autos_btn){
            abrirVentanaAutoReserva(event, empleadoLogin);
        }else if(event.getSource() == menu_cpa_arti_btn){
            abrirVentanaCompraArticulo(event, empleadoLogin);
        }else if(event.getSource() == menu_cpa_paq_btn){
            abrirVentanaCompraPaquete(event, empleadoLogin);
        }
    }


    private void abrirVentanaAutoReserva(ActionEvent event, Empleado empleado) {
        sceneController.cambiarAVentanaReservaHotel(event, empleado);
    }

    private void abrirVentanaCompraArticulo(ActionEvent event, Empleado empleado) {
        sceneController.cambiarAVentanaCompraArticulo(event, empleado);
    }

    private void abrirVentanaCompraPaquete(ActionEvent event, Empleado empleado) {
        sceneController.cambiarAVentanaCompraPaquete(event, empleado);
    }*/




    /**
     * Muestra el nombre y el código de un empleado en la interfaz de usuario.
     *
     * @param empleado el objeto Empleado que contiene la información del empleado.
     */
    public void displayEmployeeIDUsername(Empleado empleado){
        empleadoLogin = empleado;
        username_lbl.setText(empleado.getPersona().getNombre());
        menu_userID_lbl.setText(String.valueOf(empleado.getCodigoEmpleado()));
        menu_user_lbl.setText(empleado.getPersona().getNombre());
    }

    @FXML
    void comboPaisHotelListener(ActionEvent event) {

    }



    @FXML
    void switchForm(javafx.event.ActionEvent event)  throws Exception{

        if(event.getSource() == menu_rva_hotel_btn){
            abrirVentanaReservaHotel(event, empleadoLogin);
            menu_rva_hotel_btn.getScene().getWindow().hide();
        }else if(event.getSource() == menu_cpa_paq_btn){
            abrirVentanaCompraPaquete(event, empleadoLogin);
            menu_cpa_paq_btn.getScene().getWindow().hide();
        }else if(event.getSource() == menu_rva_autos_btn){
            abrirVentanaAutoReserva(event, empleadoLogin);
            menu_rva_autos_btn.getScene().getWindow().hide();
        }
    }



    private void abrirVentanaReservaHotel(javafx.event.ActionEvent event, Empleado empleado) {
        sceneController.cambiarAVentanaReservaHotel(event, empleado);
    }

    private void abrirVentanaCompraPaquete(ActionEvent event, Empleado empleado) {
        sceneController.cambiarAVentanaCompraPaquete(event, empleado);
    }

    private void abrirVentanaAutoReserva(javafx.event.ActionEvent event, Empleado empleado) {
        sceneController.cambiarAVentanaReservaAuto(event, empleado);
    }


    /**
     * Inicia un nuevo hilo que actualiza la etiqueta fecha_actual_lbl con la fecha y hora actuales cada segundo.
     */
    public void runTime(){
        new Thread(){
            public void run(){
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss a");
                while(true){
                    try{
                        Thread.sleep(1000);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    Platform.runLater(() -> {
                        Date date = new Date(System.currentTimeMillis());
                        fecha_actual_lbl.setText(format.format(date));
                    });
                }
            }
        }.start();
    }

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        runTime();
        try {
            compraArticuloShowTableData();
          //  reservaSearch();

          //  cargarComboPaisHotel();
          //  cargarComboClienteHotel();
          //  habitacionesShowTableData();
          //  cargarComboHoteles();
          //  cargarDatePicker();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void compraArticuloSelectData(MouseEvent event){
        compraArtData = carticulos_tableview.getSelectionModel().getSelectedItem();

        if(compraArtData!=null){
            compraSeleccionada = compraArticuloRepo.obtener(Integer.parseInt(compraArtData.getId_compra()));
            System.out.println("compra seleccionada: " + compraSeleccionada.toString());
        }

        int num = carticulos_tableview.getSelectionModel().getSelectedIndex();
        if((num -1) < -1){
            return;
        }

    }

    @FXML
    void crearBtn(ActionEvent actionEvent) {
        abrirVentanaCrearCompraArticulo(actionEvent, empleadoLogin);
        ga_crear_btn.getScene().getWindow().hide();

    }

    private void abrirVentanaCrearCompraArticulo(ActionEvent event, Empleado empleado) {
        sceneController.cambiarAVentanaCrearCompraArticulo(event, empleado);
    }



    @FXML
    void modificarBtn(ActionEvent actionEvent) {
        if(compraArtData != null){
            abrirVentanaModificarCompraArticulo(actionEvent, empleadoLogin, compraArtData);
            ga_modificar_btn.getScene().getWindow().hide();
        }else{
            mostrarMensaje(VALIDACION_DATOS, VALIDACION_DATOS, "Por favor, seleccione una reserva para actualizar",
                    Alert.AlertType.WARNING);
        }
    }

    private void abrirVentanaModificarCompraArticulo(ActionEvent actionEvent, Empleado empleadoLogin, CompraArticuloData compraArtData) {
        sceneController.cambiarAVentanaModificarCompraArticulo(actionEvent, empleadoLogin, compraArtData);
    }

    private void mostrarMensaje(String titulo, String header, String contenido, Alert.AlertType information) {
        Alert alert = new Alert(information);
        alert.setTitle(titulo);
        alert.setHeaderText(header);
        alert.setContentText(contenido);
        alert.showAndWait();
    }

    @FXML
    void buscarBtn(ActionEvent actionEvent) {
    }

    @FXML
    void eliminarBtn(ActionEvent actionEvent) {
    }
}
