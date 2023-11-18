package co.edu.uniquindio.proyecto.controllers;


import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

import co.edu.uniquindio.proyecto.dto.HabitacionHotelData;
import co.edu.uniquindio.proyecto.dto.ReservaHotelData;
import co.edu.uniquindio.proyecto.model.*;
import co.edu.uniquindio.proyecto.repositories.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.ConstraintViolationException;
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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionSystemException;


@Controller
public class ReservaHotelController implements Initializable {


    @Autowired
    private ReservaHotelRepo reservaHotelRepo;

    @Autowired
    private ClienteRepo clienteRepo;

    @Autowired
    private PoliticaCancelacionRepo politicaCancelacionRepo;

    @Autowired
    private RegimenHospedajeRepo regimenHospedajeRepo;

    @Autowired
    private HotelRepo hotelRepo;

    @Autowired
    private HabitacionRepo habitacionRepo;

    @Autowired
    private HabitacionReservaRepo habitacionReservaRepo;

    @Autowired
    private CancelacionHospedajeRepo cancelacionHospedajeRepo;
    @Autowired
    private SceneController sceneController;


    private ReservaHotel reservaSeleccionada;
    private Cliente clienteSeleccionado;
    private Hotel hotelSeleccionado;
    private PoliticaCancelacion politicaCancelacionSeleccionada;

    private Empleado empleadoLogin;

    private RegimenHospedaje regimenSeleccionado;

    private Habitacion selectedHabitacion;

    private ReservaHotelData reserva;


    public ObservableList<ReservaHotelData> reservaHotelData = FXCollections.observableArrayList();

    // public ObservableList<HabitacionesHotelData> habitacionesHotelData = FXCollections.observableArrayList();

    private static final String VALIDACION_DATOS = "Validacion de datos";

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @FXML
    private Label current_ventana_lbl;

    @FXML
    private Label fecha_actual_lbl;

    @FXML
    private Button gr_buscar_btn;

    @FXML
    private TextField gr_cantidadhab_lbl;

    @FXML
    private DatePicker gr_checkin_date;

    @FXML
    private DatePicker gr_checkout_date;

    @FXML
    private ComboBox<String> gr_ciudad_cbx;

    @FXML
    private ComboBox<String> gr_cliente_cbx;

    @FXML
    private Button gr_crear_btn;

    @FXML
    private TextArea gr_desc_lbl;

    @FXML
    private Button gr_eliminar_btn;

    @FXML
    private ComboBox<String> gr_hotel_cbx;

    @FXML
    private Label gr_impuesto_lbl;

    @FXML
    private Button gr_modificar_btn;

    @FXML
    private ComboBox<String> gr_pais_cbx;

    @FXML
    private Label gr_plazocancelar_lbl;

    @FXML
    private Label gr_preciototal_lbl;

    @FXML
    private ComboBox<String> gr_regimen_cbx;

    @FXML
    private TextField gr_search_field;

    @FXML
    private Label gr_totaldias_lbl;

    @FXML
    private TableView<HabitacionHotelData> gr_habitaciones_tableview;

    @FXML
    private TableColumn<HabitacionHotelData, String> hab_id;

    @FXML
    private TableColumn<HabitacionHotelData, String> hab_tipo;

    @FXML
    private TableColumn<HabitacionHotelData, String> hab_capacidad;

    @FXML
    private TableColumn<HabitacionHotelData, String> hab_nivel;

    @FXML
    private TableColumn<HabitacionHotelData, Double> hab_precionoche;

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
    private AnchorPane reserva_hotel_form;

    @FXML
    private TableView<ReservaHotelData> reserva_tableview;

    @FXML
    private TableColumn<ReservaHotelData, Integer> rva_cantidad_hab;

    @FXML
    private TableColumn<ReservaHotelData, String> rva_checkin;

    @FXML
    private TableColumn<ReservaHotelData, String> rva_checkout;

    @FXML
    private TableColumn<ReservaHotelData, String> rva_cliente;

    @FXML
    private TableColumn<ReservaHotelData, String> rva_estado;

    @FXML
    private TableColumn<ReservaHotelData, String> rva_fecha;

    @FXML
    private TableColumn<ReservaHotelData, String> rva_hotel;

    @FXML
    private TableColumn<ReservaHotelData, Integer> rva_id;

    @FXML
    private TableColumn<ReservaHotelData, String> rva_rgmn;

