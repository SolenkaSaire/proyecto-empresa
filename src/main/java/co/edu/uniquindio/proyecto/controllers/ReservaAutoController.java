package co.edu.uniquindio.proyecto.controllers;

//import co.edu.uniquindio.proyecto.dto.data.ReservaAutoData;
import co.edu.uniquindio.proyecto.dto.ReservaAutoData;
import co.edu.uniquindio.proyecto.dto.ServiciosAdicionalesData;
import co.edu.uniquindio.proyecto.model.*;
import co.edu.uniquindio.proyecto.repositories.DetalleReservaAutoRepo;
import co.edu.uniquindio.proyecto.repositories.ReservaAutoRepo;
import co.edu.uniquindio.proyecto.repositories.ServiciosAdicionalesRepo;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import co.edu.uniquindio.proyecto.dto.HabitacionHotelData;
import co.edu.uniquindio.proyecto.dto.ReservaHotelData;
import co.edu.uniquindio.proyecto.model.Empleado;
import co.edu.uniquindio.proyecto.model.Habitacion;
import co.edu.uniquindio.proyecto.model.RegimenHospedaje;
import co.edu.uniquindio.proyecto.model.ReservaHotel;
import co.edu.uniquindio.proyecto.repositories.ReservaHotelRepo;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import java.awt.*;
import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;


@Controller
public class ReservaAutoController  implements Initializable {
    @Autowired
    private SceneController sceneController;
    @Autowired
    private ReservaAutoRepo reservaAutoRepo;

    private ReservaAutomovil reservaSeleccionada;

    @Autowired
    private ServiciosAdicionalesRepo serviciosAdicionalesRepo;

    @Autowired
    private DetalleReservaAutoRepo detalleReservaAutomovilRepo;

    private ReservaAutoData reserva;
    private Empleado empleadoLogin;


    private static final String VALIDACION_DATOS = "Validacion de datos";

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public ObservableList<ReservaAutoData> reservaAutoData = FXCollections.observableArrayList();
    @FXML
    private Label current_ventana_lbl;
    @FXML
    private Label fecha_actual_lbl;
    @FXML
    private Button ga_agregarS_btn;
    @FXML
    private ComboBox<String> ga_auto_cbx;
    @FXML
    private Button ga_buscar_btn;

    @FXML
    private TextField ga_cantidad_lbl;

    @FXML
    private ComboBox<String> ga_cliente_cbx;

    @FXML
    private Button ga_crear_btn;

    @FXML
    private TextField ga_destino_lbl;

    @FXML
    private Button ga_eliminar_btn;

    @FXML
    private DatePicker ga_fechadev_date;

    @FXML
    private DatePicker ga_fechainicio_date;

    @FXML
    private ComboBox<String> ga_gama_cbx;

    @FXML
    private ComboBox<String> ga_marca_cbx;

    @FXML
    private Button ga_modificar_btn;

    @FXML
    private TextField ga_origen_lbl;

    @FXML
    private TextField ga_preciodia_lbl;

    @FXML
    private Label ga_preciototal_lbl;

    @FXML
    private Button ga_quitarS_btn;

    @FXML
    private TextField ga_search_field;

    @FXML
    private ComboBox<String> ga_servicio_cbx;

    @FXML
    private TableView<ServiciosAdicionalesData> ga_servicios_tableview;

    @FXML
    private ComboBox<String> ga_tipo_cbx;

    @FXML
    private Label ga_totaldias_lbl;

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
    private AnchorPane reserva_automovil_form;
    @FXML
    private TableColumn<ReservaAutoData, String> rvautos_auto;

    @FXML
    private TableColumn<ReservaAutoData, Integer> rvautos_cantidad_au;

    @FXML
    private TableColumn<ReservaAutoData, String> rvautos_cliente;

    @FXML
    private TableColumn<ReservaAutoData, String> rvautos_destino;

    @FXML
    private TableColumn<ReservaAutoData, String> rvautos_fecha;

    @FXML
    private TableColumn<ReservaAutoData, String> rvautos_id;

    @FXML
    private TableColumn<ReservaAutoData, String> rvautos_origen;

    @FXML
    private TableColumn<ReservaAutoData, String> rvautos_servicios;

