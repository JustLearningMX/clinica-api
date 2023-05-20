package me.hiramchavez.clinicaapi.dto;

import jakarta.validation.constraints.NotBlank;
import me.hiramchavez.clinicaapi.Model.Direccion;

public record DireccionRecord (
        @NotBlank
        String calle,
        @NotBlank
        String distrito,
        @NotBlank
        String ciudad,
        @NotBlank
        String numero,
        @NotBlank
        String complemento) {
}
