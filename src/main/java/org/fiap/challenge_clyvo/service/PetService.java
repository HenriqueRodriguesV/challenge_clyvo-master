package org.fiap.challenge_clyvo.service;

import org.fiap.challenge_clyvo.dto.PetRequestDTO;
import org.fiap.challenge_clyvo.dto.PetResponseDTO;
import org.fiap.challenge_clyvo.exception.ResourceNotFoundException;
import org.fiap.challenge_clyvo.model.Pet;
import org.fiap.challenge_clyvo.model.Responsavel;
import org.fiap.challenge_clyvo.repository.PetRepository;
import org.fiap.challenge_clyvo.repository.ResponsavelRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PetService {
    private final PetRepository petRepository;
    private final ResponsavelRepository responsavelRepository;

    public PetService(PetRepository petRepository, ResponsavelRepository responsavelRepository) {
        this.petRepository = petRepository;
        this.responsavelRepository = responsavelRepository;
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "pets", key = "#pageable.pageNumber + '-' + #pageable.pageSize + '-' + #pageable.sort")
    public Page<PetResponseDTO> listarTodos(Pageable pageable) {
        return petRepository.findAll(pageable).map(this::toDTO);
    }

    @Cacheable(value = "pets", key = "#id")
    public PetResponseDTO buscarPorId(Long id) {
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pet não encontrado com id: " + id));
        return toDTO(pet);
    }

    public Page<PetResponseDTO> buscarPorNome(String nome, Pageable pageable) {
        return petRepository.findByNomeContainingIgnoreCase(nome, pageable).map(this::toDTO);
    }

    public Page<PetResponseDTO> buscarPorRaca(String raca, Pageable pageable) {
        return petRepository.findByRacaContainingIgnoreCase(raca, pageable).map(this::toDTO);
    }

    public Page<PetResponseDTO> buscarPorResponsavel(Long responsavelId, Pageable pageable) {
        return petRepository.findByResponsavelId(responsavelId, pageable).map(this::toDTO);
    }

    public Page<PetResponseDTO> buscarPorNomeERaca(String nome, String raca, Pageable pageable) {
        return petRepository.findByNomeContainingIgnoreCaseAndRacaContainingIgnoreCase(nome, raca, pageable)
                .map(this::toDTO);
    }

    @Transactional
    @CacheEvict(value = "pets", allEntries = true)
    public PetResponseDTO salvar(PetRequestDTO dto) {
        Responsavel responsavel = responsavelRepository.findById(dto.getResponsavelId())
                .orElseThrow(() -> new ResourceNotFoundException("Responsável não encontrado com id: " + dto.getResponsavelId()));

        Pet pet = new Pet();
        pet.setNome(dto.getNome());
        pet.setDescricao(dto.getDescricao());
        pet.setRaca(dto.getRaca());
        pet.setDataNascimento(dto.getDataNascimento());
        pet.setResponsavel(responsavel);

        return toDTO(petRepository.save(pet));
    }

    @Transactional
    @CacheEvict(value = "pets", allEntries = true)
    public PetResponseDTO atualizar(Long id, PetRequestDTO dto) {
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pet não encontrado com id: " + id));

        Responsavel responsavel = responsavelRepository.findById(dto.getResponsavelId())
                .orElseThrow(() -> new ResourceNotFoundException("Responsável não encontrado com id: " + dto.getResponsavelId()));

        pet.setNome(dto.getNome());
        pet.setDescricao(dto.getDescricao());
        pet.setRaca(dto.getRaca());
        pet.setDataNascimento(dto.getDataNascimento());
        pet.setResponsavel(responsavel);

        return toDTO(petRepository.save(pet));
    }

    @Transactional
    @CacheEvict(value = "pets", allEntries = true)
    public void deletar(Long id) {
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pet não encontrado com id: " + id));

        petRepository.delete(pet);
    }

    private PetResponseDTO toDTO(Pet pet) {
        return new PetResponseDTO(
                pet.getId(),
                pet.getNome(),
                pet.getDescricao(),
                pet.getRaca(),
                pet.getDataNascimento(),
                pet.getResponsavel().getId(),
                pet.getResponsavel().getNome()
        );
    }
}