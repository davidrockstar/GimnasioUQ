package gimnasiouq.gimnasiouq.viewcontroller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import gimnasiouq.gimnasiouq.factory.ModelFactory;
import gimnasiouq.gimnasiouq.model.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import gimnasiouq.gimnasiouq.mapping.dto.ReservaClaseDto;
import gimnasiouq.gimnasiouq.controller.ReservaClaseController;

public class AdminReservaClasesViewController {

    @FXML private Button btnConfirmar;
    @FXML private Button btnActualizar;
    @FXML private Button btnEliminar;
    @FXML private Button btnNuevo;

    @FXML private TextField txtFecha;
    @FXML private ComboBox<String> comboBoxClase;
    @FXML private ComboBox<String> comboBoxEntrenador;
    @FXML private ComboBox<String> comboBoxHorario;
    @FXML private Label lblBeneficios;

    @FXML private TableView<Usuario> tableUsuario;
    @FXML private TableColumn<Usuario, String> tcNombre;
    @FXML private TableColumn<Usuario, String> tcIdentificacion;
    @FXML private TableColumn<Usuario, String> tcTipo;
    @FXML private TableColumn<Usuario, String> tcClase;
    @FXML private TableColumn<Usuario, String> tcHorarior;
    @FXML private TableColumn<Usuario, String> tcFecha;
    @FXML private TableColumn<Usuario, String> tcEntrenador;
    @FXML private TableColumn<Usuario, String> tcEstado;

    private Usuario usuarioSeleccionado;

    // Controller para reservas
    private ReservaClaseController reservaController;

    @FXML
    void initialize() {
        comboBoxClase.getItems().addAll("Yoga", "Spinning", "Zumba");
        comboBoxHorario.getItems().addAll("8:00 AM - 10:00 AM", "10:00 AM - 12:00 PM", "12:00 PM - 2:00 PM");
        comboBoxEntrenador.getItems().clear();
        comboBoxEntrenador.setDisable(true);

        ObservableList<Usuario> listaUsuarios = ModelFactory.getInstance().obtenerUsuariosObservable();
        initDataBinding();
        tableUsuario.setItems(listaUsuarios);
        listenerSelection();
        lblBeneficios.setText("Seleccione un usuario");
    }

