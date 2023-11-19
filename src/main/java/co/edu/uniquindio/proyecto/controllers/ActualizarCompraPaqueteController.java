package co.edu.uniquindio.proyecto.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class ActualizarCompraPaqueteController {

    @FXML
    private Button btn_volver;

    @FXML
    private TableColumn<?, ?> choose_action;

    @FXML
    private TableColumn<?, ?> choose_cantidad;

    @FXML
    private TableColumn<?, ?> choose_compra;

    @FXML
    private TableColumn<?, ?> choose_desc;

    @FXML
    private TableColumn<?, ?> choose_descripcion;

    @FXML
    private TableColumn<?, ?> choose_estado;

    @FXML
    private TableColumn<?, ?> choose_id;

    @FXML
    private TableColumn<?, ?> choose_precio;

    @FXML
    private TableColumn<?, ?> compra;

    @FXML
    private AnchorPane compra_paquete_form;

    @FXML
    private TableColumn<?, ?> cp_action;

    @FXML
    private Button cp_actualizar_btn;

    @FXML
    private TableView<?> cp_choose_tableview;

    @FXML
    private ComboBox<?> cp_cliente_cbx;

    @FXML
    private TextField cp_descripcion_lbl;

    @FXML
    private DatePicker cp_fecha_date;

    @FXML
    private TableColumn<?, ?> cp_id;

    @FXML
    private ComboBox<?> cp_metodopago_cbx;

    @FXML
    private TableView<?> cp_paquetes_tableview;

    @FXML
    private Label cp_preciototal_lbl;

    @FXML
    private TableColumn<?, ?> descripcion;

    @FXML
    private Label fecha_actual_lbl;

    @FXML
    private TableColumn<?, ?> precio;

    @FXML
    private Label username_lbl;

    @FXML
    void comboHotelesListener(ActionEvent event) {

    }

    @FXML
    void actualizarBtn(ActionEvent event) {

    }

    @FXML
    void dateCheckinListener(ActionEvent event) {

    }

    @FXML
    void habitacionSelectData(MouseEvent event) {

    }

    @FXML
    void volver(ActionEvent event) {

    }

}
