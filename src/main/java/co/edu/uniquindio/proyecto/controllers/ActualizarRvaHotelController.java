package co.edu.uniquindio.proyecto.controllers;

import co.edu.uniquindio.proyecto.dto.HabitacionHotelData;
import co.edu.uniquindio.proyecto.dto.ReservaHotelData;
import co.edu.uniquindio.proyecto.model.*;
import co.edu.uniquindio.proyecto.repositories.*;
import jakarta.validation.ConstraintViolationException;
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
import javafx.scene.paint.Color;
import javafx.util.Callback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionSystemException;

import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Controller
public class ActualizarRvaHotelController implements Initializable {



    /*Repos y Controllers*/
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


    /*ENTIDADES*/
    private Empleado empleadoLogueado;
    
    private ReservaHotelData reservaSeleccionada;

    private Cliente clienteSeleccionado;
    private Hotel hotelSeleccionado;

    private Habitacion habitacionSeleccionada;
    private RegimenHospedaje regimenSeleccionado;

    private ReservaHotel reservaActualizar;

    /*MAPAS */

    private Map<String, Integer> mapaPaises = new HashMap<>();

    // private HashMap<String, String> mapaPaises;
    private HashMap<String, String> mapaCiudades;
    private HashMap<String, String[]> mapaClientes;
    private HashMap<String, String[]> mapaHoteles;
    private HashMap<String, String[]> mapaRegimenes;
    private Alert alert;

    /*FXML COMPONENTES*/
    @FXML
    private Button btn_volver;

    @FXML
    private TableColumn<HabitacionHotelData, String> choose_action;

    @FXML
    private TableColumn<HabitacionHotelData, Integer> choose_cantidad;

    @FXML
    private TableColumn<HabitacionHotelData, String> choose_capacidad;

    @FXML
    private TableColumn<HabitacionHotelData, String> choose_desc;

    @FXML
    private TableColumn<HabitacionHotelData, String> choose_id;

    @FXML
    private TableColumn<HabitacionHotelData, String> choose_nivel;

    @FXML
    private TableColumn<HabitacionHotelData, Double> choose_precionoche;

    @FXML
    private TableColumn<HabitacionHotelData, String> choose_tipo;

    @FXML
    private Label fecha_actual_lbl;

    @FXML
    private DatePicker gr_checkin_date;

    @FXML
    private DatePicker gr_checkout_date;

    @FXML
    private ComboBox<String> gr_cliente_cbx;

    @FXML
    private Button gr_guardar_btn;

    @FXML
    private TableView<HabitacionHotelData> gr_habitaciones_tableview;

    @FXML
    private TableView<HabitacionHotelData> gr_choose_tableview;

    @FXML
    private ComboBox<String> gr_hotel_cbx;

    @FXML
    private Label gr_impuesto_lbl;

    @FXML
    private Label gr_preciototal_lbl;

    @FXML
    private ComboBox<String> gr_regimen_cbx;

    @FXML
    private Label gr_totaldias_lbl;

    @FXML
    private TableColumn<HabitacionHotelData, String> hab_action;

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
    private AnchorPane reserva_hotel_form;

    @FXML
    private Label username_lbl;



    @FXML
    void guardarBtn(ActionEvent event) {

        verificarReservaHotel();
    }


    private void verificarReservaHotel() {

        //imprimir la informacion de la tabla habitaciones
        List<HabitacionHotelData>listaHabitaciones=gr_choose_tableview.getItems();
        //recorrer la lista e imprimir los datos de la tabla
        for (HabitacionHotelData habitacionHotelData : listaHabitaciones) {
            System.out.println("habitacion: " + habitacionHotelData.toString());
        }
        //verificar que el text field de descripcion no este vacio en choose_desc
        String mensaje = verificarInformacionReserva(gr_cliente_cbx.getValue(), gr_hotel_cbx.getValue(),
                gr_regimen_cbx.getValue(), gr_checkin_date.getValue(), gr_checkout_date.getValue(),listaHabitaciones);

        if (!mensaje.equalsIgnoreCase("")) {
            mostrarMensaje("Gestión de Reservas Hotel", "Información", mensaje,
                    Alert.AlertType.WARNING);
            return;
        } else {
            modificarReservaHotel(listaHabitaciones);

        }
    }

