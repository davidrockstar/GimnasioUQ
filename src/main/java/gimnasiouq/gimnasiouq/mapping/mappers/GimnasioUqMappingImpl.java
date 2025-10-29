package gimnasiouq.gimnasiouq.mapping.mappers;

import gimnasiouq.gimnasiouq.mapping.dto.UsuarioDto;
import gimnasiouq.gimnasiouq.model.Usuario;

public class GimnasioUqMappingImpl {

    public Usuario usuarioDtoToUsuario(UsuarioDto usuarioDto) {
        if (usuarioDto == null) return null;
        return new Usuario(
                usuarioDto.nombre(),
                usuarioDto.identificacion(),
                usuarioDto.edad(),
                usuarioDto.celular(),
                usuarioDto.membresia()
        );
    }
}