    @FXML
    private TableColumn<ReservaHotelData, Double> rva_total_imp;

    @FXML
    private Label username_lbl;

    private Map<String, Integer> mapaPaises = new HashMap<>();

    // private HashMap<String, String> mapaPaises;
    private HashMap<String, String> mapaCiudades;
    private HashMap<String, String[]> mapaClientes;
    private HashMap<String, String[]> mapaHoteles;
    private HashMap<String, String[]> mapaRegimenes;
    private Alert alert;


    public void reservaHotelShowTableData() throws Exception {
        List<Object[]> results = reservaHotelRepo.buscarReservaHotel();
        List<ReservaHotelData> reservaHotelDataList = new ArrayList<>();

        //repo para traer todos los id habitaciones de esa reserva
        List<String> id_habitaciones = null;

        for (Object[] result : results) {
            ReservaHotelData data = new ReservaHotelData(
                    String.valueOf(result[0]),  // id_reserva
                    String.valueOf(result[1]),  // cedula_cliente
                    (String) result[2],  // hotel
                    (String) result[3],  // regimen_hospedaje
                    ((LocalDate) result[4]).toString(),  // fecha_reserva
                    ((LocalDate) result[5]).toString(),  // fecha_checkin
                    ((LocalDate) result[6]).toString(),  // fecha_checkout
                    (Double) result[7],  // total_con_impuesto
                    ((Long) result[8]).intValue(), // cantidad_habitaciones
                    (String) result[9]  // estado

            );
            reservaHotelDataList.add(data);
        }

        reservaHotelData = FXCollections.observableArrayList(reservaHotelDataList);

        rva_id.setCellValueFactory(new PropertyValueFactory<>("id_reserva"));
        rva_cliente.setCellValueFactory(new PropertyValueFactory<>("cedula_cliente"));
        rva_hotel.setCellValueFactory(new PropertyValueFactory<>("hotel"));
        rva_rgmn.setCellValueFactory(new PropertyValueFactory<>("regimen_hospedaje"));
        rva_fecha.setCellValueFactory(new PropertyValueFactory<>("fecha_reserva"));
        rva_checkin.setCellValueFactory(new PropertyValueFactory<>("fecha_checkin"));
        rva_checkout.setCellValueFactory(new PropertyValueFactory<>("fecha_checkout"));
        rva_total_imp.setCellValueFactory(new PropertyValueFactory<>("total_con_impuesto"));
        rva_cantidad_hab.setCellValueFactory(new PropertyValueFactory<>("cantidad_habitaciones"));
        rva_estado.setCellValueFactory(new PropertyValueFactory<>("estado"));

        reserva_tableview.setItems(reservaHotelData);
    }


