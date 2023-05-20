package me.hiramchavez.clinicaapi.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import me.hiramchavez.clinicaapi.Model.Medico;
import me.hiramchavez.clinicaapi.dto.DireccionRecord;
import me.hiramchavez.clinicaapi.dto.MedicoActualizarRecord;
import me.hiramchavez.clinicaapi.dto.MedicoListadoRecord;
import me.hiramchavez.clinicaapi.dto.MedicoRecord;
import me.hiramchavez.clinicaapi.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

    private MedicoRepository medicoRepository;

    @Autowired
    public MedicoController(MedicoRepository medicoRepository) {
        this.medicoRepository = medicoRepository;
    }

    @PostMapping
    public void registrarMedico(@RequestBody @Valid MedicoRecord medico) {
        medicoRepository.save(new Medico(medico));
    }

    @GetMapping
    public Page<MedicoListadoRecord> listaMedicos(@PageableDefault(sort = "nombre") Pageable paginacion) {
        return medicoRepository.findAll(paginacion)
                .map(MedicoListadoRecord::new);
    }

    @PutMapping
    @Transactional
    public MedicoActualizarRecord actualizarMedico(@RequestBody @Valid MedicoActualizarRecord medicoActualizarRecord) {
        Medico medico = medicoRepository.getReferenceById(medicoActualizarRecord.id());
        medico.actualizarDatos(medicoActualizarRecord);
        //Medico medicoUpdated = medicoRepository.save(medico);

        //return medicoUpdated;

        return new MedicoActualizarRecord(
                medico.getId(),
                medico.getNombre(),
                medico.getDocumento(),
                new DireccionRecord(
                        medico.getDireccion().getCalle(),
                        medico.getDireccion().getDistrito(),
                        medico.getDireccion().getCiudad(),
                        medico.getDireccion().getNumero(),
                        medico.getDireccion().getComplemento()
                )
        );
    }
}
