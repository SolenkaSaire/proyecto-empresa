package co.edu.uniquindio.proyecto.controllers;

import co.edu.uniquindio.proyecto.dto.CompraPaqueteData;
import co.edu.uniquindio.proyecto.dto.PaqueteData;
import co.edu.uniquindio.proyecto.model.*;
import co.edu.uniquindio.proyecto.repositories.ClienteRepo;
import co.edu.uniquindio.proyecto.repositories.CompraPaqueteRepo;
import co.edu.uniquindio.proyecto.repositories.DetalleCompraPaqueteRepo;
import co.edu.uniquindio.proyecto.repositories.PaqueteTuristicoRepo;
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
import java.util.*;


@Controller
public class ActualizarCompraPaqueteController implements Initializable {

    /*Repos y Controllers*/
    @Autowired
    private CompraPaqueteRepo compraPaqueteRepo;
    @Autowired
    private PaqueteTuristicoRepo paqueteRepo;
    @Autowired
    private ClienteRepo clienteRepo;
    @Autowired
    private SceneController sceneController;

    @Autowired
    private DetalleCompraPaqueteRepo detalleCompraPaqueteRepo;

    /*ENTIDADES*/

    private CompraPaqueteData compraPaqueteData;
    private Empleado empleadoLogueado;
    private Cliente clienteSeleccionado;

    private Compra compraActualizar;

    private MetodoPago metodoSeleccionado;

    private PaqueteTuristico paqueteSeleccionado;
    /*MAPAS */
    private Map<String, Integer> mapaPaises = new HashMap<>();
    private HashMap<String, String[]> mapaClientes;
    private Alert alert;

    /*FXML COMPONENTES*/
    @FXML
    private Button btn_volver;
    @FXML
    private TableColumn<PaqueteData, String> choose_action;

    @FXML
    private TableColumn<PaqueteData, Integer> choose_cantidad;

    @FXML
    private TableColumn<PaqueteData, String> choose_detalle;

    @FXML
    private TableColumn<PaqueteData, String> choose_fecha;

    @FXML
    private TableColumn<PaqueteData, String> choose_id;

    @FXML
    private TableColumn<PaqueteData, String> choose_info;

    @FXML
    private TableColumn<PaqueteData, String> choose_nombre;

    @FXML
    private TableColumn<PaqueteData, Double> choose_precio;

    @FXML
    private AnchorPane compra_paquete_form;

    @FXML
    private TableColumn<PaqueteData, String> cp_action;

    @FXML
    private TableView<PaqueteData> cp_choose_tableview;

    @FXML
    private ComboBox<String> cp_cliente_cbx;

    @FXML
    private Button cp_crear_btn;

    @FXML
    private TableColumn<PaqueteData, String> cp_descripcion;

    @FXML
    private TextField cp_descripcion_lbl;

    @FXML
    private TableColumn<PaqueteData, String> cp_fecha;

    @FXML
    private DatePicker cp_fecha_date;

    @FXML
    private TableColumn<PaqueteData, String> cp_id;

    @FXML
    private ComboBox<String> cp_metodopago_cbx;

    @FXML
    private TableColumn<PaqueteData, String> cp_nombre;

    @FXML
    private TableView<PaqueteData> cp_paquetes_tableview;

    @FXML
    private TableColumn<PaqueteData, Double> cp_precio;

    @FXML
    private Label cp_preciototal_lbl;

    @FXML
    private Label fecha_actual_lbl;

    @FXML
    private Label username_lbl;

    @FXML
    void crearBtn(ActionEvent event) {
        verificarCompraPaquete();

    }


    private void verificarCompraPaquete() {

        // Obtener la lista de paquetes seleccionados
        List<PaqueteData> listaPaquetes = cp_choose_tableview.getItems();
        for (PaqueteData paqueteData : listaPaquetes) {
            System.out.println("Paquete elegido: " + paqueteData.toString());
        }

        // Verificar que los campos necesarios no estén vacíos
        String mensaje = verificarInformacionReserva(cp_cliente_cbx.getValue(), cp_metodopago_cbx.getValue(), listaPaquetes);

        if (!mensaje.equalsIgnoreCase("")) {
            mostrarMensaje("Gestión de Compra Paquete", "Información", mensaje, Alert.AlertType.WARNING);
            return;
        } else {
            modificarCompraPaquete(listaPaquetes);
        }


    }

