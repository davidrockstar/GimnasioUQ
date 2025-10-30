package gimnasiouq.gimnasiouq.mapping.mappers;

import gimnasiouq.gimnasiouq.mapping.dto.UsuarioDto;
import gimnasiouq.gimnasiouq.mapping.dto.MembresiaDto;
import gimnasiouq.gimnasiouq.mapping.dto.ReservaClaseDto;
import gimnasiouq.gimnasiouq.model.Usuario;
import gimnasiouq.gimnasiouq.model.Membresia;
import gimnasiouq.gimnasiouq.model.MembresiaBasica;
import gimnasiouq.gimnasiouq.model.MembresiaPremium;
import gimnasiouq.gimnasiouq.model.MembresiaVIP;
import gimnasiouq.gimnasiouq.model.ReservaClase;

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

    public Membresia membresiaDtoToMembresia(MembresiaDto dto) {
        if (dto == null) return null;
        String tipo = dto.tipo() != null ? dto.tipo().trim().toLowerCase() : "";
        return switch (tipo) {
            case "basica" -> new MembresiaBasica(dto.costo(), dto.inicio(), dto.fin());
            case "premium" -> new MembresiaPremium(dto.costo(), dto.inicio(), dto.fin());
            case "vip" -> new MembresiaVIP(dto.costo(), dto.inicio(), dto.fin());
            default -> new MembresiaBasica(dto.costo(), dto.inicio(), dto.fin());
        };
    }

    // Nuevo: mapea ReservaClaseDto -> ReservaClase
    public ReservaClase reservaDtoToReserva(ReservaClaseDto dto) {
        if (dto == null) return null;
        ReservaClase r = new ReservaClase(dto.clase(), dto.horario(), dto.entrenador(), dto.fecha() != null ? dto.fecha().toString() : null);
        r.setIdentificacion(dto.identificacionUsuario());
        return r;
    }

    // Opcional: mapea ReservaClase -> ReservaClaseDto
    public ReservaClaseDto reservaToReservaDto(ReservaClase r) {
        if (r == null) return null;
        java.time.LocalDate fecha = null;
        try {
            if (r.getFecha() != null) fecha = java.time.LocalDate.parse(r.getFecha());
        } catch (Exception ignored) {
        }
        return new ReservaClaseDto(r.getClase(), r.getHorario(), r.getEntrenador(), fecha, r.getIdentificacion());
    }
}
