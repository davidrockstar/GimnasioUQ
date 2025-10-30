package gimnasiouq.gimnasiouq.viewcontroller;

import gimnasiouq.gimnasiouq.factory.ModelFactory;
import gimnasiouq.gimnasiouq.model.Entrenador;
import gimnasiouq.gimnasiouq.model.ReservaClase;
import gimnasiouq.gimnasiouq.model.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.LinkedHashSet;
import java.util.Set;

public class AdminGestionEntrenadoresViewController {

    @FXML private TextField fieldNombre;
    @FXML private TextField fieldIdentificacion;
    @FXML private TextField fieldEspecialidad;
    @FXML private TextField fieldCorreo;
    @FXML private TextField fieldCelular;
    @FXML private ComboBox<String> comboBoxClases;
    @FXML private Button btnCrear;
    @FXML private Button btnActualizar;
    @FXML private Button btnEliminar;

    private final ObservableList<String> clasesObservables = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        cargarClasesDisponibles();
    }

    private void cargarClasesDisponibles() {
        clasesObservables.clear();

        ObservableList<ReservaClase> reservas = ModelFactory.getInstance().obtenerReservasDeUsuarios();
        Set<String> clasesUnicasVIP = new LinkedHashSet<>();
        for (ReservaClase r : reservas) {
            Usuario u = ModelFactory.getInstance().buscarUsuario(r.getIdentificacion());
            if (u != null && "VIP".equalsIgnoreCase(u.getMembresia())) {
                clasesUnicasVIP.add(r.getClase());
            }
        }
        clasesObservables.setAll(clasesUnicasVIP);
        comboBoxClases.setItems(clasesObservables);
    }

    @FXML
    void onNuevo(ActionEvent event){}
    @FXML
    void onActualizar(ActionEvent event){}
    @FXML
    void onEliminar(ActionEvent event){}
    @FXML
    void onGuardar(ActionEvent event) {
        String nombre = fieldNombre.getText();
        String identificacion = fieldIdentificacion.getText();
        String especialidad = fieldEspecialidad.getText();
        String correo = fieldCorreo.getText();
        String celular = fieldCelular.getText();
        String claseAsignada = comboBoxClases.getValue();

        if (nombre == null || nombre.isEmpty() ||
                identificacion == null || identificacion.isEmpty() ||
                especialidad == null || especialidad.isEmpty() ||
                correo == null || correo.isEmpty() ||
                celular == null || celular.isEmpty() ||
                claseAsignada == null) {
            mostrarAlerta("Error", "Complete todos los campos", Alert.AlertType.ERROR);
            return;
        }

        Entrenador entrenador = new Entrenador(nombre, identificacion);
        entrenador.setClasesDisponibles(claseAsignada);

        if (ModelFactory.getInstance().agregarEntrenador(entrenador)) {
            mostrarAlerta("Éxito", "Entrenador creado exitosamente", Alert.AlertType.INFORMATION);
            limpiarCampos();
        } else {
            mostrarAlerta("Error", "No se pudo crear el entrenador", Alert.AlertType.ERROR);
        }
    }

    private void mostrarAlerta(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void limpiarCampos() {
        fieldNombre.clear();
        fieldIdentificacion.clear();
        fieldEspecialidad.clear();
        fieldCorreo.clear();
        fieldCelular.clear();
        comboBoxClases.getSelectionModel().clearSelection();
    }
}