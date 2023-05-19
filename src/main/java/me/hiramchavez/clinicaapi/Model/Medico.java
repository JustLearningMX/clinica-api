package me.hiramchavez.clinicaapi.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import me.hiramchavez.clinicaapi.dto.MedicoRecord;
import me.hiramchavez.clinicaapi.medico.Especialidad;

@Table(name = "medicos")
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Medico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String email;
    private String telefono;
    private String documento;
    @Enumerated(EnumType.STRING)
    private Especialidad especialidad;
    @Embedded
    private Direccion direccion;

    public Medico(MedicoRecord medico) {
        this.nombre = medico.nombre();
        this.email = medico.email();
        this.telefono = medico.telefono();
        this.documento = medico.documento();
        this.especialidad = medico.especialidad();
        this.direccion = new Direccion(medico.direccion());
    }
}
