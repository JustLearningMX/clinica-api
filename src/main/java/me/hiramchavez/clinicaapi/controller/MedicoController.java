package me.hiramchavez.clinicaapi.controller;

import jakarta.validation.Valid;
import me.hiramchavez.clinicaapi.Model.Medico;
import me.hiramchavez.clinicaapi.dto.MedicoRecord;
import me.hiramchavez.clinicaapi.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
