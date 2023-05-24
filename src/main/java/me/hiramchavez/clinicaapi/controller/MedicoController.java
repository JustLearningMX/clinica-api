package me.hiramchavez.clinicaapi.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import me.hiramchavez.clinicaapi.Model.Medico;
import me.hiramchavez.clinicaapi.dto.*;
import me.hiramchavez.clinicaapi.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

    private final MedicoRepository medicoRepository;

    @Autowired
    public MedicoController(MedicoRepository medicoRepository) {
        this.medicoRepository = medicoRepository;
    }

    @GetMapping
    public ResponseEntity<Page<MedicoListadoRecord>> listaMedicos(@PageableDefault(sort = "nombre") Pageable paginacion) {
        //return medicoRepository.findAll(paginacion).map(MedicoListadoRecord::new);
        return ResponseEntity.ok(medicoRepository.findMedicoByActivoTrue(paginacion).map(MedicoListadoRecord::new));
    }

    @PostMapping
    public ResponseEntity<MedicoRegistradoRecord> registrarMedico(@RequestBody @Valid MedicoRecord medico, UriComponentsBuilder uriBuilder) {
        Medico medicoBd = medicoRepository.save(new Medico(medico));
        MedicoRegistradoRecord medicoRegistradoRecord = new MedicoRegistradoRecord(
          medicoBd.getId(),
          medicoBd.getNombre(),
          medicoBd.getEmail(),
          medicoBd.getTelefono(),
          medicoBd.getDocumento(),
          medicoBd.getEspecialidad(),
          new DireccionRecord(
            medicoBd.getDireccion().getCalle(),
            medicoBd.getDireccion().getDistrito(),
            medicoBd.getDireccion().getCiudad(),
            medicoBd.getDireccion().getNumero(),
            medicoBd.getDireccion().getComplemento()
          )
        );

        URI url = uriBuilder.path("/medicos/{id}").buildAndExpand(medicoBd.getId()).toUri();
        return ResponseEntity.created(url).body(medicoRegistradoRecord);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<MedicoActualizarRecord> actualizarMedico(@RequestBody @Valid MedicoActualizarRecord medicoActualizarRecord) {
        Medico medico = medicoRepository.getReferenceById(medicoActualizarRecord.id());
        medico.actualizarDatos(medicoActualizarRecord);

        return ResponseEntity.ok(new MedicoActualizarRecord(
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
        ));
    }

    /*BORRADO A NIVEL DE BD*/
    /*@DeleteMapping("/{id}")
    @Transactional
    public void eliminarMedico(@PathVariable Long id) {
        medicoRepository.deleteById(id);
    }*/

    /*BORRADO A NIVEL LOGICO*/
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarMedico(@PathVariable Long id) {
        Medico medico = medicoRepository.getReferenceById(id);
        medico.desactivar();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @Transactional
    public ResponseEntity<MedicoRegistradoRecord> retornaDatosMedico(@PathVariable Long id) {
        Medico medico = medicoRepository.getReferenceById(id);
        var datosMedico = new MedicoRegistradoRecord(
          medico.getId(),
          medico.getNombre(),
          medico.getEmail(),
          medico.getTelefono(),
          medico.getDocumento(),
          medico.getEspecialidad(),
          new DireccionRecord(
            medico.getDireccion().getCalle(),
            medico.getDireccion().getDistrito(),
            medico.getDireccion().getCiudad(),
            medico.getDireccion().getNumero(),
            medico.getDireccion().getComplemento()
          )
        );

        return ResponseEntity.ok(datosMedico);
    }
}
