package org.fiap.challenge_clyvo.service;

import org.fiap.challenge_clyvo.dto.ProntuarioRequestDTO;
import org.fiap.challenge_clyvo.dto.ProntuarioResponseDTO;
import org.fiap.challenge_clyvo.exception.ResourceNotFoundException;
import org.fiap.challenge_clyvo.model.Pet;
import org.fiap.challenge_clyvo.model.Prontuario;
import org.fiap.challenge_clyvo.model.Veterinario;
import org.fiap.challenge_clyvo.repository.PetRepository;
import org.fiap.challenge_clyvo.repository.ProntuarioRepository;
import org.fiap.challenge_clyvo.repository.VeterinarioRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class ProntuarioService {

    private final ProntuarioRepository prontuarioRepository;
    private final PetRepository petRepository;
    private final VeterinarioRepository veterinarioRepository;

    public ProntuarioService(ProntuarioRepository prontuarioRepository,
                             PetRepository petRepository,
                             VeterinarioRepository veterinarioRepository) {
        this.prontuarioRepository = prontuarioRepository;
        this.petRepository = petRepository;
        this.veterinarioRepository = veterinarioRepository;
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "prontuarios", key = "#pageable.pageNumber + '-' + #pageable.pageSize + '-' + #pageable.sort")
    public Page<ProntuarioResponseDTO> listarTodos(Pageable pageable) {
        return prontuarioRepository.findAll(pageable).map(this::toDTO);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "prontuarios", key = "#id")
    public ProntuarioResponseDTO buscarPorId(Long id) {
        return toDTO(buscarEntidade(id));
    }

    @Transactional(readOnly = true)
    public Page<ProntuarioResponseDTO> buscarPorPet(Long petId, Pageable pageable) {
        return prontuarioRepository.findByPetId(petId, pageable).map(this::toDTO);
    }

    @Transactional(readOnly = true)
    public Page<ProntuarioResponseDTO> buscarPorVeterinario(Long veterinarioId, Pageable pageable) {
        return prontuarioRepository.findByVeterinarioId(veterinarioId, pageable).map(this::toDTO);
    }

    @Transactional(readOnly = true)
    public Page<ProntuarioResponseDTO> buscarPorData(LocalDate data, Pageable pageable) {
        return prontuarioRepository.findByDataProcedimento(data, pageable).map(this::toDTO);
    }

    @Transactional(readOnly = true)
    public Page<ProntuarioResponseDTO> buscarPorPetEVeterinario(Long petId, Long veterinarioId, Pageable pageable) {
        return prontuarioRepository.findByPetIdAndVeterinarioId(petId, veterinarioId, pageable)
                .map(this::toDTO);
    }

    @Transactional(readOnly = true)
    public Page<ProntuarioResponseDTO> buscarPorPeriodo(LocalDate dataInicio, LocalDate dataFim, Pageable pageable) {
        return prontuarioRepository.findByDataProcedimentoBetween(dataInicio, dataFim, pageable)
                .map(this::toDTO);
    }

    @Transactional
    @CacheEvict(value = "prontuarios", allEntries = true)
    public ProntuarioResponseDTO salvar(ProntuarioRequestDTO dto) {
        Pet pet = petRepository.findById(dto.getPetId())
                .orElseThrow(() -> new ResourceNotFoundException("Pet não encontrado com id: " + dto.getPetId()));

        Veterinario veterinario = veterinarioRepository.findById(dto.getVeterinarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Veterinário não encontrado com id: " + dto.getVeterinarioId()));

        Prontuario prontuario = new Prontuario();
        prontuario.setProcedimento(dto.getProcedimento());
        prontuario.setDataProcedimento(dto.getDataProcedimento());
        prontuario.setLocalAtendimento(dto.getLocalAtendimento());
        prontuario.setPet(pet);
        prontuario.setVeterinario(veterinario);

        return toDTO(prontuarioRepository.save(prontuario));
    }

    @Transactional
    @CacheEvict(value = "prontuarios", allEntries = true)
    public ProntuarioResponseDTO atualizar(Long id, ProntuarioRequestDTO dto) {
        Prontuario prontuario = buscarEntidade(id);

        Pet pet = petRepository.findById(dto.getPetId())
                .orElseThrow(() -> new ResourceNotFoundException("Pet não encontrado com id: " + dto.getPetId()));

        Veterinario veterinario = veterinarioRepository.findById(dto.getVeterinarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Veterinário não encontrado com id: " + dto.getVeterinarioId()));

        prontuario.setProcedimento(dto.getProcedimento());
        prontuario.setDataProcedimento(dto.getDataProcedimento());
        prontuario.setLocalAtendimento(dto.getLocalAtendimento());
        prontuario.setPet(pet);
        prontuario.setVeterinario(veterinario);

        return toDTO(prontuarioRepository.save(prontuario));
    }

    @Transactional
    @CacheEvict(value = "prontuarios", allEntries = true)
    public void deletar(Long id) {
        prontuarioRepository.delete(buscarEntidade(id));
    }

    private Prontuario buscarEntidade(Long id) {
        return prontuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Prontuário não encontrado com id: " + id));
    }

    private ProntuarioResponseDTO toDTO(Prontuario prontuario) {
        return new ProntuarioResponseDTO(
                prontuario.getId(),
                prontuario.getProcedimento(),
                prontuario.getDataProcedimento(),
                prontuario.getLocalAtendimento(),
                prontuario.getPet().getId(),
                prontuario.getPet().getNome(),
                prontuario.getVeterinario().getId(),
                prontuario.getVeterinario().getNome()
        );
    }
}