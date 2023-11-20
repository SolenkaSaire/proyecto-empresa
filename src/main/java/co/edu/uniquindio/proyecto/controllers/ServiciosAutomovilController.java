package co.edu.uniquindio.proyecto.controllers;


import co.edu.uniquindio.proyecto.dto.HabitacionHotelData;
import co.edu.uniquindio.proyecto.dto.ReservaHotelData;
import co.edu.uniquindio.proyecto.dto.ServiciosAdicionalesData;
import co.edu.uniquindio.proyecto.model.Empleado;
import co.edu.uniquindio.proyecto.repositories.TipoServicioRepo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

@Controller
public class ServiciosAutomovilController implements Initializable {
    /*Repos y Controllers*/
    @Autowired
    private SceneController sceneController;

    @Autowired
    private CrearReservaAutoController crearController;

    @Autowired
    private ActualizarReservaAutoController actualizarController;

    @Autowired
    private TipoServicioRepo serviciosRepo;

    /*ENTIDADES*/
    private Empleado empleadoLogueado;

    private List<ServiciosAdicionalesData> llegadaServiciosAdicionales;

    private String tipoVentana;


    /*FXML COMPONENTES*/


    @FXML
    private TableColumn<ServiciosAdicionalesData, String> choose_action;

    @FXML
    private TableColumn<ServiciosAdicionalesData, String> choose_desc;

    @FXML
    private TableColumn<ServiciosAdicionalesData, String> choose_id;

    @FXML
    private TableColumn<ServiciosAdicionalesData, String> choose_info;

    @FXML
    private TableColumn<ServiciosAdicionalesData, String> choose_nombre;

    @FXML
    private TableColumn<ServiciosAdicionalesData, Double> choose_precio;

    @FXML
    private Button gr_guardarservicios_btn;

    @FXML
    private TableView<ServiciosAdicionalesData> gs_choose_tableview;

    @FXML
    private TableView<ServiciosAdicionalesData> gs_servicios_tableview;

    @FXML
    private AnchorPane reserva_hotel_form;

    @FXML
    private TableColumn<ServiciosAdicionalesData, String> servicio_action;

    @FXML
    private TableColumn<ServiciosAdicionalesData, String> servicio_id;

    @FXML
    private TableColumn<ServiciosAdicionalesData, String> servicio_info;

    @FXML
    private TableColumn<ServiciosAdicionalesData, String> servicio_nombre;

    @FXML
    private TableColumn<ServiciosAdicionalesData, Double> servicio_precio;

    @FXML
    void crearBtn(ActionEvent event) {
        //verificar si el carrito esta vacio
        if(gs_choose_tableview.getItems().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Aviso");
            alert.setHeaderText("No hay servicios adicionales en el carrito");
            alert.setContentText("Se seleccionaron 0 servicios adicionales para la reserva de automovil");
            alert.show();

        }else if(!gs_choose_tableview.getItems().isEmpty()){
            //verificar que la descripcion de cada servicio no este vacia
            ObservableList<ServiciosAdicionalesData> serviciosAdicionalesDataList = gs_choose_tableview.getItems();
            for (ServiciosAdicionalesData servicio : serviciosAdicionalesDataList) {
                if(servicio.getDescripcion()==null || servicio.getDescripcion().isEmpty()){
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Aviso");
                    alert.setHeaderText("Descripcion de servicio vacia");
                    alert.setContentText("Por favor ingrese una descripcion para cada servicio adicional");
                    alert.show();
                    return;
                }
            }
            //verificar si el carrito no esta vacio
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Aviso");
            alert.setHeaderText("Servicios agregados exitosamente a la reserva");
            alert.setContentText("Sus servicios seleccionados fueron agregados a la reserva de automoviles "+ gs_choose_tableview.getItems().size() +" servicios adicionales para la reserva de automovil");
            alert.show();
        }

        //recuperar la lista de servicios adicionales de la tabla gs_choose_tablview
        ObservableList<ServiciosAdicionalesData> serviciosAdicionalesDataList = gs_choose_tableview.getItems();

        if(tipoVentana.equals("Crear")){
            //enviar la lista de servicios adicionales a la ventana de crear reserva de automovil
            devolverACrearReservaAutomovil( serviciosAdicionalesDataList);
        }else if(tipoVentana.equals("Actualizar")){

            devolverAActualizarReservaAutomovil(serviciosAdicionalesDataList);
        }
        //cerrar la ventana de servicios adicionales
        gr_guardarservicios_btn.getScene().getWindow().hide();
    }

    private void devolverAActualizarReservaAutomovil(ObservableList<ServiciosAdicionalesData> serviciosAdicionalesDataList) {
        actualizarController.devolverAReservaAutoActualizar(serviciosAdicionalesDataList);
    }

    private void devolverACrearReservaAutomovil( ObservableList<ServiciosAdicionalesData> serviciosAdicionalesDataList) {
        crearController.devolverAReservaAutoCreacion(serviciosAdicionalesDataList);
    }


