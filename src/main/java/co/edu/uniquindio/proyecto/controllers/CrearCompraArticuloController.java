package co.edu.uniquindio.proyecto.controllers;

import co.edu.uniquindio.proyecto.dto.*;
import co.edu.uniquindio.proyecto.model.*;
import co.edu.uniquindio.proyecto.repositories.*;
import jakarta.persistence.criteria.CriteriaBuilder;
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
public class CrearCompraArticuloController implements Initializable {


    /*Repos y Controllers*/

    @Autowired
    CompraArticuloRepo compraArticuloRepo;

    @Autowired
    private ClienteRepo clienteRepo;
    @Autowired
    private SceneController sceneController;

    @Autowired
    private DetalleCompraArticuloRepo detalleCompraArticuloRepo;

    @Autowired
    private ArticuloTuristicoRepo articuloRepo;
    /*ENTIDADES*/
    private Empleado empleadoLogueado;
    private Cliente clienteSeleccionado;

    private MetodoPago metodoSeleccionado;

    private ArticuloTuristico articuloSeleccionado;
    /*MAPAS */
    private Map<String, Integer> mapaPaises = new HashMap<>();
    private HashMap<String, String[]> mapaClientes;
    private Alert alert;

    /*FXML COMPONENTES*/

    @FXML
    private Button btn_volver;
    @FXML
    private TableColumn<ArticuloData, String> ca_action;

    @FXML
    private TableView<ArticuloData> ca_articulos_tableview;

    @FXML
    private TableColumn<ArticuloData, String> ca_categoria;

    @FXML
    private TableView<ArticuloData> ca_choose_tableview;

    @FXML
    private ComboBox<String> ca_cliente_cbx;

    @FXML
    private TextArea ca_descripcion_lbl;

    @FXML
    private Button ca_guardar_btn;

    @FXML
    private TableColumn<ArticuloData, String> ca_id;

    @FXML
    private Label ca_impuesto_lbl;

    @FXML
    private TableColumn<ArticuloData, String> ca_info;

    @FXML
    private ComboBox<String> ca_metodo_cbx;

    @FXML
    private TableColumn<ArticuloData, String> ca_nombre;

    @FXML
    private TableColumn<ArticuloData, Double> ca_precio;

    @FXML
    private Label ca_preciototal_lbl;

    @FXML
    private TableColumn<ArticuloData, String> choose_action;
    @FXML
    private TableColumn<ArticuloData, Integer> choose_cantidad;

    @FXML
    private TableColumn<ArticuloData, String> choose_categoria;

    @FXML
    private TableColumn<ArticuloData, String> choose_id;

    @FXML
    private TableColumn<ArticuloData, String> choose_info;

    @FXML
    private TableColumn<ArticuloData, String> choose_nombre;

    @FXML
    private TableColumn<ArticuloData, Double> choose_precio;
    @FXML
    private Label fecha_actual_lbl;

    @FXML
    private AnchorPane reserva_Articulo_form;

    @FXML
    private Label username_lbl;

    @FXML
    void crearBtn(ActionEvent event) {

        verificarCompraArticulo();
    }

    private void verificarCompraArticulo() {
        List<ArticuloData> listaArticulos=ca_choose_tableview.getItems();
        for (ArticuloData articuloData : listaArticulos) {
            System.out.println("articulo elegido: " + articuloData.toString());
        }
        //verificar que el text field de descripcion no este vacio en choose_desc
        String mensaje = verificarInformacionReserva(ca_cliente_cbx.getValue(), ca_metodo_cbx.getValue(),listaArticulos);

        if (!mensaje.equalsIgnoreCase("")) {
            mostrarMensaje("Gestión de Compra Articulo", "Información", mensaje,
                    Alert.AlertType.WARNING);
            return;
        } else {
            crearCompraArticulo(listaArticulos);

        }


    }