    @FXML
    private TableView<ReservaAutoData> rvautos_tableview;
    @FXML
    private TableColumn<ReservaAutoData, Double> rvautos_total;
    @FXML
    private TableColumn<ServiciosAdicionalesData, String> servicios_id;
    @FXML
    private TableColumn<ServiciosAdicionalesData, String> servicios_tipo;
    @FXML
    private TableColumn<ServiciosAdicionalesData, String> servicios_desc;
    @FXML
    private TableColumn<ServiciosAdicionalesData, Double> servicios_precio;
    @FXML
    private Label username_lbl;

    private Map<String, Integer> mapaPaises = new HashMap<>();
    private HashMap<String, String> mapaCiudades;
    private HashMap<String, String[]> mapaClientes;
    private HashMap<String, String[]> mapaHoteles;
    private HashMap<String, String[]> mapaRegimenes;



    @FXML
    void eliminar(ActionEvent event) {

        eliminarReserva();
    }

    private void eliminarReserva() {

        boolean confirmacion;

        if (reservaSeleccionada != null) {
            confirmacion = mostrarMensajeConfirmacion("Confirme que desea eliminar la reserva seleccionada");
            if (confirmacion) {

                //encontrar todos los servicios adicionales asociados a la reserva seleccionada
                List<ServiciosAdicionales> serviciosAdicionales = reservaAutoRepo.getServiciosAdicionales(reservaSeleccionada.getIdReservaAutomovil());
                int sizeSev = serviciosAdicionales.size();

                //encontrar todos los detalles reserva automovil asociados a la reserva seleccionada
                List<DetalleReservaAutomovil> detallesReservaAutomovil = reservaAutoRepo.getDetallesReservaAutomovil(reservaSeleccionada.getIdReservaAutomovil());
                int sizeDet = detallesReservaAutomovil.size();

                //warning de confirmacion, se eliminara 1 reserva, sizeSev servicios y sizeDet detalles reserva automovil
                mostrarMensaje(VALIDACION_DATOS, VALIDACION_DATOS, "Se eliminará 1 reserva, " + sizeSev + " servicios adicionales y " + sizeDet + " detalles reserva automovil\n" +
                                "¿Desea continuar?",
                        Alert.AlertType.WARNING);

                //eliminar todos los servicios adicionales asociados a la reserva seleccionada
                for (ServiciosAdicionales serviciosAdicional : serviciosAdicionales) {
                        serviciosAdicionalesRepo.delete(serviciosAdicional);
                }

                //eliminar todos los detalles reserva automovil asociados a la reserva seleccionada
                for (DetalleReservaAutomovil detalleReservaAutomovil : detallesReservaAutomovil) {
                        detalleReservaAutomovilRepo.delete(detalleReservaAutomovil);
                }

                //elimina la reserva
                reservaAutoRepo.delete(reservaSeleccionada);

                reservaSeleccionada = null;
                mostrarMensaje(VALIDACION_DATOS, VALIDACION_DATOS, "Reserva eliminada con éxito",
                        Alert.AlertType.INFORMATION);


                /*
                // Encuentra todas las habitaciones asociadas con la reserva
                List<HabitacionReserva> habitacionesReserva = habitacionReservaRepo.findByReservaId(Integer.parseInt(reserva.getId_reserva()));

                // Elimina todas las habitaciones asociadas con la reserva
                for (HabitacionReserva habitacionReserva : habitacionesReserva) {
                    habitacionReservaRepo.delete(habitacionReserva);
                }

                // Elimina cancelacion hospedaje asociado
                CancelacionHospedaje cancelacionHospedaje = reservaHotelRepo.buscarCancelacionHospedaje(Integer.parseInt(reserva.getId_reserva()));
                if (cancelacionHospedaje != null) {
                    cancelacionHospedajeRepo.delete(cancelacionHospedaje);
                }

                // Elimina la reserva
                reservaHotelRepo.delete(reservaSeleccionada);

                //  limpiarInterfazReservaHotel();

                //limpiar habitacionSeleccionada y reservaSeleccionada
                selectedHabitacion = null;
                reservaSeleccionada = null;
                mostrarMensaje(VALIDACION_DATOS, VALIDACION_DATOS, "Reserva eliminada con éxito",
                        Alert.AlertType.INFORMATION);


                 */
            }

        } else {
            mostrarMensaje(VALIDACION_DATOS, VALIDACION_DATOS, "No ha seleccionado una reserva para eliminar",
                    Alert.AlertType.WARNING);
        }


    }


