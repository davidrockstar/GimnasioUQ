package gimnasiouq.gimnasiouq.viewcontroller;

import gimnasiouq.gimnasiouq.controller.ReservaClaseController;
import gimnasiouq.gimnasiouq.factory.ModelFactory;
import gimnasiouq.gimnasiouq.model.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.format.DateTimeParseException;
import java.util.List;

public class RecepReservaClasesViewController {

    @FXML
    private Button btnConfirmar;
    @FXML
    private Button btnActualizar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnNuevo;

    @FXML
    private TextField txtFecha;
    @FXML
    private ComboBox<String> comboBoxClase;
    @FXML
    private ComboBox<String> comboBoxEntrenador;
    @FXML
    private ComboBox<String> comboBoxHorario;
    @FXML
    private Label lblBeneficios;

    @FXML
    private TableView<Usuario> tableUsuario;
    @FXML
    private TableColumn<Usuario, String> tcNombre;
    @FXML
    private TableColumn<Usuario, String> tcIdentificacion;
    @FXML
    private TableColumn<Usuario, String> tcTipo;
    @FXML
    private TableColumn<Usuario, String> tcClase;
    @FXML
    private TableColumn<Usuario, String> tcHorarior;
    @FXML
    private TableColumn<Usuario, String> tcFecha;
    @FXML
    private TableColumn<Usuario, String> tcEntrenador;
    @FXML
    private TableColumn<Usuario, String> tcEstado;

    private Usuario usuarioSeleccionado;

    private ReservaClaseController reservaController;

    @FXML
    void initialize() {
        reservaController = new ReservaClaseController();

        comboBoxClase.getItems().addAll("Yoga", "Spinning", "Zumba");
        comboBoxHorario.getItems().addAll("8:00 AM - 10:00 AM", "10:00 AM - 12:00 PM", "12:00 PM - 2:00 PM");
        comboBoxEntrenador.getItems().clear();
        comboBoxEntrenador.setDisable(true);

        ObservableList<Usuario> listaUsuarios = ModelFactory.getInstance().obtenerUsuariosObservable();
        initDataBinding();
        tableUsuario.setItems(listaUsuarios);
        listenerSelection();
        lblBeneficios.setText("Seleccione un usuario");

        cargarEntrenadoresDisponibles();
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

    private void cargarEntrenadoresDisponibles() {
        ObservableList<Entrenador> listaEntrenadores = ModelFactory.getInstance().obtenerEntrenadorObservable();
        // Actualizar el listener para refrescar el comboBox cuando cambie la lista
        listaEntrenadores.addListener((javafx.collections.ListChangeListener.Change<? extends Entrenador> c) -> {
            if (usuarioSeleccionado != null) {
                actualizarComboBoxEntrenadores();
            }
        });
    }

    private void actualizarComboBoxEntrenadores() {
        boolean esVIP = "VIP".equalsIgnoreCase(usuarioSeleccionado.getMembresia());
        comboBoxEntrenador.getItems().clear();
        comboBoxEntrenador.setDisable(!esVIP);

        if (esVIP) {
            List<Entrenador> listaEntrenadores = ModelFactory.getInstance().obtenerEntrenadores();

            if (listaEntrenadores.isEmpty()) {
                comboBoxEntrenador.setDisable(true);
                mostrarAlerta("Sin entrenadores", "No hay entrenadores disponibles. Por favor, cree un entrenador primero en la sección de Gestión de Entrenadores.", Alert.AlertType.WARNING);
            } else {
                for (Entrenador e : listaEntrenadores) {
                    comboBoxEntrenador.getItems().add(e.getNombre());
                }
            }
        }
    }

    private void prepararEntrenadoresSegunMembresia(Usuario usuario) {
        boolean esVIP = "VIP".equalsIgnoreCase(usuario.getMembresia());
        comboBoxEntrenador.getItems().clear();
        comboBoxEntrenador.setDisable(!esVIP);

        if (esVIP) {
            List<Entrenador> entrenadores = ModelFactory.getInstance().obtenerEntrenadores();

            if (entrenadores.isEmpty()) {
                comboBoxEntrenador.setDisable(true);
                mostrarAlerta("Sin entrenadores", "No hay entrenadores disponibles. Por favor, cree un entrenador primero en la sección de Gestión de Entrenadores.", Alert.AlertType.WARNING);
            } else {
                for (Entrenador e : entrenadores) {
                    comboBoxEntrenador.getItems().add(e.getNombre());
                }
            }
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
                case "vip" ->
                        beneficios = "Acceso general al gimnasio, clases grupales ilimitadas, entrenador personal";
                default -> beneficios = "Tipo de membresía no reconocido";
            }
        }
        lblBeneficios.setText(beneficios);
    }

    @FXML
    void onNuevo(ActionEvent event) {
        nuevaReserva();
    }

    @FXML
    void onConfirmar(ActionEvent event) {
        confirmarReserva();
    }

    @FXML
    void onActualizar(ActionEvent event) {
        actualizarReserva();
    }

    @FXML
    void onEliminar(ActionEvent event) {
        eliminarReserva();
    }

    private void nuevaReserva() {
        limpiarCampos();
        tableUsuario.getSelectionModel().clearSelection();
        usuarioSeleccionado = null;
    }

