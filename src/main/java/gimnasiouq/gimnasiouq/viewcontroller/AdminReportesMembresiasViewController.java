package gimnasiouq.gimnasiouq.viewcontroller;

import gimnasiouq.gimnasiouq.controller.ReportesMembresiasController;
import gimnasiouq.gimnasiouq.factory.ModelFactory;
import gimnasiouq.gimnasiouq.model.Usuario;
import javafx.collections.ListChangeListener;
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
        // Listener para actualizar indicadores en tiempo real cuando la lista observable cambie
        listaUsuarios.addListener((ListChangeListener.Change<? extends Usuario> change) -> {
            cargarIndicadores();
        });
        tableView.setItems(listaUsuarios);

        // Bind de labels a las properties del modelo para actualización automática
        ModelFactory mf = ModelFactory.getInstance();
        if (lblMembresiasTotales != null)
            lblMembresiasTotales.textProperty().bind(mf.membresiasTotalesProperty().asString());
        if (lblMembresiasConValor != null)
            lblMembresiasConValor.textProperty().bind(mf.membresiasConValorProperty().asString());
        if (lblMembresiasSinValor != null)
            lblMembresiasSinValor.textProperty().bind(mf.membresiasSinValorProperty().asString());
        if (lblIngresosTotales != null)
            lblIngresosTotales.textProperty().bind(mf.ingresosTotalesProperty().asString("$%.0f"));
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
        // Dejar la carga manual para compatibilidad; el binding ya actualiza Labels automáticamente.
        int totales = reportesController.obtenerMembresiasTotales();
        int conValor = reportesController.obtenerMembresiasConValor();
        int sinValor = reportesController.obtenerMembresiasSinValor();
        double ingresos = reportesController.obtenerIngresosTotales();

        // Debug: imprimir valores obtenidos para comprobar en tiempo de ejecución
        System.out.println("[AdminReportesMembresias] indicadores -> totales: " + totales + ", conValor: " + conValor + ", sinValor: " + sinValor + ", ingresos: " + ingresos);

        // Las Labels están bindadas a las properties del ModelFactory y se actualizan automáticamente.
        // No llamar lbl.setText(...) aquí para evitar excepciones si la propiedad está bindada.
    }

    @FXML
    void onExportarPdf(ActionEvent event) {

    }

}
