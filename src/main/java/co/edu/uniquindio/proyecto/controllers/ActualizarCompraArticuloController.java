package co.edu.uniquindio.proyecto.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class ActualizarCompraArticuloController {

    @FXML
    private Button btn_volver;

    @FXML
    private ComboBox<?> ca_Articulo_cbx1;

    @FXML
    private TableView<?> ca_Articulos_tableview;

    @FXML
    private TableView<?> ca_choose_tableview;

    @FXML
    private ComboBox<?> ca_cliente_cbx;

    @FXML
    private TextArea ca_descripcion_txtfile;

    @FXML
    private Button ca_guardar_btn;

    @FXML
    private Label ca_impuesto_lbl;

    @FXML
    private Label ca_preciototal_lbl;

    @FXML
    private TableColumn<?, ?> choose_cantidad;

    @FXML
    private TableColumn<?, ?> choose_cantidad1;

    @FXML
    private TableColumn<?, ?> choose_capacidad;

    @FXML
    private TableColumn<?, ?> choose_id;

    @FXML
    private TableColumn<?, ?> choose_nivel;

    @FXML
    private TableColumn<?, ?> choose_precionoche;

    @FXML
    private TableColumn<?, ?> choose_tipo;

    @FXML
    private Label fecha_actual_lbl;

    @FXML
    private TableColumn<?, ?> hab_action;

    @FXML
    private TableColumn<?, ?> hab_action1;

    @FXML
    private TableColumn<?, ?> hab_id;

    @FXML
    private TableColumn<?, ?> hab_nivel;

    @FXML
    private TableColumn<?, ?> hab_precionoche;

    @FXML
    private TableColumn<?, ?> hab_tipo;

    @FXML
    private AnchorPane reserva_Articulo_form;

    @FXML
    private Label username_lbl;

    @FXML
    void comboArticuloesListener(ActionEvent event) {

    }

    @FXML
    void crearBtn(ActionEvent event) {

    }

    @FXML
    void habitacionSelectData(MouseEvent event) {

    }

    @FXML
    void volver(ActionEvent event) {

    }

}
