package gimnasiouq.gimnasiouq.viewcontroller;

import gimnasiouq.gimnasiouq.controller.ReportesMembresiasController;
import gimnasiouq.gimnasiouq.factory.ModelFactory;
import gimnasiouq.gimnasiouq.model.Usuario;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.beans.property.SimpleStringProperty;

import java.net.URL;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class AdminReportesMembresiasViewController implements Initializable{

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

    private final ReportesMembresiasController reportesController = new ReportesMembresiasController();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initView();
        cargarIndicadores();
    }

    private void initView() {
        initDataBinding();
        listaUsuarios = ModelFactory.getInstance().obtenerUsuariosObservable();
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
    }

    private void cargarIndicadores() {
        int totales = reportesController.obtenerMembresiasTotales();
        int conValor = reportesController.obtenerMembresiasConValor();
        int sinValor = reportesController.obtenerMembresiasSinValor();
        double ingresos = reportesController.obtenerIngresosTotales();

        NumberFormat moneda = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("es-CO"));

        lblMembresiasTotales.setText(String.valueOf(totales));
        lblMembresiasConValor.setText(String.valueOf(conValor));
        lblMembresiasSinValor.setText(String.valueOf(sinValor));
        lblIngresosTotales.setText(moneda.format(ingresos));
    }

    @FXML
    void onExportarPdf(ActionEvent event) {

    }

}