    private boolean mostrarMensajeConfirmacion(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("Confirmación");
        alert.setContentText(mensaje);
        Optional<ButtonType> action = alert.showAndWait();
        if (action.isPresent()) {
            return (action.get() == ButtonType.OK);
        } else {
            return false;
        }
    }
/*
    @FXML
    void reservkaSearch(ActionEvent event) {
        FilteredList<ReservaHotelData> filter = new FilteredList<>(reservaHotelData, p -> true);

        gr_search_field.textProperty().addListener((Observable, oldValue, newValue) -> {
            filter.setPredicate(reserva -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (reserva.getId_reserva().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (reserva.getCedula_cliente().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (reserva.getHotel().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (reserva.getRegimen_hospedaje().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (reserva.getFecha_reserva().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (reserva.getFecha_checkin().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (reserva.getFecha_checkout().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (Double.toString(reserva.getTotal_con_impuesto()).contains(lowerCaseFilter)) {
                    return true;
                /*} else if (Integer.toString(reserva.getCantidad_habitaciones()).contains(lowerCaseFilter)) {
                    return true;
                } else if (reserva.getEstado().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else
                    return false;
            });
        });

        SortedList<ReservaHotelData> sortedData = new SortedList<>(filter);
        sortedData.comparatorProperty().bind(reserva_tableview.comparatorProperty());
        reserva_tableview.setItems(sortedData);

    }

*/

    public void reservaAutoShowTableData() throws Exception{
        List<Object[]> results= reservaAutoRepo.buscarReservasAutomovil();
        List<ReservaAutoData> reservaAutoDataList = new ArrayList<>();

        for(Object[] result : results){
            Double total = result[7] != null ? ((Double) result[7]).doubleValue() : 0.0;
            Integer cantidadAutos = result[8] != null ? ((Integer) result[8]).intValue() : 0;
            ReservaAutoData data = new ReservaAutoData(
                    String.valueOf(result[0]), // idReservaAuto
                    String.valueOf(result[1]), // idCliente
                    String.valueOf(result[2]), // auto
                    String.valueOf(result[3]), // servicios
                    String.valueOf(result[4]), // fechaReserva
                    String.valueOf(result[5]), // origen
                    String.valueOf(result[6]), // destino
                    total, // total
                    cantidadAutos // cantidad_auto
            );
            reservaAutoDataList.add(data);
        }

        ObservableList<ReservaAutoData> reservaAutoData = FXCollections.observableArrayList(reservaAutoDataList);

        rvautos_id.setCellValueFactory(new PropertyValueFactory<>("id_reserva"));
        rvautos_cliente.setCellValueFactory(new PropertyValueFactory<>("cedula_cliente"));
        rvautos_auto.setCellValueFactory(new PropertyValueFactory<>("auto"));
        rvautos_servicios.setCellValueFactory(new PropertyValueFactory<>("servicios_adicionales"));
        rvautos_fecha.setCellValueFactory(new PropertyValueFactory<>("fecha_reserva"));
        rvautos_origen.setCellValueFactory(new PropertyValueFactory<>("lugar_origen"));
        rvautos_destino.setCellValueFactory(new PropertyValueFactory<>("lugar_destino"));
        rvautos_total.setCellValueFactory(new PropertyValueFactory<>("precio_total"));
        rvautos_cantidad_au.setCellValueFactory(new PropertyValueFactory<>("cantidad_autos"));

        rvautos_tableview.setItems(reservaAutoData);
    }

/*
    @FXML
    void reservaSearch(ActionEvent event) {

    }*/

    @FXML
    void switchForm(ActionEvent event)  throws Exception{

        if(event.getSource() == menu_rva_hotel_btn){
            abrirVentanaReservaHotel(event, empleadoLogin);
            menu_rva_hotel_btn.getScene().getWindow().hide();
        }else if(event.getSource() == menu_cpa_arti_btn){
            abrirVentanaCompraArticulo(event, empleadoLogin);
            menu_cpa_arti_btn.getScene().getWindow().hide();
        }else if(event.getSource() == menu_cpa_paq_btn){
            abrirVentanaCompraPaquete(event, empleadoLogin);
            menu_cpa_paq_btn.getScene().getWindow().hide();
        }
    }



    private void abrirVentanaReservaHotel(ActionEvent event, Empleado empleado) {
        sceneController.cambiarAVentanaReservaHotel(event, empleado);
    }

    private void abrirVentanaCompraArticulo(ActionEvent event, Empleado empleado) {
        sceneController.cambiarAVentanaCompraArticulo(event, empleado);
    }

