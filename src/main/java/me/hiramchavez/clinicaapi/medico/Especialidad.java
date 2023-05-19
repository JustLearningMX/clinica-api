package me.hiramchavez.clinicaapi.medico;

public enum Especialidad {
    ORTO("Ortopedia"),
    CARDIO("Cardiologia"),
    GINE("Ginecologia"),
    PEDIA("Pediatria");

    private String nombre;

    Especialidad(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
}
