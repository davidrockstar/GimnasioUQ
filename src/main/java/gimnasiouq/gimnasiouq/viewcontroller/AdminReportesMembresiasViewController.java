package gimnasiouq.gimnasiouq.viewcontroller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class AdminReportesMembresiasViewController {

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
    private TableView<?> tableView;

    @FXML
    private TableColumn<?, ?> tcFechaInicio;

    @FXML
    private TableColumn<?, ?> tcFechaVencimiento;

    @FXML
    private TableColumn<?, ?> tcPlanMembresias;

    @FXML
    private TableColumn<?, ?> tcTipoMembresias;

    @FXML
    void onExportarPdf(ActionEvent event) {

    }

}