    private void modificarCompraPaquete(List<PaqueteData> listaPaquetes) {
        try{
            List<PaqueteData>existentes = new ArrayList<>();
            List<PaqueteData>no_existentes = new ArrayList<>();
            List<PaqueteData>antiguosPorEliminar =listaPaquetes;

            for(PaqueteData paqueteData: listaPaquetes){
               DetalleCompraPaquete detallePaquetelist= detalleCompraPaqueteRepo.findByBothId(Integer.parseInt(paqueteData.getId_paquete()),Integer.parseInt(compraPaqueteData.getId_compra()));
                if(detallePaquetelist==null){
                    no_existentes.add(paqueteData);
                }else{
                    antiguosPorEliminar.remove(paqueteData);
                    existentes.add(paqueteData);
                }
            }

            if(antiguosPorEliminar.size()>0) {
                for (PaqueteData paqueteData : antiguosPorEliminar) {
                    DetalleCompraPaquete detallePaquete = detalleCompraPaqueteRepo.findByBothId(Integer.parseInt(paqueteData.getId_paquete()),Integer.parseInt(compraPaqueteData.getId_compra()));
                    if (detallePaquete != null) {
                        detalleCompraPaqueteRepo.delete(detallePaquete);
                    }
                }
            }

            Compra compra = actualizacionCompraPaquete();

            List<DetalleCompraPaquete> listaCreada = creacionDetalleCompraPaquete(compra, no_existentes);
            List<DetalleCompraPaquete> listaActualizada = actualizacionDetalleCompraPaquete(compra, existentes);

            List<DetalleCompraPaquete> listaDetalleCompra = new ArrayList<>();
            listaDetalleCompra.addAll(listaCreada);
            listaDetalleCompra.addAll(listaActualizada);

            mostrarMensajeCompraCreada(compra, listaDetalleCompra);



            /*
            Compra compra = actualizacionCompraArticulo();
            List<DetalleCompraPaquete> listaDetalleCompra = creacionDetalleCompraArticulo(compra, listaPaquetes);
            mostrarMensajeCompraCreada(compra, listaDetalleCompra);*/
        }catch  (TransactionSystemException e) {
            manejarExcepcion(e);
        }
    }

    private List<DetalleCompraPaquete> actualizacionDetalleCompraPaquete(Compra compra, List<PaqueteData> existentes) {
        List<DetalleCompraPaquete> listaDetalleCompra = new ArrayList<>();
        for (PaqueteData paqueteData : existentes) {
            DetalleCompraPaquete detalleCompra = detalleCompraPaqueteRepo.findByBothId(Integer.parseInt(compraPaqueteData.getId_compra()), Integer.parseInt(paqueteData.getId_paquete()));
            detalleCompra.setPaqueteTuristico(paqueteRepo.obtener(Integer.parseInt(paqueteData.getId_paquete())));
            detalleCompra.setCompra(compra);
            detalleCompra.setDetalle(paqueteData.getDetalle());
            detalleCompra.setCantidad(paqueteData.getCantidad_boletas());
            detalleCompra.setEstado("Confirmada");
            detalleCompraPaqueteRepo.save(detalleCompra);
            listaDetalleCompra.add(detalleCompra);
        }
        return listaDetalleCompra;
    }

    private List<DetalleCompraPaquete> creacionDetalleCompraPaquete(Compra compra, List<PaqueteData> noExistentes) {
        List<DetalleCompraPaquete> listaDetalleCompra = new ArrayList<>();
        for (PaqueteData paqueteData : noExistentes) {

            DetalleCompraPaquete detalleCompra = new DetalleCompraPaquete();
            detalleCompra.setPaqueteTuristico(paqueteRepo.obtener(Integer.parseInt(paqueteData.getId_paquete())));
            detalleCompra.setCompra(compra);
            detalleCompra.setDetalle(paqueteData.getDetalle());
            detalleCompra.setCantidad(paqueteData.getCantidad_boletas());
            detalleCompra.setEstado("Confirmada");
            detalleCompraPaqueteRepo.save(detalleCompra);
            listaDetalleCompra.add(detalleCompra);
        }
        return listaDetalleCompra;
    }


