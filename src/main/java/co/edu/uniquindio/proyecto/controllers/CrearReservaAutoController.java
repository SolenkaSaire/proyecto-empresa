package co.edu.uniquindio.proyecto.controllers;

import co.edu.uniquindio.proyecto.dto.AutomovilData;
import co.edu.uniquindio.proyecto.dto.HabitacionHotelData;
import co.edu.uniquindio.proyecto.dto.ServiciosAdicionalesData;
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
import javafx.util.Callback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionSystemException;

import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@Controller
public class CrearReservaAutoController implements Initializable {

    /*Repos y Controllers*/
    @Autowired
    private ReservaAutoRepo reservaAutoRepo;

    @Autowired
    private AutomovilRepo automovilRepo;

    @Autowired
    private DetalleReservaAutoRepo detalleReservaAutoRepo;

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
    private TipoServicioRepo tipoServicioRepo;


    @Autowired
    private ServiciosAdicionalesRepo serviciosAdicionalesRepo;
    @Autowired
    private SceneController sceneController;

    /*ENTIDADES*/
    private Empleado empleadoLogueado;
    private Cliente clienteSeleccionado;
    private Hotel hotelSeleccionado;

    private Habitacion habitacionSeleccionada;
    private RegimenHospedaje regimenSeleccionado;

    private List<ServiciosAdicionalesData> listaServiciosAdicionales;

    private Automovil automovilSeleccionado;

    private TipoServicio tipoServicioSeleccionado;


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
    private TableColumn<AutomovilData, String> auto_action;

    @FXML
    private TableColumn<AutomovilData, String> auto_gama;

    @FXML
    private TableColumn<AutomovilData, String> auto_id;

    @FXML
    private TableColumn<AutomovilData, String> auto_marca;

    @FXML
    private TableColumn<AutomovilData, Double> auto_preciodiario;

    @FXML
    private TableColumn<AutomovilData, String> auto_tipo;

    @FXML
    private Button btn_volver;

    @FXML
    private TableColumn<AutomovilData, String> choose_action;

    @FXML
    private TableColumn<AutomovilData, String> choose_cantidad;

    @FXML
    private TableColumn<AutomovilData, String> choose_gama;

    @FXML
    private TableColumn<AutomovilData, String> choose_id;

    @FXML
    private TableColumn<AutomovilData, String> choose_marca;

    @FXML
    private TableColumn<AutomovilData, Double> choose_preciodiario;

    @FXML
    private TableColumn<AutomovilData, String> choose_tipo;

    @FXML
    private Label fecha_actual_lbl;

    @FXML
    private TableView<AutomovilData> ga_autos_tableview;

    @FXML
    private TableView<AutomovilData> ga_choose_tableview;

    @FXML
    private TextField ga_destino_lbl;

    @FXML
    private DatePicker ga_fechadev_date;

    @FXML
    private DatePicker ga_fechainicio_date;

    @FXML
    private TextField ga_origen_lbl;

    @FXML
    private ComboBox<String> gr_cliente_cbx;

    @FXML
    private Button gr_guardar_btn;


    @FXML
    private Label gr_preciototal_lbl;

    @FXML
    private Button gr_servicios_btn;

    @FXML
    private Label gr_totaldias_lbl;

    @FXML
    private AnchorPane reserva_hotel_form;

    @FXML
    private Label username_lbl;

    @FXML
    void crearBtn(ActionEvent event) {
        verificarReservaAutos();
    }

    private void verificarReservaAutos() {
        //imprimir la informacion de la tabla habitaciones
        List<AutomovilData> listaAutomoviles=ga_choose_tableview.getItems();
        //recorrer la lista e imprimir los datos de la tabla
        for (AutomovilData automovilData : listaAutomoviles) {
            System.out.println("automovil: " + automovilData.toString());
        }
        //verificar que el text field de descripcion no este vacio en choose_desc
        String mensaje = verificarInformacionReserva(gr_cliente_cbx.getValue(), ga_origen_lbl.getText(),
                ga_destino_lbl.getText(), ga_fechainicio_date.getValue(), ga_fechadev_date.getValue(),listaAutomoviles);

        if (!mensaje.equalsIgnoreCase("")) {
            mostrarMensaje("Gestión de Reservas Hotel", "Información", mensaje,
                    Alert.AlertType.WARNING);
            return;
        } else {
            crearReservaAuto(listaAutomoviles);
        }
    }