    private void modificarReservaHotel(List<HabitacionHotelData> listaHabitaciones) {
        try {
            List<HabitacionHotelData> existentes = new ArrayList<>();
            List<HabitacionHotelData> no_existentes = new ArrayList<>();

            for (HabitacionHotelData habitacionHotelData : listaHabitaciones) {
                HabitacionReserva habitacionReserva = habitacionReservaRepo.findByBothId(Integer.parseInt(reservaSeleccionada.getId_reserva()), Integer.parseInt(habitacionHotelData.getId_habitacion()));
                if (habitacionReserva != null) {
                     existentes.add(habitacionHotelData);
                } else {
                     no_existentes.add(habitacionHotelData);
                }
            }

            ReservaHotel reservaHotelActualizada = actualizarReservaHotel();

            List<HabitacionReserva> listaAuxCrear = crearHabitacionesReserva(no_existentes, reservaHotelActualizada);
            List<HabitacionReserva> listaAuxActualizar = actualizarHabitacionesReserva(existentes, reservaHotelActualizada);

            List<HabitacionReserva> listaFinal = new ArrayList<>();
            listaFinal.addAll(listaAuxCrear);
            listaFinal.addAll(listaAuxActualizar);

            mostrarMensajeReservaActualizada(reservaHotelActualizada, listaFinal);
        } catch (TransactionSystemException e) {
            manejarExcepcion(e);
        }
    }

    private List<HabitacionReserva> actualizarHabitacionesReserva(List<HabitacionHotelData> listaHabitaciones, ReservaHotel reservaHotel) {
        List<HabitacionReserva> listaAux = new ArrayList<>();
        for (HabitacionHotelData habitacion : listaHabitaciones) {
            habitacionSeleccionada = habitacionRepo.findById(habitacion.getId_habitacion());
            HabitacionReserva habitacionReserva = new HabitacionReserva(reservaHotel, habitacionSeleccionada,
                    habitacion.getPrecio_noche(), "Reservada", habitacion.getDescripcion(), habitacion.getCantidad_habitaciones());
            listaAux.add(habitacionReserva);

            habitacionReserva.setReserva(reservaHotel);
            habitacionReserva.setHabitacion(habitacionSeleccionada);
            habitacionReserva.setPrecioTotal(habitacion.getPrecio_noche() * habitacion.getCantidad_habitaciones());
            habitacionReserva.setEstado("Reservada");
            habitacionReserva.setDescripcion(habitacion.getDescripcion());
            habitacionReserva.setCantidad(habitacion.getCantidad_habitaciones());

            habitacionReservaRepo.save(habitacionReserva);
        }
        return listaAux;
    }

    private List<HabitacionReserva> crearHabitacionesReserva(List<HabitacionHotelData> listaHabitaciones, ReservaHotel reservaHotel) {
        List<HabitacionReserva> listaAux = new ArrayList<>();
        for (HabitacionHotelData habitacion : listaHabitaciones) {
            habitacionSeleccionada = habitacionRepo.findById(habitacion.getId_habitacion());
            HabitacionReserva habitacionReserva = new HabitacionReserva(reservaHotel, habitacionSeleccionada,
                    habitacion.getPrecio_noche(), "Reservada", habitacion.getDescripcion(), habitacion.getCantidad_habitaciones());
            listaAux.add(habitacionReserva);
            habitacionReservaRepo.save(habitacionReserva);
        }
        return listaAux;
    }


