package gimnasiouq.gimnasiouq.factory;

import gimnasiouq.gimnasiouq.model.Entrenador;
import gimnasiouq.gimnasiouq.model.GimnasioUQ;
import gimnasiouq.gimnasiouq.model.RegistroAcceso;
import gimnasiouq.gimnasiouq.model.ReservaClase;
import gimnasiouq.gimnasiouq.model.Usuario;
import gimnasiouq.gimnasiouq.util.DataUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class ModelFactory {

    private static ModelFactory modelFactory;
    private final GimnasioUQ gimnasioUQ;

    private final ObservableList<Usuario> listaUsuariosObservable;
    private final ObservableList<Entrenador> listaEntrenadorObservable;

    public static ModelFactory getInstance(){
        if (modelFactory == null){
            modelFactory = new ModelFactory();
        }
        return modelFactory;
    }

    private ModelFactory(){
        gimnasioUQ = DataUtil.inicializarDatos();
        listaUsuariosObservable = FXCollections.observableArrayList(gimnasioUQ.getListaUsuarios());
        listaEntrenadorObservable = FXCollections.observableArrayList(gimnasioUQ.getListaEntrenador());
    }

    public List<Usuario> obtenerUsuarios() { return gimnasioUQ.getListaUsuarios(); }

    public ObservableList<Usuario> obtenerUsuariosObservable() {
        listaUsuariosObservable.setAll(gimnasioUQ.getListaUsuarios());
        return listaUsuariosObservable;
    }

    public ObservableList<Entrenador> obtenerEntrenadorObservable(){
        listaEntrenadorObservable.setAll(gimnasioUQ.getListaEntrenador());
        return listaEntrenadorObservable;
    }

    public boolean agregarUsuario(Usuario usuario) {
        boolean ok = gimnasioUQ.agregarUsuario(usuario);
        if (ok) listaUsuariosObservable.setAll(gimnasioUQ.getListaUsuarios());
        return ok;
    }

    public boolean actualizarUsuario(String identificacion, Usuario usuarioActualizado) {
        boolean ok = gimnasioUQ.actualizarUsuario(identificacion, usuarioActualizado);
        if (ok) listaUsuariosObservable.setAll(gimnasioUQ.getListaUsuarios());
        return ok;
    }

    public boolean eliminarUsuario(String identificacion) {
        boolean ok = gimnasioUQ.eliminarUsuario(identificacion);
        if (ok) listaUsuariosObservable.setAll(gimnasioUQ.getListaUsuarios());
        return ok;
    }

    public Usuario buscarUsuario(String identificacion) {
        return gimnasioUQ.buscarUsuarioPorIdentificacion(identificacion);
    }

    // ====== Entrenadores ======
    public boolean agregarEntrenador(Entrenador entrenador) {
        boolean ok = gimnasioUQ.getListaEntrenador().add(entrenador);
        if (ok) listaEntrenadorObservable.setAll(gimnasioUQ.getListaEntrenador());
        return ok;
    }

    // ====== Accesos / Reservas ======
    public List<RegistroAcceso> getListaRegistrosAcceso() {
        return gimnasioUQ.getListaRegistrosAcceso();
    }

    // Aplana reservas desde los usuarios; asigna identificación en cada reserva
    public ObservableList<ReservaClase> obtenerReservasDeUsuarios() {
        ObservableList<ReservaClase> reservas = FXCollections.observableArrayList();
        for (Usuario u : gimnasioUQ.getListaUsuarios()) {
            if (u.getReservas() != null && !u.getReservas().isEmpty()) {
                for (ReservaClase r : u.getReservas()) {
                    r.setIdentificacion(u.getIdentificacion());
                    reservas.add(r);
                }
            }
        }
        return reservas;
    }

    public boolean agregarRegistroAcceso(RegistroAcceso registro) {
        return gimnasioUQ.agregarRegistroAcceso(registro);
    }
}