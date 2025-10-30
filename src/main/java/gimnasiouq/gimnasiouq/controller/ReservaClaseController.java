package gimnasiouq.gimnasiouq.controller;

import gimnasiouq.gimnasiouq.factory.ModelFactory;
import gimnasiouq.gimnasiouq.mapping.dto.ReservaClaseDto;
import gimnasiouq.gimnasiouq.mapping.mappers.GimnasioUqMappingImpl;
import gimnasiouq.gimnasiouq.model.ReservaClase;
import gimnasiouq.gimnasiouq.model.Usuario;

import java.time.LocalDate;

public class ReservaClaseController {

    private final GimnasioUqMappingImpl mapper = new GimnasioUqMappingImpl();

    public ReservaClase crearReserva(ReservaClaseDto dto) {
        if (dto == null) return null;
        return mapper.reservaDtoToReserva(dto);
    }

    public boolean validarReserva(ReservaClaseDto dto) {
        if (dto == null) return false;
        if (dto.clase() == null || dto.clase().isEmpty()) return false;
        if (dto.horario() == null || dto.horario().isEmpty()) return false;
        if (dto.fecha() == null) return false;
        if (dto.identificacionUsuario() == null || dto.identificacionUsuario().isEmpty()) return false;
        return true;
    }

    // Agrega una reserva al usuario identificado por el DTO. Verifica membresía vigente y rango de fecha.
    public boolean agregarReserva(ReservaClaseDto dto) {
        if (!validarReserva(dto)) return false;
        Usuario u = ModelFactory.getInstance().buscarUsuario(dto.identificacionUsuario());
        if (u == null) return false;
        if (!u.tieneMembresiaActiva()) return false;

        LocalDate inicio = u.getFechaInicioMembresia();
        LocalDate fin = u.getFechaFinMembresia();
        LocalDate fecha = dto.fecha();

        if (inicio == null || fin == null || fecha == null) return false;
        if (fecha.isBefore(inicio) || fecha.isAfter(fin)) return false;

        ReservaClase r = mapper.reservaDtoToReserva(dto);
        // Delegar la persistencia al ModelFactory
        return ModelFactory.getInstance().agregarReservaAUsuario(u.getIdentificacion(), r);
    }

    public boolean eliminarReserva(ReservaClaseDto dto) {
        if (dto == null || dto.identificacionUsuario() == null) return false;
        // Delegate to ModelFactory
        return ModelFactory.getInstance().eliminarReservasUsuario(dto.identificacionUsuario());
    }

    public boolean actualizarReserva(ReservaClaseDto dto) {
        if (dto == null) return false;
        if (!validarReserva(dto)) return false;
        ReservaClase r = mapper.reservaDtoToReserva(dto);
        return ModelFactory.getInstance().actualizarReservaUsuario(dto.identificacionUsuario(), r);
    }
}
