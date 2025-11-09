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

    public void initData(Usuario usuario) {
        if (usuario == null) return;

        if (lblNombre != null) lblNombre.setText(usuario.getNombre());
        if (lblIdentificacion != null) lblIdentificacion.setText(usuario.getIdentificacion());

        if (lblTipoUsuario != null) {
            if (usuario instanceof Estudiante) {
                lblTipoUsuario.setText("Estudiante");
            } else if (usuario instanceof TrabajadorUQ) {
                lblTipoUsuario.setText("Trabajador UQ");
            } else {
                lblTipoUsuario.setText("Externo");
            }
        }

        Membresia membresia = usuario.getMembresiaActiva();

        if (membresia != null) {
            if (lblPlan != null) lblPlan.setText(usuario.getPlanMembresia());
            if (lblCosto != null) lblCosto.setText(usuario.getCostoMembresiaFormateado());
            if (lblFechaInicio != null) lblFechaInicio.setText(usuario.getFechaInicioFormateada());
            if (lblFechaFin != null) lblFechaFin.setText(usuario.getFechaFinFormateada());
            if (lblEstado != null) {
                lblEstado.setText(usuario.getEstadoMembresia());
                if ("ACTIVA".equals(usuario.getEstadoMembresia())) {
                    lblEstado.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
                } else {
                    lblEstado.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                }
            }
            if (lblFechaPago != null && membresia.getFechaPago() != null) {
                lblFechaPago.setText(membresia.getFechaPago().format(formatoFecha));
            }
            if (lblHoraPago != null && membresia.getHoraPago() != null) {
                lblHoraPago.setText(membresia.getHoraPago().format(formatoHora));
            }
        } else {
            if (lblPlan != null) lblPlan.setText("N/A");
            if (lblCosto != null) lblCosto.setText("N/A");
            if (lblFechaInicio != null) lblFechaInicio.setText("N/A");
            if (lblFechaFin != null) lblFechaFin.setText("N/A");
            if (lblEstado != null) {
                lblEstado.setText("Sin membresía");
                lblEstado.setStyle("-fx-text-fill: orange; -fx-font-weight: bold;");
            }
            if (lblFechaPago != null) lblFechaPago.setText("N/A");
            if (lblHoraPago != null) lblHoraPago.setText("N/A");
        }
    }
}
