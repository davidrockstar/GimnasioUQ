package gimnasiouq.gimnasiouq.factory;

import gimnasiouq.gimnasiouq.model.GimnasioUQ;
import gimnasiouq.gimnasiouq.model.Usuario;
import gimnasiouq.gimnasiouq.util.DataUtil;

import java.util.List;

public class ModelFactory {

    public static ModelFactory modelFactory;
    private GimnasioUQ gimnasioUQ;

    public static ModelFactory getInstance(){
        if (modelFactory == null){
            modelFactory = new ModelFactory();
        }
        return modelFactory;
    }

    private ModelFactory(){
        gimnasioUQ = DataUtil.inicializarDatos();
    }

    public GimnasioUQ getGimnasioUQ() {
        return gimnasioUQ;
    }

    public List<Usuario> obtenerUsuarios() {
        return gimnasioUQ.getListaUsuarios();
    }

    public boolean agregarUsuario(Usuario usuario) {
        return gimnasioUQ.agregarUsuario(usuario);
    }

    public boolean actualizarUsuario(String identificacion, Usuario usuarioActualizado) {
        return gimnasioUQ.actualizarUsuario(identificacion, usuarioActualizado);
    }

    public boolean eliminarUsuario(String identificacion) {
        return gimnasioUQ.eliminarUsuario(identificacion);
    }

    public Usuario buscarUsuario(String identificacion) {
        return gimnasioUQ.buscarUsuarioPorIdentificacion(identificacion);
    }
}
