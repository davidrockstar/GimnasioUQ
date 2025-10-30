package gimnasiouq.gimnasiouq.controller;

import gimnasiouq.gimnasiouq.mapping.dto.MembresiaDto;
import gimnasiouq.gimnasiouq.mapping.mappers.GimnasioUqMappingImpl;
import gimnasiouq.gimnasiouq.model.Membresia;
import gimnasiouq.gimnasiouq.factory.ModelFactory;
import gimnasiouq.gimnasiouq.model.Usuario;

import java.time.LocalDate;

public class MembresiaController {

    private final GimnasioUqMappingImpl mapper = new GimnasioUqMappingImpl();

    public boolean validarMembresia(MembresiaDto dto) {
        if (dto == null) return false;
        if (dto.tipo() == null || dto.tipo().trim().isEmpty()) return false;
        if (dto.inicio() == null || dto.fin() == null) return false;
        if (dto.inicio().isAfter(dto.fin())) return false;
        if (dto.costo() < 0) return false;
        return true;
    }

    // Eliminar membresía del usuario: delega en ModelFactory para persistir
    public boolean eliminarMembresiaUsuario(Usuario usuario) {
        if (usuario == null || usuario.getIdentificacion() == null) return false;
        return ModelFactory.getInstance().eliminarMembresiaUsuario(usuario.getIdentificacion());
    }

    // Asigna una membresía (desde DTO) a un usuario identificado por su identificación, delega en ModelFactory
    public boolean asignarMembresiaUsuario(String identificacionUsuario, MembresiaDto dto) {
        if (identificacionUsuario == null || identificacionUsuario.isEmpty() || dto == null) return false;
        if (!validarMembresia(dto)) return false;

        Membresia membresia = mapper.membresiaDtoToMembresia(dto);
        if (membresia == null) return false;

        return ModelFactory.getInstance().asignarMembresiaUsuario(identificacionUsuario, membresia);
    }

    // Actualizar membresía de usuario: delega en ModelFactory
    public boolean actualizarMembresiaUsuario(String identificacionUsuario, MembresiaDto dto) {
        if (identificacionUsuario == null || identificacionUsuario.isEmpty() || dto == null) return false;
        if (!validarMembresia(dto)) return false;
        Membresia membresia = mapper.membresiaDtoToMembresia(dto);
        if (membresia == null) return false;
        return ModelFactory.getInstance().actualizarMembresiaUsuario(identificacionUsuario, membresia);
    }
}