    private void crearReservaAuto(List<AutomovilData> listaAutomoviles){
        try {
            ReservaAutomovil reservaAutomovil =crearReservaAuto();
            if (reservaAutomovil == null) {
                return;
            }

            List<DetalleReservaAutomovil> listaAux = crearDetalleReservaAutomovil(listaAutomoviles, reservaAutomovil);

            List<ServiciosAdicionales> listaAdicionales = crearServiciosAdicionales(listaAux);
            mostrarMensajeReservaCreada(reservaAutomovil, listaAux, listaAdicionales);
           } catch (TransactionSystemException e) {
            manejarExcepcion(e);
        }
    }


    private ReservaAutomovil crearReservaAuto() {

        String idCliente = obtenerIdCliente();
        Double precioTotal = obtenerPrecioTotal();
        //lanzar mensaje con informacion de confirmacion incluyendo el precio total, el impuesto y el total a pagar
        //alerta tipo confirmacion para presionar si o no
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación de Reserva");
        alert.setHeaderText("Confirmación de Reserva");
        alert.setContentText("¿Está seguro de crear la reserva?\n" +
                "Precio Total: " + precioTotal + "\n" +
                "Impuesto: " + precioTotal*0.1 + "\n" +
                "Total a pagar: " +(precioTotal*0.1) + precioTotal + "\n");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            // ... user chose OK
            //crear la reserva
            clienteSeleccionado = clienteRepo.findById(Integer.valueOf(idCliente)).get();
            java.util.Date fechaInicio = java.sql.Date.valueOf(ga_fechainicio_date.getValue());
            java.util.Date fechaDev = java.sql.Date.valueOf(ga_fechadev_date.getValue());

            ReservaAutomovil reservaAutomovil = new ReservaAutomovil(fechaInicio, fechaDev
                    , ga_origen_lbl.getText(), ga_destino_lbl.getText(),
                    empleadoLogueado, clienteSeleccionado);

            reservaAutoRepo.save(reservaAutomovil);
            return reservaAutomovil;
        } else {
            //detener la creacion de la reserva
            return null;
        }

    }


    private Double obtenerPrecioTotal() {
        List<AutomovilData> listaAutomoviles = ga_choose_tableview.getItems();
        Double precioTotal = 0.0;
        for (AutomovilData automovil : listaAutomoviles) {
            precioTotal += automovil.getPrecio_diario() * automovil.getCantidad_autos();
        }

        return precioTotal;
    }


    private String obtenerIdCliente() {
        String[] partesCliente = gr_cliente_cbx.getValue().split(",");
        String cedulaCliente = partesCliente[1].replace("Cedula: ", "").trim();
        return clienteRepo.buscarByCedula(cedulaCliente);
    }

