package gimnasiouq.gimnasiouq.viewcontroller;

import gimnasiouq.gimnasiouq.model.Estudiante;
import gimnasiouq.gimnasiouq.model.Membresia;
import gimnasiouq.gimnasiouq.model.TrabajadorUQ;
import gimnasiouq.gimnasiouq.model.Usuario;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.time.format.DateTimeFormatter;

public class MembresiaDetallesViewController {

    @FXML
    private Label lblNombre;

    @FXML
    private Label lblIdentificacion;

    @FXML
    private Label lblTipoUsuario;

    @FXML
    private Label lblPlan;

    @FXML
    private Label lblCosto;

    @FXML
    private Label lblFechaInicio;

    @FXML
    private Label lblFechaFin;

    @FXML
    private Label lblEstado;

    @FXML
    private Label lblFechaPago;

    @FXML
    private Label lblHoraPago;

    private final DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("hh:mm:ss a");

    /**
     * Inicializa la ventana con los datos del usuario seleccionado.
     * Este método debe ser llamado por el controlador que abre esta ventana.
     * @param usuario El usuario del cual se mostrarán los detalles.
     */
    public void initData(Usuario usuario) {
        if (usuario == null) return;

        lblNombre.setText(usuario.getNombre());
        lblIdentificacion.setText(usuario.getIdentificacion());

        // Determinar el tipo de usuario
        if (usuario instanceof Estudiante) {
            lblTipoUsuario.setText("Estudiante");
        } else if (usuario instanceof TrabajadorUQ) {
            lblTipoUsuario.setText("Trabajador UQ");
        } else {
            lblTipoUsuario.setText("Externo");
        }

        Membresia membresia = usuario.getMembresiaActiva();

        // Mostrar detalles de la membresía
        if (membresia != null) {
            lblPlan.setText(usuario.getPlanMembresia());
            lblCosto.setText(usuario.getCostoMembresiaFormateado());
            lblFechaInicio.setText(usuario.getFechaInicioFormateada());
            lblFechaFin.setText(usuario.getFechaFinFormateada());
            lblEstado.setText(usuario.getEstadoMembresia());

            // Mostrar fecha y hora de pago
            if (membresia.getFechaPago() != null) {
                lblFechaPago.setText(membresia.getFechaPago().format(formatoFecha));
            }
            if (membresia.getHoraPago() != null) {
                lblHoraPago.setText(membresia.getHoraPago().format(formatoHora));
            }
        } else {
            // Estado si no tiene una membresía asignada
            lblPlan.setText("N/A");
            lblCosto.setText("N/A");
            lblFechaInicio.setText("N/A");
            lblFechaFin.setText("N/A");
            lblEstado.setText("Sin membresía");
            lblFechaPago.setText("N/A");
            lblHoraPago.setText("N/A");
        }
    }
}