    @FXML
    void reservaSearch(ActionEvent event) {
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
                    return true;*/
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

    @FXML
    void habitacionSelectData(MouseEvent event) {
        HabitacionHotelData habitacionHotelData = gr_habitaciones_tableview.getSelectionModel().getSelectedItem();

        int cantidadHabitaciones = Integer.parseInt(gr_cantidadhab_lbl.getText());
        Double precioTotal = cantidadHabitaciones * habitacionHotelData.getPrecio_noche();
        gr_preciototal_lbl.setText(String.valueOf(precioTotal));

        selectedHabitacion = habitacionRepo.findById(Integer.valueOf(habitacionHotelData.getId_habitacion())).get();
        System.out.println(" selected precio noche: " + selectedHabitacion.getPrecioNoche());
        selectedHabitacion.setPrecioNoche(habitacionHotelData.getPrecio_noche());
    }


    @FXML
    void eliminarBtn(ActionEvent event) {
        eliminarReserva();
    }

    private void eliminarReserva() {

        boolean confirmacion;

        if (reservaSeleccionada != null) {
            confirmacion = mostrarMensajeConfirmacion("Confirme que desea eliminar la reserva seleccionada");
            if (confirmacion) {
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


    private void crearReservaHotel() {
        String informacionVerificada = verificarInformacionReserva(gr_cliente_cbx.getValue(), gr_hotel_cbx.getValue(),
                gr_regimen_cbx.getValue(), gr_checkin_date.getValue().toString(), gr_checkout_date.getValue().toString(),
                selectedHabitacion, gr_cantidadhab_lbl.getText(), gr_desc_lbl.getText());
        //CREACION DE RESERVA HOTEL: RESERVA
        //idCliente
        String[] partesCliente = gr_cliente_cbx.getValue().split(",");
        String cedulaCliente = partesCliente[1].replace("Cedula: ", "").trim();
        String idCliente = clienteRepo.buscarByCedula(cedulaCliente);
        //idHotel
        String hotel = gr_hotel_cbx.getValue();
        String[] partesHotel = hotel.split(",");
        String idHotelSeleccionado = partesHotel[0].replace("ID: ", "").trim();
        //idPoliticaCancelacion
        String idPoliticaCancelacion = reservaHotelRepo.obtenerIDPlazoCancelacionByHotel(idHotelSeleccionado);
        //codigoEmpleado
        String codigoEmpleado = menu_userID_lbl.getText();
        //idRegimen
        String regimen = gr_regimen_cbx.getValue();
        String[] partesRegimen = regimen.split(",");
        String idRegimenSeleccionado = partesRegimen[0].replace("ID: ", "").trim();
        //fechaReserva
        String fechaReserva = LocalDate.now().toString();
        //fechaCheckin
        String checkin = gr_checkin_date.getValue().toString();
        //fechaCheckout
        String checkout = gr_checkout_date.getValue().toString();
        //impuesto
        String impuestoText = gr_impuesto_lbl.getText().replace(",", ".");
        Double impuesto = Double.parseDouble(impuestoText);
        //estado
        String estado = "Activa";

        //total
        Double precioTotal = Double.parseDouble(gr_preciototal_lbl.getText()) + impuesto;

        //CREACION DE DETALLE: RESERVA_HABITACION
        int cantidadHabRe = Integer.parseInt(gr_cantidadhab_lbl.getText());
        double precioTotalHabRe = cantidadHabRe * selectedHabitacion.getPrecioNoche();
        String estadoHabRe = "Reservada";
        String descripcionHabRe = gr_desc_lbl.getText();


        if (!informacionVerificada.equalsIgnoreCase("Ok")) {
            mostrarMensaje(VALIDACION_DATOS, VALIDACION_DATOS, informacionVerificada, Alert.AlertType.WARNING);
            return;
        }
        clienteSeleccionado = clienteRepo.findById(Integer.valueOf(idCliente)).get();
        hotelSeleccionado = hotelRepo.findById(idHotelSeleccionado);
        politicaCancelacionSeleccionada = politicaCancelacionRepo.findById(Integer.parseInt(idPoliticaCancelacion));
        regimenSeleccionado = regimenHospedajeRepo.findById(idRegimenSeleccionado);

        try {

            ReservaHotel reservaHotel = new ReservaHotel(LocalDate.now(), LocalDate.parse(checkin),
                    LocalDate.parse(checkout), impuesto, estado, precioTotal, clienteSeleccionado,
                    empleadoLogin, hotelSeleccionado, regimenSeleccionado);
            System.out.println("reserva hotel: " + reservaHotel.toString());
            reservaHotelRepo.save(reservaHotel).getIdReserva();

            HabitacionReserva habitacionReserva = new HabitacionReserva(reservaHotel, selectedHabitacion,
                    precioTotalHabRe, estadoHabRe, descripcionHabRe, cantidadHabRe);

            System.out.println("habitacion reserva: " + habitacionReserva.toString());
            habitacionReservaRepo.save(habitacionReserva).getReserva().getIdReserva();


            limpiarInterfazReservaHotel();

            reservaHotelShowTableData();
            //    habitacionesShowTableData();
            //limpiar habitacionSeleccionada y reservaSeleccionada
            selectedHabitacion = null;
            reservaSeleccionada = null;

            mostrarMensaje("Gestión de Reservas Hotel", "Información", "Reservación creado con éxito",
                    Alert.AlertType.INFORMATION);
        } catch (TransactionSystemException e) {
            Throwable t = e.getCause();
            while ((t != null) && !(t instanceof ConstraintViolationException)) {
                t = t.getCause();
            }
            if (t instanceof ConstraintViolationException) {
                ConstraintViolationException cve = (ConstraintViolationException) t;
                if (!cve.getConstraintViolations().isEmpty()) {
                    String errorMessage = cve.getConstraintViolations().iterator().next().getMessage();
                    mostrarMensaje(VALIDACION_DATOS, VALIDACION_DATOS, errorMessage,
                            Alert.AlertType.WARNING);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


    private void actualizarReservaHotel() {
        //SE OBTIENEN SOLO VALORES A ACTUALIZAR
        String informacionVerificada = verificarInformacionReserva(gr_cliente_cbx.getValue(), gr_hotel_cbx.getValue(),
                gr_regimen_cbx.getValue(), gr_checkin_date.getValue().toString(), gr_checkout_date.getValue().toString(),
                selectedHabitacion, gr_cantidadhab_lbl.getText(), gr_desc_lbl.getText());
        //ACTUALIZACION DE RESERVA HOTEL: RESERVA
        //idCliente
        String[] partesCliente = gr_cliente_cbx.getValue().split(",");
        String cedulaCliente = partesCliente[1].replace("Cedula: ", "").trim();
        String idCliente = clienteRepo.buscarByCedula(cedulaCliente);
        //idHotel
        String hotel = gr_hotel_cbx.getValue();
        String[] partesHotel = hotel.split(",");
        String idHotelSeleccionado = partesHotel[0].replace("ID: ", "").trim();
        //idPoliticaCancelacion
        String idPoliticaCancelacion = reservaHotelRepo.obtenerIDPlazoCancelacionByHotel(idHotelSeleccionado);
        //codigoEmpleado
        String codigoEmpleado = menu_userID_lbl.getText();
        //idRegimen
        String regimen = gr_regimen_cbx.getValue();
        String[] partesRegimen = regimen.split(",");
        String idRegimenSeleccionado = partesRegimen[0].replace("ID: ", "").trim();
        //fechaReserva
        String fechaReserva = LocalDate.now().toString();
        //fechaCheckin
        String checkin = gr_checkin_date.getValue().toString();
        //fechaCheckout
        String checkout = gr_checkout_date.getValue().toString();
        //impuesto
        String impuestoText = gr_impuesto_lbl.getText().replace(",", ".");
        Double impuesto = Double.parseDouble(impuestoText);
        //estado
        String estado = "Activa";

        //total
        Double precioTotal = Double.parseDouble(gr_preciototal_lbl.getText()) + impuesto;

        //ACTUALIZACION DE DETALLE: RESERVA_HABITACION
        int cantidadHabRe = Integer.parseInt(gr_cantidadhab_lbl.getText());
        double precioTotalHabRe = cantidadHabRe * selectedHabitacion.getPrecioNoche();
        String estadoHabRe = "Reservada";
        String descripcionHabRe = gr_desc_lbl.getText();

        if (!informacionVerificada.equalsIgnoreCase("Ok")) {
            mostrarMensaje(VALIDACION_DATOS, VALIDACION_DATOS, informacionVerificada, Alert.AlertType.WARNING);
            return;
        }
        clienteSeleccionado = clienteRepo.findById(Integer.valueOf(idCliente)).get();
        hotelSeleccionado = hotelRepo.findById(idHotelSeleccionado);
        politicaCancelacionSeleccionada = politicaCancelacionRepo.findById(Integer.parseInt(idPoliticaCancelacion));
        regimenSeleccionado = regimenHospedajeRepo.findById(idRegimenSeleccionado);


        try {
            reservaSeleccionada.setCliente(clienteSeleccionado);
            reservaSeleccionada.setHotel(hotelSeleccionado);
            reservaSeleccionada.setEmpleado(empleadoLogin);
            reservaSeleccionada.setRegimenHospedaje(regimenSeleccionado);
            reservaSeleccionada.setFechaCheckin(LocalDate.parse(checkin));
            reservaSeleccionada.setFechaCheckout(LocalDate.parse(checkout));
            reservaSeleccionada.setImpuesto(impuesto);
            reservaSeleccionada.setTotal(precioTotal);
            reservaSeleccionada.setEstado(estado);
            //actualizar
            reservaHotelRepo.save(reservaSeleccionada);

            //actualizar habitacion reserva
            HabitacionReserva habitacionReserva = new HabitacionReserva();
            //reserva es de tipo reserva Hotel Data - contiene idHabitacion seleccionada
            //   habitacionReserva = habitacionReservaRepo.findByBothId(Integer.parseInt(reserva.getId_reserva()),Integer.parseInt(reserva.getId_habitacion()));

            habitacionReserva.setCantidad(cantidadHabRe);
            habitacionReserva.setPrecioTotal(precioTotalHabRe);
            habitacionReserva.setEstado(estadoHabRe);
            habitacionReserva.setDescripcion(descripcionHabRe);

            habitacionReservaRepo.save(habitacionReserva);

            mostrarMensaje("Gestión de Reservas", "Información", "Reserva actualizada con éxito",
                    Alert.AlertType.INFORMATION);

            //limpiar habitacionSeleccionada y reservaSeleccionada
            selectedHabitacion = null;
            reservaSeleccionada = null;
        } catch (TransactionSystemException e) {
            Throwable t = e.getCause();
            while ((t != null) && !(t instanceof ConstraintViolationException)) {
                t = t.getCause();
            }
            if (t instanceof ConstraintViolationException) {
                ConstraintViolationException cve = (ConstraintViolationException) t;
                if (!cve.getConstraintViolations().isEmpty()) {
                    String errorMessage = cve.getConstraintViolations().iterator().next().getMessage();
                    mostrarMensaje(VALIDACION_DATOS, VALIDACION_DATOS, errorMessage,
                            Alert.AlertType.WARNING);
                }
            }
        }
    }


    private void limpiarInterfazReservaHotel() {
        gr_impuesto_lbl.setText("");
        gr_preciototal_lbl.setText("");
        gr_plazocancelar_lbl.setText("");
        gr_totaldias_lbl.setText("");
        /*
        gr_checkin_date.setValue(null);
        gr_checkout_date.setValue(null);
        gr_cliente_cbx.getSelectionModel().clearSelection();
        gr_hotel_cbx.getSelectionModel().clearSelection();
        gr_regimen_cbx.getSelectionModel().clearSelection();*/

    }

    private void mostrarMensaje(String titulo, String header, String contenido, Alert.AlertType information) {
        Alert alert = new Alert(information);
        alert.setTitle(titulo);
        alert.setHeaderText(header);
        alert.setContentText(contenido);
        alert.showAndWait();
    }

    private String verificarInformacionReserva(String idCliente, String idHotelSeleccionado,
                                               String idRegimenSeleccionado, String checkin, String checkout,
                                               Habitacion selectedHabitacion, String cantidadHabitaciones, String descripcion) {

        String mensaje = "";
        if (idCliente.equalsIgnoreCase("")) {
            mensaje += "Por favor seleccione un cliente \n";
        }
        if (idHotelSeleccionado.equalsIgnoreCase("")) {
            mensaje += "Por favor seleccione un hotel \n";
        }
        if (idRegimenSeleccionado.equalsIgnoreCase("")) {
            mensaje += "Por favor seleccione el regimen de hospedaje \n";
        }
        if (checkin.equalsIgnoreCase("")) {
            mensaje += "Por favor ingrese la fecha de checkin \n";
        }
        if (checkout.equalsIgnoreCase("")) {
            mensaje += "Por favor ingrese la fecha de checkout \n";
        }
        if (selectedHabitacion == null) {
            mensaje += "Por favor seleccione una habitacion \n";
        }
        if (cantidadHabitaciones.equalsIgnoreCase("")) {
            mensaje += "Por favor ingrese la cantidad de habitaciones \n";
        }
        if (descripcion.equalsIgnoreCase("")) {
            mensaje += "Por favor ingrese una descripcion \n";
        }
        if (mensaje.equalsIgnoreCase("")) {
            mensaje = "OK";
        }
        return mensaje;

    }


    void cargarComboPaisHotel() {
        try {
            List<Map<String, Object>> paises = reservaHotelRepo.obtenerPaises();
            ObservableList<String> datos = FXCollections.observableArrayList();
            for (Map<String, Object> pais : paises) {
                String nombre = (String) pais.get("nombre");
                Integer id = (Integer) pais.get("id");
                datos.add(nombre);
                mapaPaises.put(nombre, id);
            }
            gr_pais_cbx.setItems(datos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    void cargarComboCiudadHotel(Integer paisSeleccionado) {
        try {
            List<Map<String, Object>> ciudades = reservaHotelRepo.obtenerCiudades(paisSeleccionado);
            ObservableList<String> datos = FXCollections.observableArrayList();
            for (Map<String, Object> ciudad : ciudades) {
                String nombre = (String) ciudad.get("nombre");
                datos.add(nombre);
            }
            gr_ciudad_cbx.setItems(datos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    void comboPaisHotelListener(ActionEvent event) {
        Integer idPais = mapaPaises.get(gr_pais_cbx.getValue());
        cargarComboCiudadHotel(idPais);
    }


    @FXML
    void reservaHotelSelectData(MouseEvent event) {
        reserva = reserva_tableview.getSelectionModel().getSelectedItem();

        if (reserva != null) {
            reservaSeleccionada = reservaHotelRepo.findById(Integer.valueOf(reserva.getId_reserva())).get();
            System.out.println("reserva seleccionada: " + reservaSeleccionada.toString());
        }
        int num = reserva_tableview.getSelectionModel().getSelectedIndex();
        if ((num - 1) < -1) {
            return;
        }
        /*
        String checkin = reserva.getFecha_checkin().substring(0,10);
        String checkout = reserva.getFecha_checkout().substring(0,10);
        LocalDate localcheckin = LocalDate.parse(checkin, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate localcheckout = LocalDate.parse(checkout, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        String cedulaCliente = reserva.getCedula_cliente();
        for (String item : gr_cliente_cbx.getItems()) {
            if (item.contains("Cedula: " + cedulaCliente)) {
                gr_cliente_cbx.setValue(item);
                break;
            }
        }
        String hotelReserva = reserva.getHotel();
        for (String item : gr_hotel_cbx.getItems()) {
            if (item.contains("Nombre: " + hotelReserva)) {
                gr_hotel_cbx.setValue(item);
                break;
            }
        }
        String regimenHospedaje = reserva.getRegimen_hospedaje();
        for (String item : gr_regimen_cbx.getItems()) {
            if (item.contains("Descripcion: " + regimenHospedaje)) {
                gr_regimen_cbx.setValue(item);
                break;
            }
        }

        gr_checkin_date.setValue(localcheckin);
        gr_checkout_date.setValue(localcheckout);
      //  gr_cantidadhab_lbl.setText(Integer.toString(reserva.getCantidad_habitaciones()));

        String impuestoText = gr_impuesto_lbl.getText().replace(",", ".");
        Double impuesto = Double.parseDouble(impuestoText);
        Double precioTotal = reserva.getTotal_con_impuesto() - impuesto;
        gr_preciototal_lbl.setText(String.valueOf(precioTotal));
    //    gr_desc_lbl.setText(reserva.getDescripcion());

        //como seleccionar en la tabla de habitaciones la habitacion que esta en la reserva
       // String idHabitacionReserva = reserva.getId_habitacion();
        for (HabitacionHotelData habitacion : gr_habitaciones_tableview.getItems()) {
           // if (habitacion.getId_habitacion().equals(idHabitacionReserva)) {
                gr_habitaciones_tableview.getSelectionModel().select(habitacion);
                break;
            }
        }*/
    }
/*
    public void habitacionesShowTableData() {
        try {
            List<Map<String, Object>> habitacionesData = reservaHotelRepo.obtenerHabitacionesData();

            ObservableList<HabitacionHotelData> habitacionesListData = FXCollections.observableArrayList();
            for (Map<String, Object> habitacionData : habitacionesData) {
                HabitacionHotelData habitacion = new HabitacionHotelData(
                        String.valueOf(habitacionData.get("id_habitacion")),
                        String.valueOf(habitacionData.get("tipo")),
                        String.valueOf(habitacionData.get("capacidad")),
                        String.valueOf(habitacionData.get("nivel")),
                        Double.valueOf(String.valueOf(habitacionData.get("precio_noche"))));
                habitacionesListData.add(habitacion);
            }

            hab_id.setCellValueFactory(new PropertyValueFactory<>("id_habitacion"));
            hab_tipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
            hab_capacidad.setCellValueFactory(new PropertyValueFactory<>("capacidad"));
            hab_nivel.setCellValueFactory(new PropertyValueFactory<>("nivel"));
            hab_precionoche.setCellValueFactory(new PropertyValueFactory<>("precio_noche"));

            gr_habitaciones_tableview.setItems(habitacionesListData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
/*
    public void habitacionesDisponiblesShowTableData(String idHotel, Date checkin) {
        try {
           // List<Map<String, Object>> habitacionesData = reservaHotelRepo.obtenerHabitacionesDisponiblesData(idHotel, checkin);
            java.util.Date utilDate = new java.util.Date(checkin.getTime());

            // Convertir java.util.Date a java.time.LocalDate
            LocalDate localDateCheckin = utilDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();


            List<Map<String, Object>> habitacionesData = reservaHotelRepo.obtenerHabitacionesDisponiblesData(idHotel, localDateCheckin);
            ObservableList<HabitacionHotelData> habitacionesListData = FXCollections.observableArrayList();
            for (Map<String, Object> habitacionData : habitacionesData) {
                HabitacionHotelData habitacion = new HabitacionHotelData(
                        String.valueOf(habitacionData.get("id_habitacion")),
                        String.valueOf(habitacionData.get("tipo")),
                        String.valueOf(habitacionData.get("capacidad")),
                        String.valueOf(habitacionData.get("nivel")),
                        Double.valueOf(String.valueOf(habitacionData.get("precio_noche"))));
                habitacionesListData.add(habitacion);
            }

            hab_id.setCellValueFactory(new PropertyValueFactory<>("id_habitacion"));
            hab_tipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
            hab_capacidad.setCellValueFactory(new PropertyValueFactory<>("capacidad"));
            hab_nivel.setCellValueFactory(new PropertyValueFactory<>("nivel"));
            hab_precionoche.setCellValueFactory(new PropertyValueFactory<>("precio_noche"));

            gr_habitaciones_tableview.setItems(habitacionesListData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
*/


    /**
     * Método que se encarga de cambiar la ventana de acuerdo al botón que se presione.
     *
     * @param event el evento que se genera al presionar un botón.
     * @throws Exception
     */
    @FXML
    void switchForm(ActionEvent event) throws Exception {

        if (event.getSource() == menu_rva_autos_btn) {
            abrirVentanaAutoReserva(event, empleadoLogin);
            menu_rva_autos_btn.getScene().getWindow().hide();
        } else if (event.getSource() == menu_cpa_arti_btn) {
            abrirVentanaCompraArticulo(event, empleadoLogin);
            menu_cpa_arti_btn.getScene().getWindow().hide();
        } else if (event.getSource() == menu_cpa_paq_btn) {
            abrirVentanaCompraPaquete(event, empleadoLogin);
            menu_cpa_paq_btn.getScene().getWindow().hide();
        }
    }


    @FXML
    void actualizarBtn(ActionEvent event) {
        //validar que se haya seleccionado una reserva
        if (reserva != null) {
            //actualizarReservaHotel();
            abrirVentanaActualizarReservaHotel(event, empleadoLogin, reserva);
            gr_modificar_btn.getScene().getWindow().hide();
        } else {
            mostrarMensaje(VALIDACION_DATOS, VALIDACION_DATOS, "Por favor, seleccione una reserva para actualizar",
                    Alert.AlertType.WARNING);
        }

    }

    private void abrirVentanaActualizarReservaHotel(ActionEvent event, Empleado empleadoLogin, ReservaHotelData reservaAux) {
        sceneController.cambiarAVentanaActualizarReservaHotel(event, empleadoLogin, reservaAux);
    }

    @FXML
    void crearBtn(ActionEvent event) {
        abrirVentanaCrearReservaHotel(event, empleadoLogin);
        gr_crear_btn.getScene().getWindow().hide();
        //  crearReservaHotel();
    }

    private void abrirVentanaCrearReservaHotel(ActionEvent event, Empleado empleado) {
        sceneController.cambiarAVentanaCrearReservaHotel(event, empleado);
    }

    private void abrirVentanaAutoReserva(ActionEvent event, Empleado empleado) {
        sceneController.cambiarAVentanaReservaAuto(event, empleado);
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
    public void displayEmployeeIDUsername(Empleado empleado) {
        empleadoLogin = empleado;
        username_lbl.setText(empleado.getPersona().getNombre());
        menu_userID_lbl.setText(String.valueOf(empleado.getCodigoEmpleado()));
        menu_user_lbl.setText(empleado.getPersona().getNombre());
    }


    /**
     * Inicia un nuevo hilo que actualiza la etiqueta fecha_actual_lbl con la fecha y hora actuales cada segundo.
     */
    public void runTime() {
        new Thread() {
            public void run() {
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss a");
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
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
        System.out.println("ReservaHotelRepo: " + reservaHotelRepo);

        try {
            reservaHotelShowTableData();

            cargarComboPaisHotel();
            // cargarComboClienteHotel();
            // habitacionesShowTableData();
            // cargarComboHoteles();
            // cargarDatePicker();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