    private void abrirVentanaCompraPaquete(ActionEvent event, Empleado empleado) {
        sceneController.cambiarAVentanaCompraPaquete(event, empleado);
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
    void setReservaAutoData(MouseEvent event){
        /*
        ReservaAutoData reserva = rvautos_tableview.getSelectionModel().getSelectedItem();

        int num= rvautos_tableview.getSelectionModel().getSelectedIndex();
        if((num -1) < -1){
            return;
        }

        String fechaInicio= reserva.getFechaReserva().substring(0,10);
        String fechaDevolucion= reserva.getFechaDevolucion().substring(0,10);
        LocalDate localinicio = LocalDate.parse(fechaInicio, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate localDev = LocalDate.parse(fechaDevolucion, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        String cedulaCliente = reserva.getIdCliente();
        for (String item : ga_auto_cbx.getItems()) {
            if (item.contains("Cedula: " + cedulaCliente)) {
                ga_cliente_cbx.setValue(item);
                break;
            }
        }
        String autoReserva = reserva.getAuto();
        for (String item : ga_auto_cbx.getItems()) {
            if (item.contains("Nombre: " + autoReserva)) {
                ga_auto_cbx.setValue(item);
                break;
            }
        }
/*
        ga_fechainicio_date.setValue(localinicio);
        ga_fechadev_date.setValue(localDev);
        ga_cantidad_lbl.setText(Integer.toString(reserva.));
*/





    }




    @FXML
    void reservaAutoSelectData(MouseEvent event) {
        reserva = rvautos_tableview.getSelectionModel().getSelectedItem();

        if (reserva != null) {
            reservaSeleccionada = reservaAutoRepo.findById(Integer.valueOf(reserva.getId_reserva())).get();
            System.out.println("reserva seleccionada: " + reservaSeleccionada.toString());
        }
        int num = rvautos_tableview.getSelectionModel().getSelectedIndex();
        if ((num - 1) < -1) {
            return;
        }

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
    void crearBtn(ActionEvent event) {
        abrirVentanaCrearReservaAuto(event, empleadoLogin);
        ga_crear_btn.getScene().getWindow().hide();

    }

    private void abrirVentanaCrearReservaAuto(ActionEvent event, Empleado empleado) {
        sceneController.cambiarAVentanaCrearReservaAuto(event, empleado);
    }


    @FXML
    void actualizarBtn(ActionEvent event) {
        //validar que se haya seleccionado una reserva
        if (reserva != null) {
            //actualizarReservaHotel();
            abrirVentanaActualizarReservaAuto(event, empleadoLogin, reserva);
            ga_modificar_btn.getScene().getWindow().hide();
        } else {
            mostrarMensaje(VALIDACION_DATOS, VALIDACION_DATOS, "Por favor, seleccione una reserva para actualizar",
                    Alert.AlertType.WARNING);
        }

    }

    private void abrirVentanaActualizarReservaAuto(ActionEvent event, Empleado empleadoLogin, ReservaAutoData reservaAux) {
        sceneController.cambiarAVentanaActualizarReservaAuto(event, empleadoLogin, reservaAux);
    }


    private void mostrarMensaje(String titulo, String header, String contenido, Alert.AlertType information) {
        Alert alert = new Alert(information);
        alert.setTitle(titulo);
        alert.setHeaderText(header);
        alert.setContentText(contenido);
        alert.showAndWait();
    }

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        runTime();
        try {
            reservaAutoShowTableData();
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
    public void reservaAutoSearch(ActionEvent actionEvent) {
        FilteredList<ReservaAutoData> filter = new FilteredList<>(reservaAutoData, p -> true);


        ga_search_field.textProperty().addListener((Observable, oldValue, newValue) -> {
            filter.setPredicate(reserva -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (reserva.getId_reserva().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (reserva.getCedula_cliente().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (reserva.getAuto().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (reserva.getServicios_adicionales().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (reserva.getFecha_reserva().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (reserva.getLugar_origen().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (reserva.getLugar_destino().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (Double.toString(reserva.getPrecio_total()).contains(lowerCaseFilter)) {
                    return true;
                } else if (Integer.toString(reserva.getCantidad_autos()).contains(lowerCaseFilter)) {
                    return true;
                } else
                    return false;
            });
        });

        SortedList<ReservaAutoData> sortedData = new SortedList<>(filter);
        sortedData.comparatorProperty().bind(rvautos_tableview.comparatorProperty());
        rvautos_tableview.setItems(sortedData);
    }
}