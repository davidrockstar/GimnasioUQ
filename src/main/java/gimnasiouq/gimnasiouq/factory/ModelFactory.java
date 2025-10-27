package gimnasiouq.gimnasiouq.factory;

import gimnasiouq.gimnasiouq.model.GimnasioUQ;
import gimnasiouq.gimnasiouq.model.RegistroAcceso;
import gimnasiouq.gimnasiouq.model.Usuario;
import gimnasiouq.gimnasiouq.util.DataUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class ModelFactory {

    public static ModelFactory modelFactory;
    private GimnasioUQ gimnasioUQ;
    
    // ⭐ NUEVA: Lista observable compartida por todos los controladores
    private ObservableList<Usuario> listaUsuariosObservable;

    public static ModelFactory getInstance(){
        if (modelFactory == null){
            modelFactory = new ModelFactory();
        }
        return modelFactory;
    }

    private ModelFactory(){
        gimnasioUQ = DataUtil.inicializarDatos();
        // ⭐ Inicializar la lista observable con los datos del gimnasio
        listaUsuariosObservable = FXCollections.observableArrayList(gimnasioUQ.getListaUsuarios());
    }

    public GimnasioUQ getGimnasioUQ() {
        return gimnasioUQ;
    }

    public List<Usuario> obtenerUsuarios() {
        return gimnasioUQ.getListaUsuarios();
    }

    public ObservableList<Usuario> obtenerUsuariosObservable() {
        // Sincronizar con la lista del gimnasio
        listaUsuariosObservable.setAll(gimnasioUQ.getListaUsuarios());
        return listaUsuariosObservable;
    }

    public boolean agregarUsuario(Usuario usuario) {
        boolean resultado = gimnasioUQ.agregarUsuario(usuario);
        if (resultado) {
            // ⭐ Actualizar la lista observable
            listaUsuariosObservable.setAll(gimnasioUQ.getListaUsuarios());
        }
        return resultado;
    }

    public boolean actualizarUsuario(String identificacion, Usuario usuarioActualizado) {
        boolean resultado = gimnasioUQ.actualizarUsuario(identificacion, usuarioActualizado);
        if (resultado) {
            // ⭐ Actualizar la lista observable
            listaUsuariosObservable.setAll(gimnasioUQ.getListaUsuarios());
        }
        return resultado;
    }

    public boolean eliminarUsuario(String identificacion) {
        boolean resultado = gimnasioUQ.eliminarUsuario(identificacion);
        if (resultado) {
            // ⭐ Actualizar la lista observable
            listaUsuariosObservable.setAll(gimnasioUQ.getListaUsuarios());
        }
        return resultado;
    }

    public Usuario buscarUsuario(String identificacion) {
        return gimnasioUQ.buscarUsuarioPorIdentificacion(identificacion);
    }

    public List<RegistroAcceso> obtenerRegistrosAcceso() {
        return gimnasioUQ.getListaRegistrosAcceso();
    }

    public boolean agregarRegistroAcceso(RegistroAcceso registro) {
        return gimnasioUQ.agregarRegistroAcceso(registro);
    }
}