    public void serviciosShowTableData(){


        try {
            List<Map<String, Object>> serviciosData = serviciosRepo.obtenerHabitacionesData();

            ObservableList<ServiciosAdicionalesData> serviciosListData = FXCollections.observableArrayList();
            for (Map<String, Object> servicioData : serviciosData) {
                ServiciosAdicionalesData servicio = new ServiciosAdicionalesData(
                        String.valueOf(servicioData.get("id")),
                        String.valueOf(servicioData.get("nombre")),
                        String.valueOf(servicioData.get("informacion")),
                        Double.valueOf(String.valueOf(servicioData.get("precio"))));
                serviciosListData.add(servicio);
            }
            servicio_id.setCellValueFactory(new PropertyValueFactory<>("id"));
            servicio_nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
            servicio_info.setCellValueFactory(new PropertyValueFactory<>("informacion"));
            servicio_precio.setCellValueFactory(new PropertyValueFactory<>("precio"));

            //crear los botones en cada celda
            Callback<TableColumn<ServiciosAdicionalesData, String>, TableCell<ServiciosAdicionalesData, String>> cellFactory = (param) -> {
                //crear la celda que va a devolver el valor de la propiedad de la persona
                final TableCell<ServiciosAdicionalesData, String> cell = new TableCell<ServiciosAdicionalesData, String>(){
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
                                ServiciosAdicionalesData h = getTableView().getItems().get(getIndex());

                                //get the current data in the cart
                                ObservableList<ServiciosAdicionalesData> data = gs_choose_tableview.getItems();

                                //check if selected service is already in the cart
                                if(data.contains(h)){
                                    Alert alert = new Alert(Alert.AlertType.WARNING);
                                    alert.setContentText("Servicio ya agregado a la reserva");
                                    alert.show();
                                    return;
                                }else{
                                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                    alert.setContentText("Servicio seleccionado: "+ h.getId() + " " + h.getNombre() + " " + h.getInformacion() + " " + h.getPrecio());
                                    alert.show();
                                    //enviar la habitacion seleccionada al carrito de compras
                                    serviciosSelectedShowTableData(h);
                                }


                            });

                            setGraphic(btn);
                            setText(null);
                        }
                    }
                    ;
                };

                return cell;
            };
            servicio_action.setCellFactory(cellFactory);

            gs_servicios_tableview.setItems(serviciosListData);



        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void serviciosSelectedShowTableData(ServiciosAdicionalesData servicioCarrito){

        try{
            // Obtener los datos actuales de la tabla
            ObservableList<ServiciosAdicionalesData> data = gs_choose_tableview.getItems();

            // Agregar la nueva habitación
            data.add(servicioCarrito);

            //crear los botones en cada celda
            Callback<TableColumn<ServiciosAdicionalesData, String>, TableCell<ServiciosAdicionalesData, String>> cellFactory = (param) -> {
                //crear la celda que va a devolver el valor de la propiedad de la persona
                final TableCell<ServiciosAdicionalesData, String> cell = new TableCell<ServiciosAdicionalesData, String>(){
                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if(empty){
                            setGraphic(null);
                            setText(null);
                        }else{
                            //crar la accion boton para agregar al carrito
                            final Button btn = new Button("Quitar");
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
                                ServiciosAdicionalesData h = getTableView().getItems().get(getIndex());
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setContentText("Servicio eliminado de mi reserva: "+ h.getId() + " " + h.getNombre() + " " + h.getInformacion() + " " + h.getPrecio());
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

            // Add a TextField to the "descripcion" column
            choose_desc.setCellFactory(tc -> new TableCell<>() {
                private final TextField textField = new TextField();

                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty );
                    if (empty) {
                        setGraphic(null);
                    } else {
                        ServiciosAdicionalesData servicio = getTableView().getItems().get(getIndex());
                        textField.setText(servicio.getDescripcion());
                        textField.textProperty().addListener((obs, oldValue, newValue) -> {
                            servicio.setDescripcion(newValue);
                        });
                        setGraphic(textField);
                    }
                }
            });


            //agregar en la celda la accion de los botones
            choose_action.setCellFactory(cellFactory);
            // Volver a establecer los datos de la tabla
            gs_choose_tableview.setItems(data);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        //System.out.println("ReservaHotelRepo: " + reservaHotelRepo);

        choose_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        choose_nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        choose_info.setCellValueFactory(new PropertyValueFactory<>("informacion"));
        choose_precio.setCellValueFactory(new PropertyValueFactory<>("precio"));

        try {
            serviciosShowTableData();

           // cargarComboPaisHotel();
            // cargarComboClienteHotel();
            // habitacionesShowTableData();
            // cargarComboHoteles();
            // cargarDatePicker();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void displayEmployeeIDUsername(Empleado empleado, List<ServiciosAdicionalesData>serviciosAdicionalesDataList, String tipoVentana ) {
        this.tipoVentana= tipoVentana;

        llegadaServiciosAdicionales = serviciosAdicionalesDataList;

        //cargar los datos que ya estaban seleccionados
        if(llegadaServiciosAdicionales!= null){
            // Obtener los datos actuales de la tabla
            ObservableList<ServiciosAdicionalesData> data = gs_choose_tableview.getItems();
            for (ServiciosAdicionalesData servicio : llegadaServiciosAdicionales) {
                System.out.println("llegadaServiciosAdicionales: " + servicio.getId() + " " + servicio.getNombre() + " " + servicio.getInformacion() + " " + servicio.getPrecio());

                serviciosSelectedShowTableData(servicio);
                //data.add(servicio);
            }
            //  gs_choose_tableview.setItems(data);
        }

        empleadoLogueado = empleado;
    }
}