    private ReservaHotel actualizarReservaHotel() {


        String idCliente = obtenerIdCliente();
        String idHotelSeleccionado = obtenerIdHotelSeleccionado();
        String idRegimenSeleccionado = obtenerIdRegimenSeleccionado();
        Double impuesto = obtenerImpuesto();
        Double precioTotal = obtenerPrecioTotal(impuesto);

        clienteSeleccionado = clienteRepo.findById(Integer.valueOf(idCliente)).get();
        hotelSeleccionado = hotelRepo.findById(idHotelSeleccionado);
        regimenSeleccionado = regimenHospedajeRepo.findById(idRegimenSeleccionado);

        reservaActualizar = reservaHotelRepo.findByIdReserva(reservaSeleccionada.getId_reserva());

        reservaActualizar.setCliente(clienteSeleccionado);
        reservaActualizar.setHotel(hotelSeleccionado);
        reservaActualizar.setRegimenHospedaje(regimenSeleccionado);
        reservaActualizar.setFechaCheckin(gr_checkin_date.getValue());
        reservaActualizar.setFechaCheckout(gr_checkout_date.getValue());
        reservaActualizar.setImpuesto(impuesto);
        reservaActualizar.setTotal(precioTotal);
        reservaActualizar.setEstado("Activa");
        reservaActualizar.setEmpleado(empleadoLogueado);

        reservaHotelRepo.save(reservaActualizar);




        return reservaActualizar;
    }


    private String obtenerIdCliente() {
        String[] partesCliente = gr_cliente_cbx.getValue().split(",");
        String cedulaCliente = partesCliente[1].replace("Cedula: ", "").trim();
        return clienteRepo.buscarByCedula(cedulaCliente);
    }

    private String obtenerIdHotelSeleccionado() {
        String hotel = gr_hotel_cbx.getValue();
        String[] partesHotel = hotel.split(",");
        return partesHotel[0].replace("ID: ", "").trim();
    }

    private String obtenerIdRegimenSeleccionado() {
        String regimen = gr_regimen_cbx.getValue();
        String[] partesRegimen = regimen.split(",");
        return partesRegimen[0].replace("ID: ", "").trim();
    }

    private Double obtenerImpuesto() {
        String impuestoText = gr_impuesto_lbl.getText().replace(",", ".");
        return Double.parseDouble(impuestoText);
    }

    private Double obtenerPrecioTotal(Double impuesto) {
        return Double.parseDouble(gr_preciototal_lbl.getText().replace(",",".")) + impuesto;
    }



    private void mostrarMensajeReservaActualizada(ReservaHotel reservaHotel, List<HabitacionReserva> listaAux) {
        StringBuilder message = new StringBuilder();
        message.append("Reservación Actualizada con éxito\n");
        message.append("Actualización Exitosa: \n");
        message.append(reservaHotel.toString());
        message.append("\nInformacion habitacion reserva:\n");

        for (HabitacionReserva habitacion : listaAux) {
            message.append("Habitacion ID: ").append(habitacion.getHabitacion().getIdHabitacion()).append("\n");
            message.append("Tipo: ").append(habitacion.getHabitacion().getTipoHabitacion().getNombre()).append("\n");
            message.append("Nivel: ").append(habitacion.getHabitacion().getNivel().getNombre()).append("\n");
            message.append("Precio por noche: ").append(habitacion.getHabitacion().getPrecioNoche()).append("\n");
            message.append("Cantidad: ").append(habitacion.getCantidad()).append("\n");
            message.append("Descripcion: ").append(habitacion.getDescripcion()).append("\n\n");
        }

        mostrarMensaje("Gestión de Reservas Hotel", "Reserva Actualizada con éxito", message.toString(), Alert.AlertType.INFORMATION);
    }


