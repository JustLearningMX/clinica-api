package me.hiramchavez.clinicaapi.dto;

import me.hiramchavez.clinicaapi.Model.Medico;

import java.util.List;

public record MedicoListadoRecord (
        Long id,
        String nombre,
        String especialidad,
        String documento,
        String email) {

    public MedicoListadoRecord(Medico medico) {
        this(medico.getId(), medico.getNombre(), medico.getEspecialidad().toString(), medico.getDocumento(), medico.getEmail());
    }
}
