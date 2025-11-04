package gimnasiouq.gimnasiouq.viewcontroller;

import gimnasiouq.gimnasiouq.controller.ReportesMembresiasController;
import gimnasiouq.gimnasiouq.factory.ModelFactory;
import gimnasiouq.gimnasiouq.model.Usuario;
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

public class RecepReportesMembresiasViewController implements Initializable{

    ObservableList<Usuario> listaUsuarios;

    @FXML
    private Button btnExportarPdf;

    @FXML
    private Label lblIngresosTotales;

    @FXML
    private Label lblMembresiasConValor;

    @FXML
    private Label lblMembresiasSinValor;

    @FXML
    private Label lblMembresiasTotales;

    @FXML
    private TableView<Usuario> tableView;

    @FXML
    private TableColumn<Usuario, String> tcFechaInicio;

    @FXML
    private TableColumn<Usuario, String> tcFechaVencimiento;

    @FXML
    private TableColumn<Usuario, String> tcPlanMembresias;

    @FXML
    private TableColumn<Usuario, String> tcTipoMembresias;

    @FXML
    private TableColumn<Usuario, String> tcCosto;

    private final ReportesMembresiasController reportesMembresiasController = new ReportesMembresiasController();

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
        if (tcFechaInicio != null) {
            tcFechaInicio.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFechaInicioFormateada()));
        }
        if (tcFechaVencimiento != null) {
            tcFechaVencimiento.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFechaFinFormateada()));
        }
        if (tcPlanMembresias != null) {
            tcPlanMembresias.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPlanMembresia()));
        }
        if (tcTipoMembresias != null) {
            tcTipoMembresias.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTipoMembresia()));
        }
        if (tcCosto != null) {
            tcCosto.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCostoMembresiaFormateado()));
        }
    }

    private void cargarIndicadores() {
        if (lblMembresiasTotales != null)
            lblMembresiasTotales.textProperty().bind(reportesMembresiasController.membresiasTotalesProperty().asString());
        if (lblMembresiasConValor != null)
            lblMembresiasConValor.textProperty().bind(reportesMembresiasController.membresiasConValorProperty().asString());
        if (lblMembresiasSinValor != null)
            lblMembresiasSinValor.textProperty().bind(reportesMembresiasController.membresiasSinValorProperty().asString());
        if (lblIngresosTotales != null)
            lblIngresosTotales.textProperty().bind(reportesMembresiasController.ingresosTotalesProperty().asString("$%.0f"));
    }

    @FXML
    void onExportarPdf(ActionEvent event) {

    }

}
