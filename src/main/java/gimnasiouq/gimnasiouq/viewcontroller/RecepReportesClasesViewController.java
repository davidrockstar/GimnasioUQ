package gimnasiouq.gimnasiouq.viewcontroller;

import gimnasiouq.gimnasiouq.controller.ReportesClasesController;
import gimnasiouq.gimnasiouq.factory.ModelFactory;
import gimnasiouq.gimnasiouq.model.ReservaClase;
import gimnasiouq.gimnasiouq.model.Usuario;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class RecepReportesClasesViewController {

    @FXML
    private Button btnExportarPdf;

    @FXML
    private Label lblClaseMasReservada;

    @FXML
    private Label lblTotalClasesReservadas;

    @FXML
    private TableView<ReservaClase> tableView;

    @FXML
    private TableColumn<ReservaClase, String> tcClase;

    @FXML
    private TableColumn<ReservaClase, String> tcHorario;

    @FXML
    private TableColumn<ReservaClase, String> tcFecha;

    @FXML
    private TableColumn<ReservaClase, String> tcEntrenador;

    @FXML
    private TableColumn<ReservaClase, String> tcIdUsuario;

    @FXML
    private TableColumn<ReservaClase, String> tcNombreUsuario;


    @FXML
    void onExportarPdf(ActionEvent event) {}

    @FXML
    void initialize() {
        initView();
        cargarIndicadores();
    }

    private void initView() {
        initDataBinding();
        if (tableView != null) {
            ObservableList<ReservaClase> listaReservaClases = ModelFactory.getInstance().obtenerReservasObservable();
            tableView.setItems(listaReservaClases);
        }
    }

    private void initDataBinding() {
        if (tcClase != null) {
            tcClase.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getClase()));
        }
        if (tcHorario != null) {
            tcHorario.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getHorario()));
        }
        if (tcFecha != null) {
            tcFecha.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFecha()));
        }
        if (tcEntrenador != null) {
            tcEntrenador.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEntrenador()));
        }
        if (tcIdUsuario != null) {
            tcIdUsuario.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIdentificacion()));
        }
        if (tcNombreUsuario != null) {
            tcNombreUsuario.setCellValueFactory(cellData -> {
                Usuario u = ModelFactory.getInstance().buscarUsuario(cellData.getValue().getIdentificacion());
                return new SimpleStringProperty(u != null ? u.getNombre() : "Desconocido");
            });
        }
    }
    private final ReportesClasesController reportesClasesController = new ReportesClasesController();

    private void cargarIndicadores() {
        if (lblClaseMasReservada != null)
            lblClaseMasReservada.textProperty().bind(reportesClasesController.claseMasReservadaProperty());
        if (lblTotalClasesReservadas != null)
            lblTotalClasesReservadas.textProperty().bind(reportesClasesController.totalClasesReservadasProperty().asString());
    }

}
