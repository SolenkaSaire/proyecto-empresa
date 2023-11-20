package co.edu.uniquindio.proyecto.controllers;

import co.edu.uniquindio.proyecto.dto.CompraArticuloData;
import co.edu.uniquindio.proyecto.dto.CompraPaqueteData;
import co.edu.uniquindio.proyecto.dto.ReservaHotelData;
import co.edu.uniquindio.proyecto.model.*;
import co.edu.uniquindio.proyecto.repositories.CompraPaqueteRepo;
import co.edu.uniquindio.proyecto.repositories.ReservaHotelRepo;
import jakarta.persistence.criteria.CriteriaBuilder;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class CompraPaqueteController  implements Initializable {


    @FXML
    private AnchorPane compra_paquete_form;

    @FXML
    private TableColumn<CompraPaqueteData, Integer> cpaquetes_cantidad;

    @FXML
    private TableColumn<CompraPaqueteData, String> cpaquetes_centro;

    @FXML
    private TableColumn<CompraPaqueteData, String> cpaquetes_cliente;

    @FXML
    private TableColumn<CompraPaqueteData, String> cpaquetes_estado;

    @FXML
    private TableColumn<CompraPaqueteData, String> cpaquetes_fechacomp;

    @FXML
    private TableColumn<CompraPaqueteData, String> cpaquetes_fechapaq;

    @FXML
    private TableColumn<CompraPaqueteData, String> cpaquetes_id;

    @FXML
    private TableColumn<CompraPaqueteData, String> cpaquetes_metodopago;

    @FXML
    private TableColumn<CompraPaqueteData, String> cpaquetes_paquete;

    @FXML
    private TableView<CompraPaqueteData> cpaquetes_tableview;

    @FXML
    private TableColumn<CompraPaqueteData, Double> cpaquetes_total;

    @FXML
    private Label current_ventana_lbl;

    @FXML
    private Label fecha_actual_lbl;

    @FXML
    private Button gp_buscar_btn;

    @FXML
    private TextField gp_cantidad_lbl;

    @FXML
    private ComboBox<String> gp_ciudad_cbx;

    @FXML
    private ComboBox<String> gp_cliente_cbx;

    @FXML
    private Button gp_crear_btn;

    @FXML
    private TextArea gp_descripcioncompra_text;

    @FXML
    private TextArea gp_detalles_lbl;

    @FXML
    private Button gp_eliminar_btn;

    @FXML
    private TextField gp_fechapaquete_lbl;

    @FXML
    private ComboBox<String> gp_metodopago_cbx;

    @FXML
    private Button gp_modificar_btn;

    @FXML
    private ComboBox<String> gp_pais_cbx;

    @FXML
    private ComboBox<String> gp_paquete_cbx;

    @FXML
    private TextField gp_plazocancel_lbl;

    @FXML
    private TextField gp_politicadnto_lbl;

    @FXML
    private Label gp_preciodnto_lbl;

    @FXML
    private Label gp_preciototal_lbl;

    @FXML
    private Label gp_precioun_lbl;

    @FXML
    private TextField gp_search_field;

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




    @FXML
    void comboPaisHotelListener(ActionEvent event) {

    }



    private static final String VALIDACION_DATOS = "Validacion de datos";


    @Autowired
    private SceneController sceneController;


    private Empleado empleadoLogin;

    private CompraPaqueteData compraPaqueteDataSeleccionada;

    private Compra compraSeleccionada;




    @Autowired
    private CompraPaqueteRepo compraPaqueteRepo;
    public ObservableList<CompraPaqueteData> compraPaqueteData = FXCollections.observableArrayList();
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public void compraPaqueteShowTableData() throws Exception {
        List<Object[]> results = compraPaqueteRepo.buscarCompraPaquete();
        List<CompraPaqueteData> compraPaqueteDataList = new ArrayList<>();

        for (Object[] result : results) {
            CompraPaqueteData data = new CompraPaqueteData(
                    String.valueOf(result[0]),         // id_compra
                    String.valueOf(result[1]),         // cedula_cliente
                    (String) result[2],                // paquete
                    (result[3]).toString(), // fecha_paquete
                    (result[4]).toString(), // fecha_compra
                    (String) result[5],                // centro_turistico
                    (String) result[6],                // metodo_pago
                    (Integer) result[7],                // cantidad_boletas
                    (Double) result[8],                 // precio_total
                    (String) result[9]                 // estado
            );
            compraPaqueteDataList.add(data);
        }

        compraPaqueteData = FXCollections.observableArrayList(compraPaqueteDataList);

        cpaquetes_id.setCellValueFactory(new PropertyValueFactory<>("id_compra"));
        cpaquetes_cliente.setCellValueFactory(new PropertyValueFactory<>("cedula_cliente"));
        cpaquetes_paquete.setCellValueFactory(new PropertyValueFactory<>("paquete"));
        cpaquetes_fechapaq.setCellValueFactory(new PropertyValueFactory<>("fecha_paquete"));
        cpaquetes_fechacomp.setCellValueFactory(new PropertyValueFactory<>("fecha_compra"));
        cpaquetes_centro.setCellValueFactory(new PropertyValueFactory<>("centro_turistico"));
        cpaquetes_metodopago.setCellValueFactory(new PropertyValueFactory<>("metodo_pago"));
        cpaquetes_cantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad_boletas"));
        cpaquetes_total.setCellValueFactory(new PropertyValueFactory<>("precio_total"));
        cpaquetes_estado.setCellValueFactory(new PropertyValueFactory<>("estado"));

        cpaquetes_tableview.setItems(compraPaqueteData);
    }

    @FXML
    void compraPaqueteSelectData(MouseEvent event) {
        compraPaqueteDataSeleccionada = cpaquetes_tableview.getSelectionModel().getSelectedItem();
        CompraPaqueteData compraPaquete = cpaquetes_tableview.getSelectionModel().getSelectedItem();
     //   System.out.println("compra seleccionada: " + compraSeleccionada.getIdCompra());

        if(compraPaqueteDataSeleccionada!=null){
            int id = Integer.parseInt(compraPaquete.getId_compra());
            compraSeleccionada = compraPaqueteRepo.obtener(id);
            System.out.println("compra seleccionada: " + compraSeleccionada.getIdCompra());
        }

        int num = cpaquetes_tableview.getSelectionModel().getSelectedIndex();
        if ((num - 1) < -1) {
            return;
        }

        /*
        String fechaPaquete = compraPaquete.getFecha_paquete().substring(0, 10);
        String fechaCompra = compraPaquete.getFecha_compra().substring(0, 10);

        LocalDate localFechaPaquete = LocalDate.parse(fechaPaquete, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate localFechaCompra = LocalDate.parse(fechaCompra, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        // Ajusta las referencias de los ComboBox y las propiedades de CompraPaqueteData
        String cedulaCliente = compraPaquete.getCedula_cliente();
        for (String item : gp_cliente_cbx.getItems()) {
            if (item.contains("Cedula: " + cedulaCliente)) {
                gp_cliente_cbx.setValue(item);
                break;
            }
        }

        String paquete = compraPaquete.getPaquete();
        for (String item : gp_paquete_cbx.getItems()) {
            if (item.contains("Descripcion: " + paquete)) {
                gp_paquete_cbx.setValue(item);
                break;
            }
        }

        String centroTuristico = compraPaquete.getCentro_turistico();
        for (String item : gp_ciudad_cbx.getItems()) {
            if (item.contains("Nombre: " + centroTuristico)) {
                gp_ciudad_cbx.setValue(item);
                break;
            }
        }

        gp_fechapaquete_lbl.setText(localFechaPaquete.toString());
        // gp_fechacompra_lbl.setText(localFechaCompra.toString());
        gp_cantidad_lbl.setText(Integer.toString(compraPaquete.getCantidad_boletas()));

        Double precioTotal = compraPaquete.getPrecio_total();
        gp_preciototal_lbl.setText(String.valueOf(precioTotal));

        gp_detalles_lbl.setText(compraPaquete.getEstado());

        System.out.println("index es " + compraPaquete.getId_compra());*/
    }



    @FXML
    void switchForm(javafx.event.ActionEvent event)  throws Exception{

        if(event.getSource() == menu_rva_hotel_btn){
            abrirVentanaReservaHotel(event, empleadoLogin);
            menu_rva_hotel_btn.getScene().getWindow().hide();
        }else if(event.getSource() == menu_cpa_arti_btn){
            abrirVentanaCompraArticulo(event, empleadoLogin);
            menu_cpa_arti_btn.getScene().getWindow().hide();
        }else if(event.getSource() == menu_rva_autos_btn){
            abrirVentanaAutoReserva(event, empleadoLogin);
            menu_rva_autos_btn.getScene().getWindow().hide();
        }
    }



    private void abrirVentanaReservaHotel(javafx.event.ActionEvent event, Empleado empleado) {
        sceneController.cambiarAVentanaReservaHotel(event, empleado);
    }

    private void abrirVentanaCompraArticulo(javafx.event.ActionEvent event, Empleado empleado) {
        sceneController.cambiarAVentanaCompraArticulo(event, empleado);
    }

    private void abrirVentanaAutoReserva(javafx.event.ActionEvent event, Empleado empleado) {
        sceneController.cambiarAVentanaReservaAuto(event, empleado);
    }

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
    void comboPaisHotelListener(javafx.event.ActionEvent event) {

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
            compraPaqueteShowTableData();
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
/*

    @FXML
    void compraPaqueteSearch(ActionEvent event) {

    }*/
    @FXML
    public void compraPaqueteSearch(javafx.event.ActionEvent actionEvent) {

        FilteredList<CompraPaqueteData> filter = new FilteredList<>(compraPaqueteData, p -> true);

        gp_search_field.textProperty().addListener((Observable, oldValue, newValue) -> {
            filter.setPredicate(compraPaquete -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (compraPaquete.getId_compra().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (compraPaquete.getCedula_cliente().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (compraPaquete.getPaquete().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (compraPaquete.getFecha_paquete().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (compraPaquete.getFecha_compra().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (compraPaquete.getCentro_turistico().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (compraPaquete.getMetodo_pago().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (Integer.toString(compraPaquete.getCantidad_boletas()).contains(lowerCaseFilter)) {
                    return true;
                } else if (Double.toString(compraPaquete.getPrecio_total()).contains(lowerCaseFilter)) {
                    return true;
                } else if (compraPaquete.getEstado().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else {
                    return false;
                }
            });
        });

        SortedList<CompraPaqueteData> sortedData = new SortedList<>(filter);
        sortedData.comparatorProperty().bind(cpaquetes_tableview.comparatorProperty());
        cpaquetes_tableview.setItems(sortedData);
    }

    @FXML
    void crearBtn(javafx.event.ActionEvent actionEvent) {
        abrirVentanaCrearCompraPaquete(actionEvent, empleadoLogin);
        gp_crear_btn.getScene().getWindow().hide();

    }

    private void abrirVentanaCrearCompraPaquete(javafx.event.ActionEvent event, Empleado empleado) {
        sceneController.cambiarAVentanaCrearCompraPaquete(event, empleado);
    }

    @FXML
    void modificarBtn(javafx.event.ActionEvent actionEvent) {
        //validar que se haya seleccionado una reserva
        if (compraPaqueteDataSeleccionada != null) {
            //actualizarReservaHotel();
            abrirVentanaActualizarCompraPaquete(actionEvent, empleadoLogin, compraPaqueteDataSeleccionada);
            gp_crear_btn.getScene().getWindow().hide();
        } else {
            mostrarMensaje(VALIDACION_DATOS, VALIDACION_DATOS, "Por favor, seleccione una reserva para actualizar",
                    Alert.AlertType.WARNING);
        }

    }




    private void abrirVentanaActualizarCompraPaquete(javafx.event.ActionEvent event, Empleado empleado, CompraPaqueteData compraPaqueteDataSeleccionada) {
        sceneController.cambiarAVentanaActualizarCompraPaquete(event, empleado, compraPaqueteDataSeleccionada);
    }



    private void mostrarMensaje(String titulo, String header, String contenido, Alert.AlertType information) {
        Alert alert = new Alert(information);
        alert.setTitle(titulo);
        alert.setHeaderText(header);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
    @FXML
    void buscarBtn(javafx.event.ActionEvent actionEvent) {
    }

    @FXML
    void eliminarBtn(javafx.event.ActionEvent actionEvent) {
    }


}



