package gimnasiouq.gimnasiouq.viewcontroller;

import gimnasiouq.gimnasiouq.controller.ReportesUsuariosController;
import gimnasiouq.gimnasiouq.factory.ModelFactory;
import gimnasiouq.gimnasiouq.model.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class RecepReportesUsuariosViewController implements Initializable {

    @FXML
    private Button btnExportarPdf;

    @FXML
    private Label lblUsuariosActivos;

    @FXML
    private Label lblUsuariosMensualidadActiva;

    @FXML
    private Label lblUsuariosSinMensualidadActiva;

    @FXML
    private TableView<Usuario> tableView;

    @FXML
    private TableColumn<Usuario, String> tcUsuario;

    @FXML
    private TableColumn<Usuario, String> tcEstado;

    @FXML
    private TableColumn<Usuario, String> tcIdentificacion;

    @FXML
    private TableColumn<Usuario, String> tcNombre;

    ObservableList<Usuario> listaUsuarios;
    private final ReportesUsuariosController reportesUsuariosController = new ReportesUsuariosController();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initView();
        cargarIndicadores();
    }

    private void initView() {
        initDataBinding();
        listaUsuarios = ModelFactory.getInstance().obtenerUsuariosObservable();
        listaUsuarios.addListener((ListChangeListener.Change<? extends Usuario> change) -> {
            cargarIndicadores();
            tableView.refresh();
        });
        tableView.setItems(listaUsuarios);

    }

    private void initDataBinding() {
        if (tcEstado != null) {
            tcEstado.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEstadoMembresia()));
        }
        if (tcIdentificacion != null) {
            tcIdentificacion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIdentificacion()));
        }
        if (tcNombre != null) {
            tcNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        }
        if (tcUsuario != null) {
            tcUsuario.setCellValueFactory(cellData -> {
                if (cellData.getValue() instanceof Estudiante) {
                    return new SimpleStringProperty("Estudiante");
                } else if (cellData.getValue() instanceof TrabajadorUQ) {
                    return new SimpleStringProperty("Trabajador UQ");
                } else {
                    return new SimpleStringProperty("Externo");
                }
            });
        }
    }

    private void cargarIndicadores() {
        if (lblUsuariosMensualidadActiva != null)
            lblUsuariosMensualidadActiva.textProperty().bind(reportesUsuariosController.usuariosMembresiaActivasProperty().asString());
        if (lblUsuariosSinMensualidadActiva != null)
            lblUsuariosSinMensualidadActiva.textProperty().bind(reportesUsuariosController.usuariosMembresiaInactivasProperty().asString());
        if (lblUsuariosActivos != null)
            lblUsuariosActivos.textProperty().bind(reportesUsuariosController.usuariosTotalesProperty().asString());
    }
}
