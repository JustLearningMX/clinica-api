package me.hiramchavez.clinicaapi.repository;

import me.hiramchavez.clinicaapi.Model.Medico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicoRepository extends JpaRepository<Medico, Long> {
}