    private void crearCompraArticulo(List<ArticuloData> listaArticulos) {
        try{
            Compra compra = creacionCompraArticulo();
            List<DetalleCompra> listaDetalleCompra = creacionDetalleCompraArticulo(compra, listaArticulos);
            mostrarMensajeCompraCreada(compra, listaDetalleCompra);
        }catch  (TransactionSystemException e) {
            manejarExcepcion(e);
        }
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

    private List<DetalleCompra> creacionDetalleCompraArticulo(Compra compra, List<ArticuloData> listaArticulos) {
        List<DetalleCompra> listaDetalleCompra = new ArrayList<>();
        for (ArticuloData articuloData : listaArticulos) {
            articuloSeleccionado = articuloRepo.obtener(Integer.parseInt(articuloData.getId_articulo()));
            DetalleCompra detalleCompra = new DetalleCompra(
                    articuloSeleccionado,
                    compra,
                    articuloSeleccionado.getPrecio(),
                    articuloData.getCantidad_articulos()

            );
            listaDetalleCompra.add(detalleCompra);
            detalleCompraArticuloRepo.save(detalleCompra);
        }
        return listaDetalleCompra;
    }

    private Compra creacionCompraArticulo() {
        String idCliente = obtenerIdCliente();
        String idMetodoPago = obtenerIdMetodoPago();
        clienteSeleccionado = clienteRepo.obtener(Integer.parseInt(idCliente));

       // System.out.println("metodo seleccionado " + ca_metodo_cbx.getValue());
       // metodoSeleccionado = compraArticuloRepo.findMetodoByNombre(ca_metodo_cbx.getValue());
       // System.out.println("metodo retornado " + metodoSeleccionado.toString());
                //compraArticuloRepo.findMetodoById(Integer.parseInt(idMetodoPago));

        Compra compra = new Compra(Integer.parseInt(idMetodoPago), Integer.parseInt(idCliente), empleadoLogueado.getCodigoEmpleado(),
                new Date(System.currentTimeMillis()),
                ca_descripcion_lbl.getText(), "Compra de Articulos Turisticos"
        );
        compraArticuloRepo.save(compra);
        return compra;
    }

    private String obtenerIdMetodoPago() {
        String[] partesMetodo = ca_metodo_cbx.getValue().split(","); //gr_cliente_cbx.getValue().split(",");
        String idMetodoPago = partesMetodo[0].replace("ID: ", "").trim();
        return idMetodoPago;

/*
        String nombreMetodo = ca_metodo_cbx.getValue();

        String idMetodoPago = compraArticuloRepo.findIdMetodoByNombre(nombreMetodo);
        System.out.println("id metodo pago " + idMetodoPago);
        return idMetodoPago;*/
    }


    private String obtenerIdCliente() {
        String[] partesCliente = ca_cliente_cbx.getValue().split(","); //gr_cliente_cbx.getValue().split(",");
        String cedulaCliente = partesCliente[1].replace("Cedula: ", "").trim();
        return clienteRepo.buscarByCedula(cedulaCliente);
    }

    private void mostrarMensajeCompraCreada(Compra compra, List<DetalleCompra> listaDetalleCompra) {
        StringBuilder mensaje = new StringBuilder();
        mensaje.append("Compra creada exitosamente.\n");
        mensaje.append("Creación Exitosa: \n");
        mensaje.append(compra.toString() + "\n");
        mensaje.append("Detalle Compra: \n");
        for (DetalleCompra detalleCompra : listaDetalleCompra) {
            mensaje.append(detalleCompra.toString() + "\n");
        }
        mostrarMensaje("Gestión de Compra Articulo", "Información", mensaje.toString(),
                Alert.AlertType.INFORMATION);
    }

    private String verificarInformacionReserva(String cliente, String metodoPago, List<ArticuloData> listaArticulos) {
        String mensaje = "";
        if (cliente == null) {
            mensaje += "Debe seleccionar un cliente.\n";
        }
        if (metodoPago == null) {
            mensaje += "Debe seleccionar un método de pago.\n";
        }
        if (listaArticulos.isEmpty()) {
            mensaje += "Debe seleccionar al menos un articulo.\n";
        }
        return mensaje;
    }



    private void mostrarMensaje(String gestiónDeReservasHotel, String información, String mensaje, Alert.AlertType warning) {
        alert = new Alert(warning);
        alert.setTitle(gestiónDeReservasHotel);
        alert.setHeaderText(información);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    @FXML
    void habitacionSelectData(MouseEvent event) {

    }

    @FXML
    void volver(ActionEvent event) {
        abrirVentanaVolver(event, empleadoLogueado);
        btn_volver.getScene().getWindow().hide();
    }



    private void abrirVentanaVolver(ActionEvent event, Empleado empleado) {
        sceneController.cambiarAVentanaCompraArticulo(event, empleado);
    }

    void cargarComboCliente() {
        try {
            List<Map<String, Object>> clientes = compraArticuloRepo.obtenerClientes();

            ObservableList<String> datos = FXCollections.observableArrayList();
            for (Map<String, Object> cliente : clientes) {
                String id = String.valueOf(cliente.get("id"));
                String cedula = String.valueOf(cliente.get("cedula"));
                String nombre = (String) cliente.get("nombre");
                datos.add("ID: " + id + ", Cedula: " + cedula + ", Nombre: " + nombre);
            }
            ca_cliente_cbx.setItems(datos);
        } catch (Exception e) {
            e.printStackTrace();
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

    private void cargarComboMetodoPago() {
        try {
            // Obtener la lista de objetos MetodoPago
            List<Map<String, Object>> metodosPago = compraArticuloRepo.obtenerMetodosPago();

            // Convertir la lista a un ObservableList
            ObservableList<String> datos = FXCollections.observableArrayList();
            for (Map<String, Object> metodoPago : metodosPago) {
                String id = String.valueOf(metodoPago.get("id_metodo"));
                String nombre = (String) metodoPago.get("nombre");
                datos.add("ID: " + id + ", Nombre: " + nombre);
            }

            // Establecer los datos del ComboBox
            ca_metodo_cbx.setItems(datos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        runTime();

        choose_id.setCellValueFactory(new PropertyValueFactory<>("id_articulo"));
        choose_categoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        choose_nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        choose_info.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        choose_precio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        try{
            cargarComboMetodoPago();
            cargarComboCliente();
            
            articuloShowTableData();

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void articuloShowTableData() {

        try {
            List<Map<String, Object>> paquetesData = compraArticuloRepo.obtenerArticulodData();
                    //compraPaqueteRepo.obtenerPaquetesData();

            ObservableList<ArticuloData> articuloListData = FXCollections.observableArrayList();
            for (Map<String, Object> articuloData : paquetesData) {
                ArticuloData articulo = new ArticuloData(
                        String.valueOf(articuloData.get("id_articulo")),
                        String.valueOf(articuloData.get("categoria")),
                        String.valueOf(articuloData.get("nombre")),
                        String.valueOf(articuloData.get("descripcion")),
                        Double.valueOf(String.valueOf(articuloData.get("precio")))
                );
                articuloListData.add(articulo);
            }

            ca_id.setCellValueFactory(new PropertyValueFactory<>("id_articulo"));
            ca_categoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
            ca_nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
            ca_info.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
            ca_precio.setCellValueFactory(new PropertyValueFactory<>("precio"));

            //crear los botones en cada celda
            Callback<TableColumn<ArticuloData, String>, TableCell<ArticuloData, String>> cellFactory = (param -> {
                //crear la celda que va a devolver el valor de la propiedad de la persona
                final TableCell<ArticuloData, String> cell = new TableCell<ArticuloData, String>() {
                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty){
                            setGraphic(null);
                            setText(null);

                        }else{
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
                                ArticuloData a = getTableView().getItems().get(getIndex());

                                // Get the current data in the cart
                                ObservableList<ArticuloData> cartData = ca_choose_tableview.getItems();

                                // Check if the selected room is already in the cart
                                if (cartData.contains(a)) {
                                    // If the room is already in the cart, show a warning message
                                    Alert alert = new Alert(Alert.AlertType.WARNING);
                                    alert.setContentText("El articulo seleccionado ya está en el carrito.");
                                    alert.show();
                                } else {
                                    // If the room is not in the cart, add it to the cart and show a confirmation message
                                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                    alert.setContentText("Articulo seleccionado: " + a.getId_articulo() + " " + a.getNombre() + " " + a.getDescripcion() + " " + a.getPrecio());
                                    alert.show();
                                    articulosSelectedShowTableData(a);
                                }

                            });

                            setGraphic(btn);
                            setText(null);
                        }
                    };

                };
                return cell;


            });//fin crear

            //agregar en la celda la accion de los botones
            ca_action.setCellFactory(cellFactory);

            //agregar los datos a la tabla
            ca_articulos_tableview.setItems(articuloListData);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void articulosSelectedShowTableData(ArticuloData articuloCarrito) {
        try {
            // Obtener los datos actuales de la tabla
            ObservableList<ArticuloData> data = ca_choose_tableview.getItems();

            // Agregar el nuevo artículo
            data.add(articuloCarrito);

            // Crear los botones en cada celda
            Callback<TableColumn<ArticuloData, String>, TableCell<ArticuloData, String>> cellFactory = (param) -> {
                // Crear la celda que va a devolver el valor de la propiedad del artículo
                final TableCell<ArticuloData, String> cell = new TableCell<ArticuloData, String>() {
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
                                ArticuloData a = getTableView().getItems().get(getIndex());
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setContentText("Articulo eliminado de mi compra: " + a.getId_articulo() + " " + a.getNombre() + " " + a.getDescripcion() + " " + a.getPrecio());
                                alert.show();
                                // Remove the selected article from the table
                                getTableView().getItems().remove(a);
                            });
                            setGraphic(btn);
                            setText(null);
                        }
                    }
                };
                return cell;
            }; // Fin crear botones en cada celda


            // Add a Spinner to the "cantidad" column
            choose_cantidad.setCellFactory(tc -> new TableCell<>() {
                private final Spinner<Integer> spinner = new Spinner<>(1, 100, 1);

                @Override
                protected void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        ArticuloData articulo = getTableView().getItems().get(getIndex());
                        Integer cantidad = articulo.getCantidad_articulos();
                        if (cantidad == null || cantidad < 1) {
                            cantidad = 1;
                            articulo.setCantidad_articulos(cantidad);
                        }
                        spinner.getValueFactory().setValue(cantidad);
                        spinner.valueProperty().addListener((obs, oldValue, newValue) -> {
                            articulo.setCantidad_articulos(newValue);
                        });
                        setGraphic(spinner);
                    }
                }
            });
            // Agregar en la celda la acción de los botones
            choose_action.setCellFactory(cellFactory);


            // Volver a establecer los datos de la tabla
            ca_choose_tableview.setItems(data);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void displayEmployeeIDUsername(Empleado empleado) {
        empleadoLogueado = empleado;
        username_lbl.setText(empleado.getPersona().getNombre());

    }
}