    private void initDataBinding() {
        tcNombre.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNombre()));
        tcIdentificacion.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getIdentificacion()));
        tcTipo.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getMembresia()));
        tcClase.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getReservas().isEmpty() ? "Sin clase" : c.getValue().getReservas().get(0).getClase()));
        tcHorarior.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getReservas().isEmpty() ? "Sin horario" : c.getValue().getReservas().get(0).getHorario()));
        tcFecha.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getReservas().isEmpty() ? "Sin fecha" : c.getValue().getReservas().get(0).getFecha()));
        tcEntrenador.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getReservas().isEmpty() ? "Sin entrenador" : c.getValue().getReservas().get(0).getEntrenador()));
        tcEstado.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getEstadoMembresia()));
    }

    private void listenerSelection() {
        tableUsuario.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            usuarioSeleccionado = newSel;
            if (newSel != null) {
                mostrarBeneficios(newSel);
                prepararEntrenadoresSegunMembresia(newSel);
            } else {
                lblBeneficios.setText("Seleccione un usuario");
                comboBoxEntrenador.getItems().clear();
                comboBoxEntrenador.setDisable(true);
            }
        });
    }

    private void prepararEntrenadoresSegunMembresia(Usuario usuario) {
        boolean esVIP = "VIP".equalsIgnoreCase(usuario.getMembresia());
        comboBoxEntrenador.getItems().clear();
        comboBoxEntrenador.setDisable(!esVIP);
        if (esVIP) {
            comboBoxEntrenador.getItems().setAll("Camilo", "Andrés", "Sofía");
        }
    }

    private void mostrarBeneficios(Usuario usuario) {
        String tipoMembresia = usuario.getMembresia();
        String beneficios;
        if (tipoMembresia == null || tipoMembresia.isEmpty()) {
            beneficios = "Sin membresía asignada";
        } else {
            switch (tipoMembresia.toLowerCase()) {
                case "basica" -> beneficios = "Acceso general al gimnasio";
                case "premium" -> beneficios = "Acceso general al gimnasio, clases grupales ilimitadas";
                case "vip" -> beneficios = "Acceso general al gimnasio, clases grupales ilimitadas, entrenador personal";
                default -> beneficios = "Tipo de membresía no reconocido";
            }
        }
        lblBeneficios.setText(beneficios);
    }

    @FXML
    void onNuevo(ActionEvent event) {
        limpiarCampos();
        tableUsuario.getSelectionModel().clearSelection();
        comboBoxEntrenador.getItems().clear();
        comboBoxEntrenador.setDisable(true);
    }

    @FXML
    void onConfirmar(ActionEvent event) {
        if (!validarDatos()) return;

        String clase = comboBoxClase.getValue();
        String horario = comboBoxHorario.getValue();
        String entrenador = comboBoxEntrenador.isDisabled() ? "No disponible" : comboBoxEntrenador.getValue();
        String fechaIngresada = txtFecha.getText();

        LocalDate fecha;
        try {
            fecha = LocalDate.parse(fechaIngresada, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        } catch (DateTimeParseException e) {
            mostrarAlerta("Error", "Formato de fecha inválido (use dd/MM/yyyy)", Alert.AlertType.ERROR);
            return;
        }

        ReservaClaseDto dto = new ReservaClaseDto(clase, horario, entrenador, fecha, usuarioSeleccionado.getIdentificacion());

        if (reservaController == null) reservaController = new ReservaClaseController();

        if (!reservaController.validarReserva(dto)) {
            mostrarAlerta("Error", "Datos de reserva inválidos", Alert.AlertType.ERROR);
            return;
        }

        if (!reservaController.agregarReserva(dto)) {
            mostrarAlerta("Error", "No se pudo crear la reserva. Verifique membresía y rango de fechas", Alert.AlertType.ERROR);
            return;
        }

        tableUsuario.refresh();
        mostrarAlerta("Éxito", "Reserva registrada", Alert.AlertType.INFORMATION);
        limpiarCampos();
    }

    @FXML
    void onActualizar(ActionEvent event) {
        onConfirmar(event);
    }

    @FXML
    void onEliminar(ActionEvent event) {
        if (usuarioSeleccionado == null || usuarioSeleccionado.getReservas().isEmpty()) {
            mostrarAlerta("Error", "Seleccione un usuario con reserva", Alert.AlertType.ERROR);
            return;
        }

        ReservaClaseDto dto = new ReservaClaseDto(null, null, null, null, usuarioSeleccionado.getIdentificacion());
        if (reservaController == null) reservaController = new ReservaClaseController();

        if (!reservaController.eliminarReserva(dto)) {
            mostrarAlerta("Error", "No se pudo eliminar la reserva", Alert.AlertType.ERROR);
            return;
        }

        tableUsuario.refresh();
        mostrarAlerta("Éxito", "Reserva eliminada", Alert.AlertType.INFORMATION);
        limpiarCampos();
    }

    private boolean validarDatos() {
        if (usuarioSeleccionado == null) {
            mostrarAlerta("Error", "Seleccione un usuario", Alert.AlertType.ERROR);
            return false;
        }
        if (!"ACTIVA".equals(usuarioSeleccionado.getEstadoMembresia())) {
            mostrarAlerta("Error", "La membresía no está activa", Alert.AlertType.ERROR);
            return false;
        }
        if (comboBoxClase.getValue() == null) {
            mostrarAlerta("Error", "Seleccione una clase", Alert.AlertType.ERROR);
            return false;
        }
        if (comboBoxHorario.getValue() == null) {
            mostrarAlerta("Error", "Seleccione un horario", Alert.AlertType.ERROR);
            return false;
        }
        if (!comboBoxEntrenador.isDisabled() && comboBoxEntrenador.getValue() == null) {
            mostrarAlerta("Error", "Seleccione un entrenador", Alert.AlertType.ERROR);
            return false;
        }
        if (txtFecha.getText() == null || txtFecha.getText().isEmpty()) {
            mostrarAlerta("Error", "Ingrese una fecha", Alert.AlertType.ERROR);
            return false;
        }
        return true;
    }

    private void limpiarCampos() {
        comboBoxClase.setValue(null);
        comboBoxHorario.setValue(null);
        comboBoxEntrenador.setValue(null);
        txtFecha.clear();
        lblBeneficios.setText("Seleccione un usuario");
    }

    private void mostrarAlerta(String titulo, String contenido, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(contenido);
        alert.showAndWait();
    }

    // Setter para inyección (testabilidad/DI)
    public void setReservaController(ReservaClaseController controller) {
        this.reservaController = controller;
    }
}