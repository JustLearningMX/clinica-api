package me.hiramchavez.clinicaapi.dto;

import me.hiramchavez.clinicaapi.medico.Especialidad;

public record MedicoRegistradoRecord(
        Long id,
        String nombre,
        String email,
        String telefono,
        String documento,
        Especialidad especialidad,
        DireccionRecord direccion) {
}
