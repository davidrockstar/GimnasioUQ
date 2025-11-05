package gimnasiouq.gimnasiouq.viewcontroller;

import java.net.URL;
import java.util.ResourceBundle;

import gimnasiouq.gimnasiouq.factory.ModelFactory;
import gimnasiouq.gimnasiouq.model.*;
import gimnasiouq.gimnasiouq.controller.UsuarioController;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class RecepUsuariosViewController {

    ObservableList<Usuario> listaUsuarios;
    Usuario usuarioSeleccionado;
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
    private TableColumn<Usuario, String> tcUsuario;

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
    private ComboBox<String> comboBoxUsuarios;

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
        if (comboBoxMembresia != null){comboBoxMembresia.getItems().addAll("Basica", "Premium", "VIP");}
        if (comboBoxUsuarios != null){comboBoxUsuarios.getItems().addAll("Externo", "Estudiante", "TrabajadorUQ");}
    }

    private void initView() {
        initDataBinding();
        listaUsuarios = ModelFactory.getInstance().obtenerUsuariosObservable();
        tableUsuario.setItems(listaUsuarios);
        listenerSelection();
    }

    private void obtenerUsuarios() {
        listaUsuarios = ModelFactory.getInstance().obtenerUsuariosObservable();
    }

    private void initDataBinding() {
        if (tcNombre != null) {
            tcNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        }
        if (tcIdentificacion != null) {
            tcIdentificacion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIdentificacion()));
        }
        if (tcEdad != null) {
            tcEdad.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEdad()));
        }
        if (tcCelular != null) {
            tcCelular.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCelular()));
        }
        if (tcMembresia != null) {
            tcMembresia.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMembresia()));
        }
        if (tcUsuario != null) {
            tcUsuario.setCellValueFactory(cellData -> {
                if (cellData.getValue() instanceof Estudiante) {
                    return new SimpleStringProperty("Estudiante");
                } else if (cellData.getValue() instanceof TrabajadorUQ) {
                    return new SimpleStringProperty("Trabajador UQ");
                } else {
                    return new SimpleStringProperty("Externo");
                }
            });
        }
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
        Usuario usuario = crearUsuario();

        if (datosValidos(usuario)) {
            if (usuarioController == null) {
                usuarioController = new UsuarioController();
            }

            if (usuarioController.agregarUsuario(usuario)) {
                limpiarCampos();

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
            Usuario usuarioActualizado = crearUsuario();

            if (datosValidos(usuarioActualizado)) {
                if (usuarioController == null) {
                    usuarioController = new UsuarioController();
                }

                if (usuarioController.actualizarUsuario(usuarioSeleccionado.getIdentificacion(), usuarioActualizado)) {
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
        comboBoxUsuarios.setValue(null);
    }

    private boolean datosValidos(Usuario usuario) {
        return usuario != null &&
                usuario.getNombre() != null && !usuario.getNombre().isEmpty() &&
                usuario.getIdentificacion() != null && !usuario.getIdentificacion().isEmpty() &&
                usuario.getEdad() != null && !usuario.getEdad().isEmpty() &&
                usuario.getCelular() != null && !usuario.getCelular().isEmpty() &&
                usuario.getMembresia() != null && !usuario.getMembresia().isEmpty();
    }

    private Usuario crearUsuario() {
        String tipoUsuario = comboBoxUsuarios.getValue();
        if (tipoUsuario == null) {
            mostrarVentanaEmergente("Tipo de usuario no seleccionado", "Error", "Debe seleccionar un tipo de usuario", Alert.AlertType.ERROR);
            return null;
        }

        String nombre = txtNombre.getText();
        String id = txtIdentificacion.getText();
        String edad = txtEdad.getText();
        String celular = txtCelular.getText();
        String membresia = comboBoxMembresia.getValue();

        Usuario nuevoUsuario;

        switch (tipoUsuario) {
            case "Estudiante":
                nuevoUsuario = new Estudiante(nombre, id, edad, celular, membresia);
                break;
            case "TrabajadorUQ":
                nuevoUsuario = new TrabajadorUQ(nombre, id, edad, celular, membresia);
                break;
            default:
                nuevoUsuario = new Externo(nombre, id, edad, celular, membresia);
                break;
        }

        // Asignar el tipo de membresía seleccionado en la UI.
        // Esto corrige el valor temporal que pudo haber sido asignado por el constructor.
        nuevoUsuario.setTipoMembresia(membresia);

        return nuevoUsuario;
    }

    private void mostrarInformacionUsuario(Usuario usuarioSeleccionado) {
        if (usuarioSeleccionado != null) {
            txtNombre.setText(usuarioSeleccionado.getNombre());
            txtIdentificacion.setText(usuarioSeleccionado.getIdentificacion());
            txtEdad.setText(usuarioSeleccionado.getEdad());
            txtCelular.setText(usuarioSeleccionado.getCelular());
            comboBoxMembresia.setValue(usuarioSeleccionado.getMembresia());

            if (usuarioSeleccionado instanceof Estudiante) {
                comboBoxUsuarios.setValue("Estudiante");
            } else if (usuarioSeleccionado instanceof TrabajadorUQ) {
                comboBoxUsuarios.setValue("TrabajadorUQ");
            } else {
                comboBoxUsuarios.setValue("Externo");
            }
        }
    }

    private void mostrarVentanaEmergente(String titulo, String header, String contenido, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(titulo);
        alert.setHeaderText(header);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
}
