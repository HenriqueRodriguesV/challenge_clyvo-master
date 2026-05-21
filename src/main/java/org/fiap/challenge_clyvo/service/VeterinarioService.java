package org.fiap.challenge_clyvo.service;

import org.fiap.challenge_clyvo.dto.VeterinarioDTO;
import org.fiap.challenge_clyvo.exception.BusinessException;
import org.fiap.challenge_clyvo.exception.ResourceNotFoundException;
import org.fiap.challenge_clyvo.model.Veterinario;
import org.fiap.challenge_clyvo.repository.VeterinarioRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VeterinarioService {
    private final VeterinarioRepository veterinarioRepository;

    public VeterinarioService(VeterinarioRepository veterinarioRepository) {
        this.veterinarioRepository = veterinarioRepository;
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "veterinarios", key = "#pageable.pageNumber + '-' + #pageable.pageSize + '-' + #pageable.sort")
    public Page<VeterinarioDTO> listarTodos(Pageable pageable) {
        return veterinarioRepository.findAll(pageable).map(this::toDTO);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "veterinarios", key = "#id")
    public VeterinarioDTO buscarPorId(Long id) {
        Veterinario veterinario = veterinarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Veterinário não encontrado com id: " + id));
        return toDTO(veterinario);
    }

    public Page<VeterinarioDTO> buscarPorNome(String nome, Pageable pageable) {
        return veterinarioRepository.findByNomeContainingIgnoreCase(nome, pageable).map(this::toDTO);
    }

    public Page<VeterinarioDTO> buscarPorCrmv(String crmv, Pageable pageable) {
        return veterinarioRepository.findByCrmvContainingIgnoreCase(crmv, pageable).map(this::toDTO);
    }

    @CacheEvict(value = "veterinarios", allEntries = true)
    public VeterinarioDTO salvar(VeterinarioDTO dto) {
        if (veterinarioRepository.existsByCpf(dto.getCpf())) {
            throw new BusinessException("Já existe um veterinário com este CPF");
        }
        if (veterinarioRepository.existsByEmail(dto.getEmail())) {
            throw new BusinessException("Já existe um veterinário com este email");
        }
        if (veterinarioRepository.existsByCrmv(dto.getCrmv())) {
            throw new BusinessException("Já existe um veterinário com este CRMV");
        }

        Veterinario veterinario = new Veterinario();
        veterinario.setNome(dto.getNome());
        veterinario.setEmail(dto.getEmail());
        veterinario.setCpf(dto.getCpf());
        veterinario.setCrmv(dto.getCrmv());

        return toDTO(veterinarioRepository.save(veterinario));
    }

    @Transactional
    @CacheEvict(value = "veterinarios", allEntries = true)
    public VeterinarioDTO atualizar(Long id, VeterinarioDTO dto) {
        Veterinario veterinario = veterinarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Veterinário não encontrado com id: " + id));

        if (!veterinario.getCpf().equals(dto.getCpf()) &&
                veterinarioRepository.existsByCpf(dto.getCpf())) {
            throw new BusinessException("Já existe um veterinário com este CPF");
        }

        if (!veterinario.getEmail().equals(dto.getEmail()) &&
                veterinarioRepository.existsByEmail(dto.getEmail())) {
            throw new BusinessException("Já existe um veterinário com este email");
        }

        if (!veterinario.getCrmv().equals(dto.getCrmv()) &&
                veterinarioRepository.existsByCrmv(dto.getCrmv())) {
            throw new BusinessException("Já existe um veterinário com este CRMV");
        }

        veterinario.setNome(dto.getNome());
        veterinario.setEmail(dto.getEmail());
        veterinario.setCpf(dto.getCpf());
        veterinario.setCrmv(dto.getCrmv());

        return toDTO(veterinarioRepository.save(veterinario));
    }

    @CacheEvict(value = "veterinarios", allEntries = true)
    public void deletar(Long id) {
        Veterinario veterinario = veterinarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Veterinário não encontrado com id: " + id));

        veterinarioRepository.delete(veterinario);
    }

    private VeterinarioDTO toDTO(Veterinario veterinario) {
        return new VeterinarioDTO(
                veterinario.getId(),
                veterinario.getNome(),
                veterinario.getEmail(),
                veterinario.getCpf(),
                veterinario.getCrmv()
        );
    }
}