/*
    private List<ServiciosAdicionales> crearServiciosAdicionales(List<DetalleReservaAutomovil> listaAux) {

        List<ServiciosAdicionalesData> serviciosAdicionalesDataList = listaServiciosAdicionales;
        //verificar si la lista esta vacia, de ser asi, retornar nada
        if (serviciosAdicionalesDataList.size() == 0) {
            return null;
        }else{
            //si no esta vacia, crear los servicios adicionales
            List<ServiciosAdicionales> listaServiciosAdicionales = new ArrayList<>();
            for (DetalleReservaAutomovil detalleReservaAutomovil : listaAux) {
                for (ServiciosAdicionalesData serviciosAdicionalesData : serviciosAdicionalesDataList) {
                    tipoServicioSeleccionado=tipoServicioRepo.findByIdTipo(serviciosAdicionalesData.getId());
                    ServiciosAdicionales serviciosAdicionales = new ServiciosAdicionales(
                            serviciosAdicionalesData.getDescripcion(),
                            detalleReservaAutomovil,
                            tipoServicioSeleccionado
                    );
                    serviciosAdicionales.setDetalleReservaAutomovil(detalleReservaAutomovil);
                    serviciosAdicionales.setDescripcion(serviciosAdicionalesData.getDescripcion());
                    serviciosAdicionales.setPrecio(serviciosAdicionalesData.getPrecio());
                    listaServiciosAdicionales.add(serviciosAdicionales);
                }
            }

            return listaServiciosAdicionales;
        }

    }*/


    private List<ServiciosAdicionales> crearServiciosAdicionales(List<DetalleReservaAutomovil> listaAux) {

        List<ServiciosAdicionalesData> serviciosAdicionalesDataList = listaServiciosAdicionales;
        //verificar si la lista esta vacia, de ser asi, retornar nada
        if (serviciosAdicionalesDataList.size() == 0) {
            return null;
        }else{
            //si no esta vacia, crear los servicios adicionales
            List<ServiciosAdicionales> listaServiciosAdicionales = new ArrayList<>();
            for (DetalleReservaAutomovil detalleReservaAutomovil : listaAux) {
                for (ServiciosAdicionalesData serviciosAdicionalesData : serviciosAdicionalesDataList) {
                    tipoServicioSeleccionado=tipoServicioRepo.findByIdTipo(serviciosAdicionalesData.getId());
                    ServiciosAdicionales serviciosAdicionales = new ServiciosAdicionales(
                            serviciosAdicionalesData.getDescripcion(),
                            detalleReservaAutomovil,
                            tipoServicioSeleccionado
                    );
                    serviciosAdicionalesRepo.save(serviciosAdicionales);

                   listaServiciosAdicionales.add(serviciosAdicionales);
                }
            }

            return listaServiciosAdicionales;
        }

    }


    private List<DetalleReservaAutomovil> crearDetalleReservaAutomovil(List<AutomovilData> listaAutomoviles, ReservaAutomovil reservaAutomovil) {
        List<DetalleReservaAutomovil> listaAux = new ArrayList<>();
        for (AutomovilData automovil : listaAutomoviles) {
            automovilSeleccionado = automovilRepo.findById(automovil.getId_automovil());
            DetalleReservaAutomovil detalleReservaAutomovil = new DetalleReservaAutomovil(reservaAutomovil, automovilSeleccionado, automovil.getCantidad_autos(),
                    automovil.getPrecio_diario(),LocalDate.now());
            listaAux.add(detalleReservaAutomovil);
            detalleReservaAutoRepo.save(detalleReservaAutomovil);
        }

        return listaAux;
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

    private void mostrarMensajeReservaCreada(ReservaAutomovil reservaAutomovil, List<DetalleReservaAutomovil> listaAux, List<ServiciosAdicionales> listaAdicionales) {
        StringBuilder message = new StringBuilder();
        message.append("Reservación creada con éxito\n");
        message.append("Creación Exitosa: \n");
        message.append(reservaAutomovil.toString());
        message.append("\nInformacion habitacion reserva:\n");

        for (DetalleReservaAutomovil detalleReservaAutomovil : listaAux) {
            message.append("Automovil ID: ").append(detalleReservaAutomovil.getAutomovil().getIdAutomovil()).append("\n");
            message.append("Tipo: ").append(detalleReservaAutomovil.getAutomovil().getTipoAutomovil().getDescripcion()).append("\n");
            message.append("Marca: ").append(detalleReservaAutomovil.getAutomovil().getMarca().getDescripcion()).append("\n");
            message.append("Gama: ").append(detalleReservaAutomovil.getAutomovil().getGama().getDescripcion()).append("\n");
            message.append("Precio por dia: ").append(detalleReservaAutomovil.getAutomovil().getPrecioDia()).append("\n");
            message.append("Cantidad: ").append(detalleReservaAutomovil.getCantidad()).append("\n");
           }

        if (listaAdicionales != null) {
            message.append("\nInformacion servicios adicionales:\n");
            for (ServiciosAdicionales serviciosAdicionales : listaAdicionales) {
                message.append("Servicio: ").append(serviciosAdicionales.getTipoServicio().getDescripcion()).append("\n");
                message.append("Carro: ").append(serviciosAdicionales.getDetalleReservaAutomovil().getAutomovil().getIdAutomovil()).append("\n");
                message.append("Precio: ").append(serviciosAdicionales.getTipoServicio().getPrecio()).append("\n");
            }
        }

        mostrarMensaje("Gestión de Reservas Hotel", "Reserva Creada con éxito", message.toString(), Alert.AlertType.INFORMATION);
    }



    private void mostrarMensaje(String gestiónDeReservasHotel, String información, String mensaje, Alert.AlertType warning) {
        alert = new Alert(warning);
        alert.setTitle(gestiónDeReservasHotel);
        alert.setHeaderText(información);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private String verificarInformacionReserva(String cliente, String origen, String destino, LocalDate fechaInicio, LocalDate fechaDev, List<AutomovilData> listaAutomoviles) {
        //verificar que la informacion no este nula y almacenar en un mensaje
        String mensaje = "";

        if (cliente == null) {
            mensaje += "Debe seleccionar un cliente.\n";
        }
        if (origen.equalsIgnoreCase("")) {
            mensaje += "Debe ingresar un origen.\n";
        }
        if (destino.equalsIgnoreCase("")) {
            mensaje += "Debe ingresar un destino.\n";
        }
        if (fechaInicio == null) {
            mensaje += "Debe seleccionar una fecha de inicio.\n";
        }
        if (fechaDev == null) {
            mensaje += "Debe seleccionar una fecha de devolución.\n";
        }
        if (listaAutomoviles.size() == 0) {
            mensaje += "Debe seleccionar por lo menos un automovil.\n";
        }
        return mensaje;
    }


    @FXML
    void automovilSelectData(MouseEvent event) {
        //mostrar mensaje informacion sobre el auto seleccionado de la tabla autos
        AutomovilData automovil = ga_autos_tableview.getSelectionModel().getSelectedItem();

        if (habitacionSeleccionada != null) {
            // Crear una ventana de alerta para mostrar la información
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
               alert.setTitle("Información del automovil");
            alert.setHeaderText("Información del automovil");
            alert.setContentText("ID: " + automovil.getId_automovil() + "\n" +
                    "Tipo: " + automovil.getTipo() + "\n" +
                    "Marca: " + automovil.getMarca() + "\n" +
                    "Gama: " + automovil.getGama() + "\n" +
                    "Precio diario: " + automovil.getPrecio_diario() + "\n");
            alert.showAndWait();
        }

    }

    @FXML
    void serviciosBtn(ActionEvent event) {
        //verificar que haya por lo menos un automovil en el carrito
        if(ga_choose_tableview.getItems().size() == 0){
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Debe seleccionar por lo menos un automovil.");
            alert.show();
        }else {
            //abrir ventana servicios
            abrirVentanaServicios(event, empleadoLogueado, listaServiciosAdicionales, "Crear");
        }
      //  gr_servicios_btn.getScene().getWindow().hide();
    }

    private void abrirVentanaServicios(ActionEvent event, Empleado empleado, List<ServiciosAdicionalesData> listaServicios, String tipoVentana) {
      sceneController.cambiarAVentanaServiciosAdicionales(event, empleado, listaServicios, tipoVentana);
    }

    private void abrirVentanaVolver(ActionEvent event, Empleado empleado) {

        sceneController.cambiarAVentanaReservaAuto(event, empleado);
    }

    @FXML
    void volver(ActionEvent event) {
        abrirVentanaVolver(event, empleadoLogueado);
        btn_volver.getScene().getWindow().hide();
    }




    /**
     * Muestra el nombre y el código de un empleado en la interfaz de usuario.
     *
     * @param empleado el objeto Empleado que contiene la información del empleado.
     */
    public void displayEmployeeIDUsername(Empleado empleado){
        empleadoLogueado = empleado;
        username_lbl.setText(empleado.getPersona().getNombre());
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


    void cargarComboClienteHotel() {
        try {
            List<Map<String, Object>> clientes =   reservaAutoRepo.obtenerClientes();
                    //reservaHotelRepo.obtenerClientes();

            ObservableList<String> datos = FXCollections.observableArrayList();
            for (Map<String, Object> cliente : clientes) {
                String id = String.valueOf(cliente.get("id"));
                String cedula = String.valueOf(cliente.get("cedula"));
                String nombre = (String) cliente.get("nombre");
                datos.add("ID: " + id + ", Cedula: " + cedula + ", Nombre: " + nombre);
            }
            gr_cliente_cbx.setItems(datos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    void dateCheckoutListener(ActionEvent event) {
        LocalDate fechaCheckin = ga_fechainicio_date.getValue();//gr_checkin_date.getValue();
        if (fechaCheckin != null) {
            gr_totaldias_lbl.setText(String.valueOf(fechaCheckin.until(ga_fechadev_date.getValue()).getDays()));



          /*  if (gr_hotel_cbx.getValue() != null) {
                cargarImpuesto();
                CALCULAR IMPUESTO POR TOTAL  * 0.1
            }*/
        }
    }

    @FXML
    void dateCheckinListener(ActionEvent event) throws Exception {
        LocalDate fechaCheckin = ga_fechainicio_date.getValue();
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
            ga_fechadev_date.setDayCellFactory(dayCellFactory);
        }

        /*
        if (gr_hotel_cbx.getValue() != null && gr_checkin_date.getValue() != null) {
            String seleccionado = gr_hotel_cbx.getValue();
            String[] partes = seleccionado.split(",");
            String idHotel = partes[0].replace("ID: ", "").trim();

            Date fechaCheckinSQL = java.sql.Date.valueOf(gr_checkin_date.getValue());
            //    habitacionesDisponiblesShowTableData(idHotel,fechaCheckinSQL );
        }*/
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
            ga_fechainicio_date.setDayCellFactory(dayCellFactory);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }




    public void automovilesShowTableData() {

        try {
            List<Map<String, Object>> automovilesData = reservaAutoRepo.obtenerAutomovilesData();
                    //reservaHotelRepo.obtenerAutomovilesData();

            ObservableList<AutomovilData> automovilesListData = FXCollections.observableArrayList();
            for (Map<String, Object> automovilData : automovilesData) {
                AutomovilData automovil = new AutomovilData(
                        String.valueOf(automovilData.get("id_automovil")),
                        String.valueOf(automovilData.get("tipo")),
                        String.valueOf(automovilData.get("marca")),
                        String.valueOf(automovilData.get("gama")),
                        Double.valueOf(String.valueOf(automovilData.get("precio_diario"))));
                automovilesListData.add(automovil);
            }

            auto_id.setCellValueFactory(new PropertyValueFactory<>("id_automovil"));
            auto_tipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
            auto_marca.setCellValueFactory(new PropertyValueFactory<>("marca"));
            auto_gama.setCellValueFactory(new PropertyValueFactory<>("gama"));
            auto_preciodiario.setCellValueFactory(new PropertyValueFactory<>("precio_diario"));



            //crear los botones en cada celda
            Callback<TableColumn<AutomovilData, String>, TableCell<AutomovilData, String>> cellFactory = (param -> {
                //crear la celda que va a devolver el valor de la propiedad de la persona
                final TableCell<AutomovilData, String> cell = new TableCell<AutomovilData, String>() {
                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);

                        } else {
                            //crar la accion boton para agregar al carrito
                            final Button btn = new Button("Elegir");
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
                                // HabitacionHotelData habitacionHotelData = gr_habitaciones_tableview.getSelectionModel().getSelectedItem();
                                AutomovilData a = getTableView().getItems().get(getIndex());

                                // Get the current data in the cart
                                ObservableList<AutomovilData> cartData = ga_choose_tableview.getItems();

                                // Check if the selected room is already in the cart
                                if (cartData.contains(a)) {
                                    // If the room is already in the cart, show a warning message
                                    Alert alert = new Alert(Alert.AlertType.WARNING);
                                    alert.setContentText("El automovil seleccionado ya está en el carrito.");
                                    alert.show();
                                } else {
                                    // If the room is not in the cart, add it to the cart and show a confirmation message
                                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                    alert.setContentText("Automovil seleccionado: " + a.getId_automovil() + " " + a.getTipo() + " " + a.getMarca() + " " + a.getGama() + " " + a.getPrecio_diario());
                                    alert.show();
                                    automovilesSelectedShowTableData(a);
                                }

                            });

                            setGraphic(btn);
                            setText(null);
                        }
                    }

                    ;
                };

                return cell;
            });//fin crear

            //agregar en la celda la accion de los botones
            auto_action.setCellFactory(cellFactory);

            //agregar los datos a la tabla
            ga_autos_tableview.setItems(automovilesListData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void automovilesSelectedShowTableData(AutomovilData automovilCarrito) {

            try {
                // Obtener los datos actuales de la tabla
                ObservableList<AutomovilData> data = ga_choose_tableview.getItems();

                // Agregar la nueva habitación
                data.add(automovilCarrito);

                //crear los botones en cada celda
                Callback<TableColumn<AutomovilData, String>, TableCell<AutomovilData, String>> cellFactory = (param) -> {
                    //crear la celda que va a devolver el valor de la propiedad de la persona
                    final TableCell<AutomovilData, String> cell = new TableCell<AutomovilData, String>() {
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
                                    AutomovilData a = getTableView().getItems().get(getIndex());
                                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                    alert.setContentText("Habitacion eliminada de mi reserva: " + a.getId_automovil() + " " + a.getTipo() + " " + a.getMarca() + " " + a.getGama() + " " + a.getPrecio_diario());
                                    alert.show();
                                    // Remove the selected room from the table
                                    getTableView().getItems().remove(a);
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
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            AutomovilData automovil = getTableView().getItems().get(getIndex());
                            // Check if getCantidad_habitaciones() is null
                            Integer cantidad = automovil.getCantidad_autos();
                            if (cantidad == null) {
                                // If it's null, set a default value
                                cantidad = 1;
                                //agregar valor por defecto a la habitacion de 1
                                automovil.setCantidad_autos(cantidad);
                            }
                            spinner.getValueFactory().setValue(cantidad);
                            spinner.valueProperty().addListener((obs, oldValue, newValue) -> {
                                automovil.setCantidad_autos(newValue);
                            });
                            setGraphic(spinner);
                        }
                    }
                });

                //agregar en la celda la accion de los botones
                choose_action.setCellFactory(cellFactory);
                // Volver a establecer los datos de la tabla
                ga_choose_tableview.setItems(data);

            } catch (Exception e) {
                e.printStackTrace();
            }

    }



    @FXML
    public void initialize(URL location, ResourceBundle resources) {

        runTime();
        // System.out.println("ReservaHotelRepo: " + reservaHotelRepo);


        choose_id.setCellValueFactory(new PropertyValueFactory<>("id_automovil"));
        choose_tipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        choose_marca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        choose_gama.setCellValueFactory(new PropertyValueFactory<>("gama"));
        choose_preciodiario.setCellValueFactory(new PropertyValueFactory<>("precio_diario"));


        try {
            // reservaHotelShowTableData();
            //reservaSearch();

            // cargarComboPaisHotel();
            cargarComboClienteHotel();
            automovilesShowTableData();
            cargarDatePicker();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void devolverAReservaAutoCreacion(ObservableList<ServiciosAdicionalesData> serviciosAdicionalesDataList) {
        listaServiciosAdicionales = serviciosAdicionalesDataList;
        //sout a la lista
        for (ServiciosAdicionalesData s : serviciosAdicionalesDataList) {
            System.out.println("los servicios seleccionados son: "+s.getNombre() + " " + s.getInformacion() + " " + s.getPrecio() + " " + s.getDescripcion());
        }
    }
}
