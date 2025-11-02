package gimnasiouq.gimnasiouq.viewcontroller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class AdminReportesClasesViewController {

    @FXML
    private Button btnExportarPdf;

    @FXML
    private Label lblClaseMasReservada;

    @FXML
    private Label lblTotalClasesReservadas;

    @FXML
    private TableView<?> tableView;

    @FXML
    private TableColumn<?, ?> tcClase;

    @FXML
    private TableColumn<?, ?> tcCupoMaximo;

    @FXML
    private TableColumn<?, ?> tcEntrenador;

    @FXML
    private TableColumn<?, ?> tcFecha;

    @FXML
    private TableColumn<?, ?> tcTipo;

    @FXML
    void onExportarPdf(ActionEvent event) {

    }

}
