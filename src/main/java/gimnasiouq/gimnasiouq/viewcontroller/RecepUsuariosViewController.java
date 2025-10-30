package gimnasiouq.gimnasiouq.viewcontroller;

import java.net.URL;
import java.util.ResourceBundle;

import gimnasiouq.gimnasiouq.factory.ModelFactory;
import gimnasiouq.gimnasiouq.mapping.dto.UsuarioDto;
import gimnasiouq.gimnasiouq.model.Usuario;
import gimnasiouq.gimnasiouq.controller.UsuarioController;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class RecepUsuariosViewController {

    AdminViewController adminViewController;
    ObservableList<Usuario> listaUsuarios = FXCollections.observableArrayList();
    Usuario usuarioSeleccionado;

    // Controller que delega la lógica de negocio
    private UsuarioController usuarioController;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnActualizar;

    @FXML
    private Button btnAgregar;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnNuevo;

    @FXML
    private TableView<Usuario> tableUsuario;

    @FXML
    private TableColumn<Usuario, String> tcCelular;

    @FXML
    private TableColumn<Usuario, String> tcEdad;

    @FXML
    private TableColumn<Usuario, String> tcIdentificacion;

    @FXML
    private TableColumn<Usuario, String> tcMembresia;

    @FXML
    private TableColumn<Usuario, String> tcNombre;

    @FXML
    private TextField txtCelular;

    @FXML
    private TextField txtEdad;

    @FXML
    private TextField txtIdentificacion;

    @FXML
    private ComboBox<String> comboBoxMembresia;

    @FXML
    private TextField txtNombre;

    @FXML
    void onActualizar(ActionEvent event) {
        actualizarUsuario();
    }

    @FXML
    void onAgregar(ActionEvent event) {
        agregarUsuario();
    }

    @FXML
    void onEliminar(ActionEvent event) {
        eliminarUsuario();
    }

    @FXML
    void onNuevo(ActionEvent event) {
        nuevoUsuario();
    }

    @FXML
    void initialize() {
        initView();
        comboBoxMembresia.getItems().addAll("Basica", "Premium", "VIP");
    }

    private void initView() {
        initDataBinding();
        obtenerUsuarios();
        tableUsuario.getItems().clear();
        tableUsuario.setItems(listaUsuarios);
        listenerSelection();
    }

    private void obtenerUsuarios() {
        listaUsuarios.clear();
        listaUsuarios.addAll(ModelFactory.getInstance().obtenerUsuarios());
    }

    private void initDataBinding() {
        tcNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        tcIdentificacion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIdentificacion()));
        tcEdad.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEdad()));
        tcCelular.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCelular()));
        tcMembresia.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMembresia()));
    }

    private void listenerSelection() {
        tableUsuario.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newSelection) -> {
            usuarioSeleccionado = newSelection;
            mostrarInformacionUsuario(usuarioSeleccionado);
        });
    }

    private void nuevoUsuario() {
        limpiarCampos();
        usuarioSeleccionado = null;
        tableUsuario.getSelectionModel().clearSelection();
    }

    private void agregarUsuario() {
        UsuarioDto usuarioDto = crearUsuarioDto();

        if (datosValidos(usuarioDto)) {
            if (usuarioController == null) {
                usuarioController = new UsuarioController();
            }

            if (usuarioController.agregarUsuario(usuarioDto)) {
                // Agregamos el nuevo usuario a la lista observable
                listaUsuarios.add(new Usuario(
                        usuarioDto.nombre(),
                        usuarioDto.identificacion(),
                        usuarioDto.edad(),
                        usuarioDto.celular(),
                        usuarioDto.membresia()
                ));

                limpiarCampos();

                // Notificar al controlador padre para que actualice otras vistas
                if (adminViewController != null) {
                    adminViewController.notificarActualizacion();
                }

                mostrarVentanaEmergente("Usuario agregado", "Éxito",
                        "El usuario se agregó correctamente", Alert.AlertType.INFORMATION);
            } else {
                mostrarVentanaEmergente("Usuario no agregado", "Error",
                        "El usuario ya existe o los datos son inválidos", Alert.AlertType.ERROR);
            }
        } else {
            mostrarVentanaEmergente("Datos incompletos", "Error",
                    "Por favor complete todos los campos", Alert.AlertType.ERROR);
        }
    }

    private void actualizarUsuario() {
        if (usuarioSeleccionado != null) {
            UsuarioDto usuarioActualizado = crearUsuarioDto();

            if (datosValidos(usuarioActualizado)) {
                // Usamos la identificación del usuario seleccionado como clave
                if (usuarioController == null) {
                    usuarioController = new UsuarioController();
                }

                if (usuarioController.actualizarUsuario(usuarioSeleccionado.getIdentificacion(), usuarioActualizado)) {

                    // Actualizamos la lista observable: reemplazamos el objeto Usuario en la lista
                    int index = listaUsuarios.indexOf(usuarioSeleccionado);
                    if (index >= 0) {
                        listaUsuarios.set(index, new Usuario(
                                usuarioActualizado.nombre(),
                                usuarioActualizado.identificacion(),
                                usuarioActualizado.edad(),
                                usuarioActualizado.celular(),
                                usuarioActualizado.membresia()
                        ));
                    }

                    tableUsuario.refresh();
                    limpiarCampos();
                    mostrarVentanaEmergente("Usuario actualizado", "Éxito",
                            "El usuario se actualizó correctamente", Alert.AlertType.INFORMATION);
                } else {
                    mostrarVentanaEmergente("Usuario no actualizado", "Error",
                            "No se pudo actualizar el usuario. Verifique que la nueva identificación no exista",
                            Alert.AlertType.ERROR);
                }
            } else {
                mostrarVentanaEmergente("Datos incompletos", "Error",
                        "Por favor complete todos los campos", Alert.AlertType.ERROR);
            }
        } else {
            mostrarVentanaEmergente("Seleccione un usuario", "Advertencia",
                    "Debe seleccionar un usuario de la tabla para actualizarlo", Alert.AlertType.WARNING);
        }
    }

    private void eliminarUsuario() {
        if (usuarioSeleccionado != null) {
            // Confirmación antes de eliminar
            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setTitle("Confirmar eliminación");
            confirmacion.setHeaderText("¿Está seguro?");
            confirmacion.setContentText("¿Desea eliminar al usuario " +
                    usuarioSeleccionado.getNombre() + " con identificación " +
                    usuarioSeleccionado.getIdentificacion() + "?");

            confirmacion.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    if (usuarioController == null) {
                        usuarioController = new UsuarioController();
                    }

                    if (usuarioController.eliminarUsuario(usuarioSeleccionado.getIdentificacion())) {
                        listaUsuarios.remove(usuarioSeleccionado);
                        limpiarCampos();
                        usuarioSeleccionado = null;
                        mostrarVentanaEmergente("Usuario eliminado", "Éxito",
                                "El usuario se eliminó correctamente", Alert.AlertType.INFORMATION);
                    } else {
                        mostrarVentanaEmergente("Usuario no eliminado", "Error",
                                "No se pudo eliminar el usuario", Alert.AlertType.ERROR);
                    }
                }
            });
        } else {
            mostrarVentanaEmergente("Seleccione un usuario", "Advertencia",
                    "Debe seleccionar un usuario de la tabla para eliminarlo", Alert.AlertType.WARNING);
        }
    }

    private void limpiarCampos() {
        txtNombre.clear();
        txtIdentificacion.clear();
        txtEdad.clear();
        txtCelular.clear();
        comboBoxMembresia.setValue(null);
    }

    // Nuevo: validación para UsuarioDto
    private boolean datosValidos(UsuarioDto usuario) {
        return usuario != null &&
                usuario.nombre() != null && !usuario.nombre().isEmpty() &&
                usuario.identificacion() != null && !usuario.identificacion().isEmpty() &&
                usuario.edad() != null && !usuario.edad().isEmpty() &&
                usuario.celular() != null && !usuario.celular().isEmpty() &&
                usuario.membresia() != null && !usuario.membresia().isEmpty();
    }


    // Nuevo: crea UsuarioDto desde la vista
    private UsuarioDto crearUsuarioDto() {
        return new UsuarioDto(
                txtNombre.getText(),
                txtIdentificacion.getText(),
                txtEdad.getText(),
                txtCelular.getText(),
                comboBoxMembresia.getValue()
        );
    }

    private void mostrarInformacionUsuario(Usuario usuarioSeleccionado) {
        if (usuarioSeleccionado != null) {
            txtNombre.setText(usuarioSeleccionado.getNombre());
            txtIdentificacion.setText(usuarioSeleccionado.getIdentificacion());
            txtEdad.setText(usuarioSeleccionado.getEdad());
            txtCelular.setText(usuarioSeleccionado.getCelular());
            comboBoxMembresia.setValue(usuarioSeleccionado.getMembresia());
        }
    }

    private void mostrarVentanaEmergente(String titulo, String header, String contenido, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(titulo);
        alert.setHeaderText(header);
        alert.setContentText(contenido);
        alert.showAndWait();
    }

    public void refrescarTabla() {
        obtenerUsuarios();
        tableUsuario.refresh();
    }
}
