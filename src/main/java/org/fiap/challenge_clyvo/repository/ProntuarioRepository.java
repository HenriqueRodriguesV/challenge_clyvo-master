package org.fiap.challenge_clyvo.repository;

import org.fiap.challenge_clyvo.model.Prontuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface ProntuarioRepository extends JpaRepository<Prontuario, Long> {
    Page<Prontuario> findByPetId(Long petId, Pageable pageable);

    Page<Prontuario> findByVeterinarioId(Long veterinarioId, Pageable pageable);

    Page<Prontuario> findByDataProcedimento(LocalDate dataProcedimento, Pageable pageable);

    Page<Prontuario> findByPetIdAndVeterinarioId(Long petId, Long veterinarioId, Pageable pageable);

    Page<Prontuario> findByDataProcedimentoBetween(LocalDate dataInicio, LocalDate dataFim, Pageable pageable);
}