package me.hiramchavez.clinicaapi.dto;

import jakarta.validation.constraints.NotNull;

public record MedicoActualizarRecord(
        @NotNull
        Long id,
        String nombre,
        String documento,
        DireccionRecord direccion) {
}