    private Compra actualizacionCompraPaquete() {
        String idCliente = obtenerIdCliente();
        String idMetodoPago = obtenerIdMetodoPago();
        metodoSeleccionado = compraPaqueteRepo.findMetodoById(Integer.parseInt(idMetodoPago));
        clienteSeleccionado = clienteRepo.obtener(Integer.parseInt(idCliente));

        compraActualizar = compraPaqueteRepo.obtener(Integer.parseInt(compraPaqueteData.getId_compra()));
        compraActualizar.setCliente(clienteSeleccionado);
        compraActualizar.setMetodoPago(metodoSeleccionado);
        compraActualizar.setDescripcion(cp_descripcion_lbl.getText());
        compraActualizar.setEmpleado(empleadoLogueado);

        compraPaqueteRepo.save(compraActualizar);



        return compraActualizar;
    }



    private String obtenerIdMetodoPago() {
        String[] partesMetodo = cp_metodopago_cbx.getValue().split(","); //gr_cliente_cbx.getValue().split(",");
        String idMetodoPago = partesMetodo[0].replace("ID: ", "").trim();
        return idMetodoPago;

    }
    private String obtenerIdCliente() {
        String[] partesCliente = cp_cliente_cbx.getValue().split(",");
        String cedulaCliente = partesCliente[1].replace("Cedula: ", "").trim();
        return clienteRepo.buscarByCedula(cedulaCliente);
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


    private void mostrarMensajeCompraCreada(Compra compra, List<DetalleCompraPaquete> listaDetalleCompra) {
        StringBuilder mensaje = new StringBuilder();
        mensaje.append("Compra actualizada exitosamente.\n");
        mensaje.append("Actualización Exitosa: \n");
        mensaje.append(compra.toString() + "\n");
        mensaje.append("Detalle Compra: \n");
        for (DetalleCompraPaquete detalleCompra : listaDetalleCompra) {
            mensaje.append(detalleCompra.toString() + "\n");
        }
        mostrarMensaje("Gestión de Compra Paquete", "Información", mensaje.toString(),
                Alert.AlertType.INFORMATION);
    }



    private void mostrarMensaje(String gestiónDeReservasHotel, String información, String mensaje, Alert.AlertType warning) {
        alert = new Alert(warning);
        alert.setTitle(gestiónDeReservasHotel);
        alert.setHeaderText(información);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }


    private String verificarInformacionReserva(String cliente, String metodo, List<PaqueteData> listaPaquetes) {
        String mensaje = "";
        if(cp_descripcion_lbl.getText().isEmpty()){
            mensaje += "Debe ingresar una descripción para la compra.\n";
        }

        if (cliente == null) {
            mensaje += "Debe seleccionar un cliente.\n";
        }

        if (metodo == null) {
            mensaje += "Debe seleccionar un método de pago.\n";
        }

        if (listaPaquetes.isEmpty()) {
            mensaje += "Debe seleccionar al menos un paquete.\n";
        }
        for (PaqueteData paqueteData : listaPaquetes) {
            if (paqueteData.getDetalle() == null || paqueteData.getDetalle().equalsIgnoreCase("")) {
                mensaje += "Debe ingresar un detalle para el paquete " + paqueteData.getId_paquete() + ".\n";
            }
        }

        return mensaje;
    }

    @FXML
    void habitacionSelectData(MouseEvent event) {

    }

    private void abrirVentanaVolver(ActionEvent event, Empleado empleado) {
        sceneController.cambiarAVentanaCompraPaquete(event, empleado);
    }

    @FXML
    void volver(ActionEvent event) {
        abrirVentanaVolver(event, empleadoLogueado);
        btn_volver.getScene().getWindow().hide();
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        runTime();

        choose_id.setCellValueFactory(new PropertyValueFactory<>("id_paquete"));
        choose_nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        choose_info.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        choose_precio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        choose_fecha.setCellValueFactory(new PropertyValueFactory<>("fecha_paquete"));


        try {
            cargarComboCliente();
            cargarComboMetodoPago();
            paqueteShowTableData();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void displayEmployeeIDUsername(Empleado empleado, CompraPaqueteData compraPaquete){
        username_lbl.setText(empleado.getPersona().getNombre());
        compraPaqueteData=compraPaquete;
        empleadoLogueado= empleado;

        cargarDatosReserva();
    }

    private void cargarDatosReserva() {
        String cedulaCliente = compraPaqueteData.getCedula_cliente();
        for (String cliente : cp_cliente_cbx.getItems()) {
            if (cliente.contains(cedulaCliente)) {
                cp_cliente_cbx.setValue(cliente);
                break;
            }
        }

        String metodoPago = compraPaqueteData.getMetodo_pago();
        for (String metodo : cp_metodopago_cbx.getItems()) {
            if (metodo.contains(metodoPago)) {
                cp_metodopago_cbx.setValue(metodo);
                break;
            }
        }

        Compra compra = compraPaqueteRepo.obtener(Integer.parseInt(compraPaqueteData.getId_compra()));
        cp_descripcion_lbl.setText(compra.getDescripcion());

        //fetch DetallePaquete objects associated with the CompraPaqueteData object
        List<DetalleCompraPaquete> listaDetalleCompra = detalleCompraPaqueteRepo.findByCompraId(Integer.parseInt(compraPaqueteData.getId_compra()));


        //convert the DetallePaquete to PaqueteData objects
        ObservableList<PaqueteData> paquetesListData = FXCollections.observableArrayList();
        for (DetalleCompraPaquete detalleCompra : listaDetalleCompra) {
            //convert data fecha to string
            String fecha = detalleCompra.getPaqueteTuristico().getFechaPaquete().toString();
            PaqueteData paquete = new PaqueteData(
                    String.valueOf(detalleCompra.getPaqueteTuristico().getIdPaqueteTuristico()),
                    detalleCompra.getPaqueteTuristico().getNombre(),
                    detalleCompra.getPaqueteTuristico().getDescripcion(),
                    detalleCompra.getPaqueteTuristico().getPrecio(),
                    fecha,
                    detalleCompra.getCantidad(),
                    detalleCompra.getDetalle()
            );
            paquetesListData.add(paquete);
            paquetesSelectedShowTableData(paquete);

        }

        //set the PaqueteData objects to the table
        cp_choose_tableview.setItems(paquetesListData);



    }


    private void paqueteShowTableData() {
        try {
            List<Map<String, Object>> paquetesData = compraPaqueteRepo.obtenerPaquetesData();

            ObservableList<PaqueteData> paquetesListData = FXCollections.observableArrayList();
            for (Map<String, Object> paqueteData : paquetesData) {
                PaqueteData paquete = new PaqueteData(
                        String.valueOf(paqueteData.get("id_paquete")),
                        String.valueOf(paqueteData.get("nombre")),
                        String.valueOf(paqueteData.get("descripcion")),
                        Double.valueOf(String.valueOf(paqueteData.get("precio"))),
                        String.valueOf(paqueteData.get("fecha_paquete"))
                );
                paquetesListData.add(paquete);
            }

            cp_id.setCellValueFactory(new PropertyValueFactory<>("id_paquete"));
            cp_nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
            cp_descripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
            cp_precio.setCellValueFactory(new PropertyValueFactory<>("precio"));
            cp_fecha.setCellValueFactory(new PropertyValueFactory<>("fecha_paquete"));

            //crear los botones en cada celda
            Callback<TableColumn<PaqueteData, String>, TableCell<PaqueteData, String>> cellFactory = (param -> {
                //crear la celda que va a devolver el valor de la propiedad de la persona
                final TableCell<PaqueteData, String> cell = new TableCell<PaqueteData, String>() {
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
                                PaqueteData p = getTableView().getItems().get(getIndex());

                                // Get the current data in the cart
                                ObservableList<PaqueteData> cartData = cp_choose_tableview.getItems();

                                // Check if the selected room is already in the cart
                                if (cartData.contains(p)) {
                                    // If the room is already in the cart, show a warning message
                                    Alert alert = new Alert(Alert.AlertType.WARNING);
                                    alert.setContentText("El paquete seleccionado ya está en el carrito.");
                                    alert.show();
                                } else {
                                    // If the room is not in the cart, add it to the cart and show a confirmation message
                                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                    alert.setContentText("Paquete seleccionado: " + p.getId_paquete() + " " + p.getNombre() + " " + p.getDescripcion() + " " + p.getPrecio() + " " + p.getFecha_paquete());
                                    alert.show();
                                    paquetesSelectedShowTableData(p);
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
            cp_action.setCellFactory(cellFactory);

            //agregar los datos a la tabla
            cp_paquetes_tableview.setItems(paquetesListData);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    public void paquetesSelectedShowTableData(PaqueteData paqueteCarrito) {
        try {
            // Obtener los datos actuales de la tabla
            ObservableList<PaqueteData> data = cp_choose_tableview.getItems();

            // Agregar el nuevo paquete
            data.add(paqueteCarrito);

            // Crear los botones en cada celda
            Callback<TableColumn<PaqueteData, String>, TableCell<PaqueteData, String>> cellFactory = (param) -> {
                // Crear la celda que va a devolver el valor de la propiedad del paquete
                final TableCell<PaqueteData, String> cell = new TableCell<PaqueteData, String>() {
                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            // Crear la acción del botón para quitar del carrito
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
                                PaqueteData p = getTableView().getItems().get(getIndex());
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setContentText("Paquete eliminado de mi compra: " + p.getId_paquete() + " " + p.getNombre() + " " + p.getDescripcion() + " " + p.getPrecio() + " " + p.getFecha_paquete());
                                alert.show();
                                // Remove the selected package from the table
                                getTableView().getItems().remove(p);
                            });
                            setGraphic(btn);
                            setText(null);
                        }
                    }
                };
                return cell;
            }; // Fin crear botones en cada celda

            // Add a TextField to the "detalle" column
            choose_detalle.setCellFactory(tc -> new TableCell<>() {
                private final TextField textField = new TextField();

                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        PaqueteData paquete = getTableView().getItems().get(getIndex());
                        textField.setText(paquete.getDetalle());
                        textField.textProperty().addListener((obs, oldValue, newValue) -> {
                            paquete.setDetalle(newValue);
                        });
                        setGraphic(textField);
                    }
                }
            });

            // Add a Spinner to the "cantidad" column
            choose_cantidad.setCellFactory(tc -> new TableCell<>() {
                private final Spinner<Integer> spinner = new Spinner<>(1, 100, 1);

                @Override
                protected void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        PaqueteData paquete = getTableView().getItems().get(getIndex());
                        Integer cantidad = paquete.getCantidad_boletas();
                        if (cantidad == null || cantidad < 1) {
                            cantidad = 1;
                            paquete.setCantidad_boletas(cantidad);
                        }
                        spinner.getValueFactory().setValue(cantidad);
                        spinner.valueProperty().addListener((obs, oldValue, newValue) -> {
                            paquete.setCantidad_boletas(newValue);
                        });
                        setGraphic(spinner);
                    }
                }
            });

            // Agregar en la celda la acción de los botones
            choose_action.setCellFactory(cellFactory);

            // Volver a establecer los datos de la tabla
            cp_choose_tableview.setItems(data);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    void cargarComboCliente() {
        try {
            List<Map<String, Object>> clientes = compraPaqueteRepo.obtenerClientes();

            ObservableList<String> datos = FXCollections.observableArrayList();
            for (Map<String, Object> cliente : clientes) {
                String id = String.valueOf(cliente.get("id"));
                String cedula = String.valueOf(cliente.get("cedula"));
                String nombre = (String) cliente.get("nombre");
                datos.add("ID: " + id + ", Cedula: " + cedula + ", Nombre: " + nombre);
            }
            cp_cliente_cbx.setItems(datos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private void cargarComboMetodoPago() {
        try {
            // Obtener la lista de objetos MetodoPago
            List<Map<String, Object>> metodosPago = compraPaqueteRepo.obtenerMetodosPago();

            // Convertir la lista a un ObservableList
            ObservableList<String> datos = FXCollections.observableArrayList();
            for (Map<String, Object> metodoPago : metodosPago) {
                String id = String.valueOf(metodoPago.get("id_metodo"));
                String nombre = (String) metodoPago.get("nombre");
                datos.add("ID: " + id + ", Nombre: " + nombre);
            }

            // Establecer los datos del ComboBox
            cp_metodopago_cbx.setItems(datos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