    private void confirmarReserva() {
        if (usuarioSeleccionado == null) {
            mostrarAlerta("Error", "Debe seleccionar un usuario de la tabla.", Alert.AlertType.ERROR);
            return;
        }

        String clase = comboBoxClase.getValue();
        String horario = comboBoxHorario.getValue();
        String fechaIngresada = txtFecha.getText();
        String entrenador = comboBoxEntrenador.getValue();

        if (clase == null || clase.isEmpty()) {
            mostrarAlerta("Error", "Debe seleccionar una clase.", Alert.AlertType.ERROR);
            return;
        }

        if (horario == null || horario.isEmpty()) {
            mostrarAlerta("Error", "Debe seleccionar un horario.", Alert.AlertType.ERROR);
            return;
        }

        if (fechaIngresada == null || fechaIngresada.isEmpty()) {
            mostrarAlerta("Error", "Debe ingresar una fecha (formato: dd/MM/yyyy).", Alert.AlertType.ERROR);
            return;
        }

        // Si es VIP y no seleccionó entrenador
        boolean esVIP = "VIP".equalsIgnoreCase(usuarioSeleccionado.getMembresia());
        if (esVIP && (entrenador == null || entrenador.isEmpty())) {
            mostrarAlerta("Error", "Los usuarios VIP deben seleccionar un entrenador.", Alert.AlertType.ERROR);
            return;
        }

        // Si no es VIP, no debe tener entrenador
        if (!esVIP) {
            entrenador = "Sin entrenador";
        }

        ReservaClase reserva = new ReservaClase(clase, horario, entrenador, fechaIngresada);
        reserva.setIdentificacion(usuarioSeleccionado.getIdentificacion());

        boolean exito = reservaController.agregarReserva(reserva);

        if (exito) {
            mostrarAlerta("Éxito", "Reserva creada exitosamente.", Alert.AlertType.INFORMATION);
            limpiarCampos();
            tableUsuario.refresh();
        } else {
            mostrarAlerta("Error", "No se pudo crear la reserva. Verifique que el usuario tenga una membresía activa y que la fecha esté dentro del período de la membresía.", Alert.AlertType.ERROR);
        }
    }

    private void actualizarReserva() {
        if (usuarioSeleccionado == null) {
            mostrarAlerta("Error", "Debe seleccionar un usuario de la tabla.", Alert.AlertType.ERROR);
            return;
        }

        if (usuarioSeleccionado.getReservas().isEmpty()) {
            mostrarAlerta("Error", "El usuario seleccionado no tiene reservas para actualizar.", Alert.AlertType.ERROR);
            return;
        }

        String clase = comboBoxClase.getValue();
        String horario = comboBoxHorario.getValue();
        String fechaIngresada = txtFecha.getText();
        String entrenador = comboBoxEntrenador.getValue();

        if (clase == null || clase.isEmpty()) {
            mostrarAlerta("Error", "Debe seleccionar una clase.", Alert.AlertType.ERROR);
            return;
        }

        if (horario == null || horario.isEmpty()) {
            mostrarAlerta("Error", "Debe seleccionar un horario.", Alert.AlertType.ERROR);
            return;
        }

        if (fechaIngresada == null || fechaIngresada.isEmpty()) {
            mostrarAlerta("Error", "Debe ingresar una fecha (formato: dd/MM/yyyy).", Alert.AlertType.ERROR);
            return;
        }

        boolean esVIP = "VIP".equalsIgnoreCase(usuarioSeleccionado.getMembresia());
        if (esVIP && (entrenador == null || entrenador.isEmpty())) {
            mostrarAlerta("Error", "Los usuarios VIP deben seleccionar un entrenador.", Alert.AlertType.ERROR);
            return;
        }

        if (!esVIP) {
            entrenador = "Sin entrenador";
        }

        ReservaClase reserva = new ReservaClase(clase, horario, entrenador, fechaIngresada);
        reserva.setIdentificacion(usuarioSeleccionado.getIdentificacion());

        boolean exito = reservaController.actualizarReserva(reserva);

        if (exito) {
            mostrarAlerta("Éxito", "Reserva actualizada exitosamente.", Alert.AlertType.INFORMATION);
            limpiarCampos();
            tableUsuario.refresh();
        } else {
            mostrarAlerta("Error", "No se pudo actualizar la reserva.", Alert.AlertType.ERROR);
        }
    }

    private void eliminarReserva() {
        if (usuarioSeleccionado == null) {
            mostrarAlerta("Error", "Debe seleccionar un usuario de la tabla.", Alert.AlertType.ERROR);
            return;
        }

        if (usuarioSeleccionado.getReservas().isEmpty()) {
            mostrarAlerta("Error", "El usuario seleccionado no tiene reservas para eliminar.", Alert.AlertType.ERROR);
            return;
        }

        boolean exito = reservaController.eliminarReserva(usuarioSeleccionado.getIdentificacion());

        if (exito) {
            mostrarAlerta("Éxito", "Reserva eliminada exitosamente.", Alert.AlertType.INFORMATION);
            limpiarCampos();
            tableUsuario.refresh();
        } else {
            mostrarAlerta("Error", "No se pudo eliminar la reserva.", Alert.AlertType.ERROR);
        }
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
}
