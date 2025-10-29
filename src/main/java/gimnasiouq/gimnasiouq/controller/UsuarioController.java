package gimnasiouq.gimnasiouq.controller;

import gimnasiouq.gimnasiouq.factory.ModelFactory;
import gimnasiouq.gimnasiouq.mapping.dto.UsuarioDto;
import gimnasiouq.gimnasiouq.mapping.mappers.GimnasioUqMappingImpl;

public class UsuarioController {

    private final GimnasioUqMappingImpl mapper = new GimnasioUqMappingImpl();

    public boolean agregarUsuario(UsuarioDto usuarioDto) {
        if (usuarioDto == null) return false;
        return ModelFactory.getInstance().agregarUsuario(mapper.usuarioDtoToUsuario(usuarioDto));
    }

    public boolean actualizarUsuario(String identificacionOriginal, UsuarioDto usuarioDto) {
        if (identificacionOriginal == null || identificacionOriginal.isEmpty() || usuarioDto == null) return false;
        return ModelFactory.getInstance().actualizarUsuario(identificacionOriginal, mapper.usuarioDtoToUsuario(usuarioDto));
    }

    public boolean eliminarUsuario(String identificacion) {
        if (identificacion == null || identificacion.isEmpty()) return false;
        return ModelFactory.getInstance().eliminarUsuario(identificacion);
    }
}