    private void manejarExcepcion(TransactionSystemException e) {
        Throwable t = e.getCause();
        while ((t != null) && !(t instanceof ConstraintViolationException)) {
            t = t.getCause();
        }
        if (t instanceof ConstraintViolationException) {
            ConstraintViolationException cve = (ConstraintViolationException) t;
            if (!cve.getConstraintViolations().isEmpty()) {
                String errorMessage = cve.getConstraintViolations().iterator().next().getMessage();
                mostrarMensaje("VALIDACION_DATOS", "ERROR INESPERADO", errorMessage,
                        Alert.AlertType.WARNING);
            }
        }
    }


    private void mostrarMensaje(String gestiónDeReservasHotel, String información, String mensaje, Alert.AlertType warning) {
        alert = new Alert(warning);
        alert.setTitle(gestiónDeReservasHotel);
        alert.setHeaderText(información);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private String verificarInformacionReserva(String cliente, String hotel, String regimen, LocalDate checkin, LocalDate checkout,List<HabitacionHotelData> listaHabitaciones) {
        //verificar que la informacion no este nula y almacenar en un mensaje
        String mensaje = "";
        if(listaHabitaciones.isEmpty()){
            mensaje += "Debe seleccionar al menos una habitación.\n";
        }
        for (HabitacionHotelData habitacion : listaHabitaciones) {
            if (habitacion.getDescripcion() == null || habitacion.getDescripcion().isEmpty()) {
                mensaje += "Debe ingresar una descripción para la habitación " + habitacion.getId_habitacion() + ".\n";
            }
        }
        if (cliente == null) {
            mensaje += "Debe seleccionar un cliente.\n";
        }
        if (hotel == null) {
            mensaje += "Debe seleccionar un hotel.\n";
        }
        if (regimen == null) {
            mensaje += "Debe seleccionar un regimen.\n";
        }
        if (checkin == null) {
            mensaje += "Debe seleccionar una fecha de checkin.\n";
        }
        if (checkout == null) {
            mensaje += "Debe seleccionar una fecha de checkout.\n";
        }
        return mensaje;
    }




    void cargarComboClienteHotel() {
        try {
            List<Map<String, Object>> clientes = reservaHotelRepo.obtenerClientes();

            ObservableList<String> datos = FXCollections.observableArrayList();
            for (Map<String, Object> cliente : clientes) {
                String id = String.valueOf(cliente.get("id"));
                String cedula = String.valueOf(cliente.get("cedula"));
                String nombre = (String) cliente.get("nombre");
                datos.add("ID: "+ id + ", Cedula: " + cedula + ", Nombre: " + nombre);
            }
            gr_cliente_cbx.setItems(datos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void cargarComboHoteles() {
        try {
            List<Map<String, Object>> hoteles = reservaHotelRepo.obtenerHoteles();
            ObservableList<String> datos = FXCollections.observableArrayList();
            for (Map<String, Object> hotel : hoteles) {
                Integer id = (Integer) hotel.get("id");
                String nombre = (String) hotel.get("nombre");
                Double precioNoche = (Double) hotel.get("precioNoche");
                datos.add("ID: "+ Integer.toString(id) + ", Nombre: " + nombre + ", Precio Base: " + Double.toString(precioNoche));
            }
            gr_hotel_cbx.setItems(datos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void cargarComboRegimenHospedaje(String hotelSeleccionado) {
        try {
            System.out.println("hotel seleccionado: " + hotelSeleccionado);
            Integer idHotel = Integer.parseInt(hotelSeleccionado);
            List<Map<String, Object>> regimenes = reservaHotelRepo.obtenerRegimenHospedaje(idHotel);
            ObservableList<String> datos = FXCollections.observableArrayList();
            for (Map<String, Object> regimen : regimenes) {
                Integer id = (Integer) regimen.get("id");
                String descripcion = (String) regimen.get("descripcion");
                Double precio = (Double) regimen.get("precio");
                datos.add("ID: "+ Integer.toString(id) + ", Descripcion: " + descripcion + ", Precio: " + Double.toString(precio));
            }
            gr_regimen_cbx.setItems(datos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void cargarDatePicker() {

        try {
            LocalDate fechaActual = LocalDate.now();
            Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
                @Override
                public DateCell call(DatePicker datePicker) {
                    return new DateCell() {
                        @Override
                        public void updateItem(LocalDate item, boolean empty) {
                            super.updateItem(item, empty);
                            if (item.isBefore(fechaActual)) {
                                setDisable(true);
                                setStyle("-fx-background-color: #ffc0cb;");
                            }
                        }
                    };
                }
            };
            gr_checkin_date.setDayCellFactory(dayCellFactory);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @FXML
    void comboClienteHotelListener(ActionEvent event) {

    }

    @FXML
    void comboHotelesListener(ActionEvent event) {
        String seleccionado = gr_hotel_cbx.getValue();
        String[] partes = seleccionado.split(",");
        String idHotel = partes[0].replace("ID: ", "").trim();
        cargarComboRegimenHospedaje(idHotel);
    }

    //cargar valor de impuesto en gr_impuesto_lbl
    /**
     * Este método carga el valor del impuesto en la etiqueta gr_impuesto_lbl a partir del 10% del precio base del hotel seleccionado en gr_hotel_cbx.
     */
    void cargarImpuesto() {
        String seleccionado = gr_hotel_cbx.getValue();
        String[] partes = seleccionado.split(",");
        String precioBase = partes[2].replace("Precio Base: ", "").trim();
        try {
            // Eliminar cualquier carácter que no sea un dígito o un punto
            // precioBase = precioBase.replaceAll("[^\\d.]", "");
            double impuesto = Double.parseDouble(precioBase) * 0.1;
            gr_impuesto_lbl.setText(String.format("%.2f", impuesto));
            gr_preciototal_lbl.setText(String.format("%.2f", Double.parseDouble(precioBase)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @FXML
    void comboRegimenHospedajeListener(ActionEvent event) {

    }

    @FXML
    void dateCheckoutListener(ActionEvent event) {
        LocalDate fechaCheckin = gr_checkin_date.getValue();
        if (fechaCheckin != null) {
            gr_totaldias_lbl.setText(String.valueOf(fechaCheckin.until(gr_checkout_date.getValue()).getDays()));
            if(gr_hotel_cbx.getValue() != null){
                cargarImpuesto();
            }
        }
    }

    @FXML
    void dateCheckinListener(ActionEvent event) throws Exception {
        LocalDate fechaCheckin = gr_checkin_date.getValue();
        if (fechaCheckin != null) {
            Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
                @Override
                public DateCell call(DatePicker datePicker) {
                    return new DateCell() {
                        @Override
                        public void updateItem(LocalDate item, boolean empty) {
                            super.updateItem(item, empty);
                            if (item.isBefore(fechaCheckin) || item.isEqual(fechaCheckin)) {
                                setDisable(true);
                                setStyle("-fx-background-color: #dad5d5;");
                            }
                        }
                    };
                }
            };
            gr_checkout_date.setDayCellFactory(dayCellFactory);
        }
        if(gr_hotel_cbx.getValue() != null && gr_checkin_date.getValue() != null){
            String seleccionado = gr_hotel_cbx.getValue();
            String[] partes = seleccionado.split(",");
            String idHotel = partes[0].replace("ID: ", "").trim();

            Date fechaCheckinSQL =java.sql.Date.valueOf(gr_checkin_date.getValue());
        //    habitacionesDisponiblesShowTableData(idHotel,fechaCheckinSQL );
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
    }*/


/*
    public void habitacionesSelectedShowTableData(HabitacionHotelData habitacionCarrito) {
        try {
            // Obtener los datos actuales de la tabla
            ObservableList<HabitacionHotelData> data = gr_choose_tableview.getItems();

            // Agregar la nueva habitación
            data.add(habitacionCarrito);

            // Volver a establecer los datos de la tabla
            gr_choose_tableview.setItems(data);

            } catch (Exception e) {
            e.printStackTrace();
        }
    }*/


    public void habitacionesSelectedShowTableData(HabitacionHotelData habitacionCarrito) {
        try {
            // Obtener los datos actuales de la tabla
            ObservableList<HabitacionHotelData> data = gr_choose_tableview.getItems();

            // Agregar la nueva habitación
            data.add(habitacionCarrito);

            //crear los botones en cada celda
            Callback<TableColumn<HabitacionHotelData, String>, TableCell<HabitacionHotelData, String>> cellFactory = (param) -> {
                //crear la celda que va a devolver el valor de la propiedad de la persona
                final TableCell<HabitacionHotelData, String> cell = new TableCell<HabitacionHotelData, String>() {
                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            //crar la accion boton para agregar al carrito
                            final Button btn = new Button("Quitar");
                            btn.setStyle(
                                    "-fx-background-color: linear-gradient(to bottom right, #0f6789, #388675); " +
                                            "-fx-background-radius: 5px; " +
                                            "-fx-cursor: hand; " +
                                            "-fx-text-fill: #fff; " +
                                            "-fx-font-family: Arial; " +
                                            "-fx-fond-size: 13px; " +
                                            "cursor: pointer; " +
                                            "fx-alignment: center;"

                            );
                            btn.setOnAction(event -> {
                                HabitacionHotelData h = getTableView().getItems().get(getIndex());
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setContentText("Habitacion eliminada de mi reserva: " + h.getId_habitacion() + " " + h.getTipo() + " " + h.getCapacidad() + " " + h.getNivel() + " " + h.getPrecio_noche());
                                alert.show();
                                // Remove the selected room from the table
                                getTableView().getItems().remove(h);
                            });
                            setGraphic(btn);
                            setText(null);
                        }
                    }

                    ;
                };
                return cell;
            };//fin crear botones en cada celda

            // Add a Spinner to the "cantidad" column

            choose_cantidad.setCellFactory(tc -> new TableCell<>() {
                private final Spinner<Integer> spinner = new Spinner<>(1, 100, 1);

                @Override
                protected void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        HabitacionHotelData habitacion = getTableView().getItems().get(getIndex());
                        // Check if getCantidad_habitaciones() is null
                        Integer cantidad = habitacion.getCantidad_habitaciones();
                        if (cantidad == null) {
                            // If it's null, set a default value
                            cantidad = 1;
                            //agregar valor por defecto a la habitacion de 1
                            habitacion.setCantidad_habitaciones(cantidad);
                        }
                        spinner.getValueFactory().setValue(cantidad);
                        spinner.valueProperty().addListener((obs, oldValue, newValue) -> {
                            habitacion.setCantidad_habitaciones(newValue);
                        });
                        setGraphic(spinner);
                    }
                }
            });

            // Add a TextField to the "descripcion" column
            choose_desc.setCellFactory(tc -> new TableCell<>() {
                private final TextField textField = new TextField();

                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        HabitacionHotelData habitacion = getTableView().getItems().get(getIndex());
                        textField.setText(habitacion.getDescripcion());
                        textField.textProperty().addListener((obs, oldValue, newValue) -> {
                            habitacion.setDescripcion(newValue);
                        });
                        setGraphic(textField);
                    }
                }
            });


            //agregar en la celda la accion de los botones
            choose_action.setCellFactory(cellFactory);

            choose_id.setCellValueFactory(new PropertyValueFactory<>("id_habitacion"));
            choose_tipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
            choose_capacidad.setCellValueFactory(new PropertyValueFactory<>("capacidad"));
            choose_nivel.setCellValueFactory(new PropertyValueFactory<>("nivel"));
            choose_precionoche.setCellValueFactory(new PropertyValueFactory<>("precio_noche"));
            choose_desc.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
            choose_cantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad_habitaciones"));


            // Volver a establecer los datos de la tabla
            gr_choose_tableview.setItems(data);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }




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


            //crear los botones en cada celda
            Callback<TableColumn<HabitacionHotelData, String>, TableCell<HabitacionHotelData, String>> cellFactory = (param) -> {
                //crear la celda que va a devolver el valor de la propiedad de la persona
                final TableCell<HabitacionHotelData, String> cell = new TableCell<HabitacionHotelData, String>(){
                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if(empty){
                            setGraphic(null);
                            setText(null);
                        }else{
                            //crar la accion boton para agregar al carrito
                            final Button btn = new Button("Elegir");
                            btn.setStyle(
                            "-fx-background-color: linear-gradient(to bottom right, #0f6789, #388675); "+
                            "-fx-background-radius: 5px; "+
                            "-fx-cursor: hand; "+
                            "-fx-text-fill: #fff; "+
                            "-fx-font-family: Arial; "+
                            "-fx-fond-size: 13px; "+
                            "cursor: pointer; "+
                             "fx-alignment: center;"

                            );
                            btn.setOnAction(event ->{
                               // HabitacionHotelData habitacionHotelData = gr_habitaciones_tableview.getSelectionModel().getSelectedItem();
                               HabitacionHotelData h = getTableView().getItems().get(getIndex());

                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setContentText("Habitacion seleccionada: "+ h.getId_habitacion() + " " + h.getTipo() + " " + h.getCapacidad() + " " + h.getNivel() + " " + h.getPrecio_noche());
                                alert.show();
                                //enviar la habitacion seleccionada al carrito de compras
                                habitacionesSelectedShowTableData(h);


                            });

                            setGraphic(btn);
                            setText(null);
                        }
                    }
                ;
                };

                return cell;
            };


            hab_action.setCellFactory(cellFactory);


            gr_habitaciones_tableview.setItems(habitacionesListData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    void habitacionSelectData(MouseEvent event) {
        // Obtener la habitación seleccionada
        HabitacionHotelData habitacionSeleccionada = gr_habitaciones_tableview.getSelectionModel().getSelectedItem();

        if (habitacionSeleccionada != null) {
            // Crear una ventana de alerta para mostrar la información
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Información de la habitación");
            alert.setHeaderText("Información de la habitación: " + habitacionSeleccionada.getId_habitacion());
            alert.setContentText("Tipo: " + habitacionSeleccionada.getTipo() + "\n" +
                    "Capacidad: " + habitacionSeleccionada.getCapacidad() + "\n" +
                    "Nivel: " + habitacionSeleccionada.getNivel() + "\n" +
                    "Precio por noche: " + habitacionSeleccionada.getPrecio_noche());
            alert.showAndWait();
        }
    }



    /**
     * Muestra el nombre y el código de un empleado en la interfaz de usuario.
     *
     * @param empleado el objeto Empleado que contiene la información del empleado.
     */
    public void displayEmployeeIDUsername(Empleado empleado, ReservaHotelData reserva){
        username_lbl.setText(empleado.getPersona().getNombre());
        reservaSeleccionada=reserva;
        empleadoLogueado= empleado;
        
        cargarDatosReserva();
    }

    private void cargarDatosReserva() {

        String checkin = reservaSeleccionada .getFecha_checkin().substring(0,10);
        String checkout = reservaSeleccionada.getFecha_checkout().substring(0,10);
        LocalDate localcheckin = LocalDate.parse(checkin, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate localcheckout = LocalDate.parse(checkout, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        String cedulaCliente = reservaSeleccionada.getCedula_cliente();
        for (String item : gr_cliente_cbx.getItems()) {
            if (item.contains("Cedula: " + cedulaCliente)) {
                gr_cliente_cbx.setValue(item);
                break;
            }
        }
        String hotelReserva = reservaSeleccionada.getHotel();
        for (String item : gr_hotel_cbx.getItems()) {
            if (item.contains("Nombre: " + hotelReserva)) {
                //obtener el id del hotel para setear los regimenes correspondientes

                gr_hotel_cbx.setValue(item);

                String seleccionado = gr_hotel_cbx.getValue();
                String[] partes = seleccionado.split(",");
                String idHotel = partes[0].replace("ID: ", "").trim();
                cargarComboRegimenHospedaje(idHotel);
                break;
            }
        }

        String regimenHospedaje = reservaSeleccionada.getRegimen_hospedaje();
        for (String item : gr_regimen_cbx.getItems()) {
            if (item.contains("Descripcion: " + regimenHospedaje)) {
                gr_regimen_cbx.setValue(item);
                break;
            }
        }

        gr_checkin_date.setValue(localcheckin);
        gr_checkout_date.setValue(localcheckout);
        //  gr_cantidadhab_lbl.setText(Integer.toString(reserva.getCantidad_habitaciones()));

        long daysBetween = ChronoUnit.DAYS.between(localcheckin, localcheckout);
        gr_totaldias_lbl.setText(Long.toString(daysBetween));



        //   String impuestoText = reservaSeleccionada.get .getText().replace(",", ".");
        Double impuesto = Math.round(reservaSeleccionada.getTotal_con_impuesto() * 0.1 * 100.0) / 100.0;


        Double precioTotal = reservaSeleccionada.getTotal_con_impuesto() - impuesto;
        gr_preciototal_lbl.setText(String.valueOf(precioTotal));
        gr_impuesto_lbl.setText(String.valueOf(impuesto));
        //    gr_desc_lbl.setText(reserva.getDescripcion());

        // Fetch HabitacionReserva objects associated with the selected reservation
        List<HabitacionReserva> habitacionesReserva = habitacionReservaRepo.findByReserva_IdReserva(reservaSeleccionada.getId_reserva());

        // Convert the HabitacionReserva objects to HabitacionHotelData objects
        ObservableList<HabitacionHotelData> habitacionesListData = FXCollections.observableArrayList();
        for (HabitacionReserva habitacionReserva : habitacionesReserva) {
            HabitacionHotelData habitacion = new HabitacionHotelData(
                    String.valueOf(habitacionReserva.getHabitacion().getIdHabitacion()),
                    habitacionReserva.getHabitacion().getTipoHabitacion().getNombre(),
                    String.valueOf(habitacionReserva.getHabitacion().getTipoHabitacion().getCapacidad()),
                    habitacionReserva.getHabitacion().getNivel().getNombre(),
                    habitacionReserva.getHabitacion().getPrecioNoche(),
                    habitacionReserva.getDescripcion(),
                    habitacionReserva.getCantidad()
            );
            habitacionesListData.add(habitacion);
            habitacionesSelectedShowTableData(habitacion);
        }

        // Set the HabitacionHotelData objects to the gr_choose_tableview
        gr_choose_tableview.setItems(habitacionesListData);

    }


    private void abrirVentanaVolver(ActionEvent event, Empleado empleado) {
        sceneController.cambiarAVentanaReservaHotel(event, empleado);
    }

    @FXML
    void volver(ActionEvent event) {
        abrirVentanaVolver(event, empleadoLogueado);
        btn_volver.getScene().getWindow().hide();
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
       // System.out.println("ReservaHotelRepo: " + reservaHotelRepo);
        choose_id.setCellValueFactory(new PropertyValueFactory<>("id_habitacion"));
        choose_tipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        choose_capacidad.setCellValueFactory(new PropertyValueFactory<>("capacidad"));
        choose_nivel.setCellValueFactory(new PropertyValueFactory<>("nivel"));
        choose_precionoche.setCellValueFactory(new PropertyValueFactory<>("precio_noche"));

        try {
           // reservaHotelShowTableData();
            //reservaSearch();

           // cargarComboPaisHotel();
            cargarComboClienteHotel();
            habitacionesShowTableData();
            cargarComboHoteles();
            cargarDatePicker();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
