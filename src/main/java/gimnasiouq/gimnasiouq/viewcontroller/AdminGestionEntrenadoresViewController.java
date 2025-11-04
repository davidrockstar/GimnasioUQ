package gimnasiouq.gimnasiouq.viewcontroller;

import gimnasiouq.gimnasiouq.controller.EntrenadorController;
import gimnasiouq.gimnasiouq.factory.ModelFactory;
import gimnasiouq.gimnasiouq.model.Entrenador;
import gimnasiouq.gimnasiouq.model.ReservaClase;
import gimnasiouq.gimnasiouq.model.Usuario;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

public class AdminGestionEntrenadoresViewController {

    ObservableList<Entrenador> listaEntrenadores;
    Entrenador entrenadorSeleccionado;
    private EntrenadorController entrenadorController;

    @FXML
    private Button btnActualizar;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnGuardar;

    @FXML
    private Button btnNuevo;

    @FXML
    private TextField fieldCelular;

    @FXML
    private TextField fieldCorreo;

    @FXML
    private TextField fieldEspecialidad;

    @FXML
    private TextField fieldIdentificacion;

    @FXML
    private TextField fieldNombre;

    @FXML
    private TableView<Entrenador> tableEntrenador;

    @FXML
    private TableColumn<Entrenador, String> tcCelular;

    @FXML
    private TableColumn<Entrenador, String> tcCorreo;

    @FXML
    private TableColumn<Entrenador, String> tcEspecialidad;

    @FXML
    private TableColumn<Entrenador, String> tcIdentificacion;

    @FXML
    private TableColumn<Entrenador, String> tcNombre;

    @FXML
    void onActualizar(ActionEvent event) {
        actualizarEntrenador();
    }

    @FXML
    void onEliminar(ActionEvent event) {
        eliminarEntrenador();
    }

    @FXML
    void onGuardar(ActionEvent event) {
        guardarEntrenador();
    }

    @FXML
    void onNuevo(ActionEvent event) {
        nuevoEntrenador();
    }

    private void nuevoEntrenador() {
        limpiarCampos();
        tableEntrenador.getSelectionModel().clearSelection();
        entrenadorSeleccionado = null;
    }

    private void guardarEntrenador() {
        String nombre = fieldNombre.getText();
        String identificacion = fieldIdentificacion.getText();
        String especialidad = fieldEspecialidad.getText();
        String correo = fieldCorreo.getText();
        String celular = fieldCelular.getText();

        if (nombre == null || nombre.isEmpty() ||
                identificacion == null || identificacion.isEmpty() ||
                especialidad == null || especialidad.isEmpty() ||
                correo == null || correo.isEmpty() ||
                celular == null || celular.isEmpty()) {
            mostrarAlerta("Error", "Complete todos los campos", Alert.AlertType.ERROR);
            return;
        }

        Entrenador entrenador = new Entrenador(nombre, identificacion);
        entrenador.setEspecialidad(especialidad);
        entrenador.setCorreo(correo);
        entrenador.setCelular(celular);

        if (entrenadorController.agregarEntrenador(entrenador)) {
            mostrarAlerta("Éxito", "Entrenador creado exitosamente", Alert.AlertType.INFORMATION);
            limpiarCampos();
            tableEntrenador.refresh();
        } else {
            mostrarAlerta("Error", "No se pudo crear el entrenador. Verifique que no exista un entrenador con la misma identificación.", Alert.AlertType.ERROR);
        }
    }

    private void actualizarEntrenador() {
        if (entrenadorSeleccionado == null) {
            mostrarAlerta("Error", "Debe seleccionar un entrenador de la tabla", Alert.AlertType.ERROR);
            return;
        }

        String nombre = fieldNombre.getText();
        String identificacion = fieldIdentificacion.getText();
        String especialidad = fieldEspecialidad.getText();
        String correo = fieldCorreo.getText();
        String celular = fieldCelular.getText();

        if (nombre == null || nombre.isEmpty() ||
                identificacion == null || identificacion.isEmpty() ||
                especialidad == null || especialidad.isEmpty() ||
                correo == null || correo.isEmpty() ||
                celular == null || celular.isEmpty()) {
            mostrarAlerta("Error", "Complete todos los campos", Alert.AlertType.ERROR);
            return;
        }

        Entrenador entrenadorActualizado = new Entrenador(nombre, identificacion);
        entrenadorActualizado.setEspecialidad(especialidad);
        entrenadorActualizado.setCorreo(correo);
        entrenadorActualizado.setCelular(celular);

        if (entrenadorController.actualizarEntrenador(entrenadorSeleccionado.getIdentificacion(), entrenadorActualizado)) {
            mostrarAlerta("Éxito", "Entrenador actualizado exitosamente", Alert.AlertType.INFORMATION);
            limpiarCampos();
            tableEntrenador.refresh();
        } else {
            mostrarAlerta("Error", "No se pudo actualizar el entrenador", Alert.AlertType.ERROR);
        }
    }

    private void eliminarEntrenador() {
        if (entrenadorSeleccionado == null) {
            mostrarAlerta("Error", "Debe seleccionar un entrenador de la tabla", Alert.AlertType.ERROR);
            return;
        }

        if (entrenadorController.eliminarEntrenador(entrenadorSeleccionado.getIdentificacion())) {
            mostrarAlerta("Éxito", "Entrenador eliminado exitosamente", Alert.AlertType.INFORMATION);
            limpiarCampos();
            tableEntrenador.refresh();
        } else {
            mostrarAlerta("Error", "No se pudo eliminar el entrenador", Alert.AlertType.ERROR);
        }
    }


    private final ObservableList<String> clasesObservables = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        entrenadorController = new EntrenadorController();
        initView();
    }

    private void initView() {
        initDataBinding();
        listaEntrenadores = ModelFactory.getInstance().obtenerEntrenadorObservable();
        tableEntrenador.setItems(listaEntrenadores);
        listenerSelection();
    }

    private void initDataBinding() {
        if (tcCelular != null) {
            tcCelular.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCelular()));
        }
        if (tcCorreo != null) {
            tcCorreo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCorreo()));
        }
        if (tcIdentificacion != null) {
            tcIdentificacion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIdentificacion()));
        }
        if (tcEspecialidad != null) {
            tcEspecialidad.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEspecialidad()));
        }
        if (tcNombre != null) {
            tcNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        }
    }

    private void listenerSelection() {
        tableEntrenador.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newSelection) -> {
                    entrenadorSeleccionado = newSelection;
                    mostrarInformacionEntrenador(entrenadorSeleccionado);
                });
    }

    private void mostrarInformacionEntrenador(Entrenador entrenador) {
        if (entrenador != null) {
            fieldNombre.setText(entrenador.getNombre());
            fieldIdentificacion.setText(entrenador.getIdentificacion());
            fieldEspecialidad.setText(entrenador.getEspecialidad());
            fieldCorreo.setText(entrenador.getCorreo());
            fieldCelular.setText(entrenador.getCelular());
        } else {
            limpiarCampos();
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
    }
}