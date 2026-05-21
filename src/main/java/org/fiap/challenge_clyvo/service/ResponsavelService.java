package org.fiap.challenge_clyvo.service;

import org.fiap.challenge_clyvo.dto.ResponsavelDTO;
import org.fiap.challenge_clyvo.exception.BusinessException;
import org.fiap.challenge_clyvo.exception.ResourceNotFoundException;
import org.fiap.challenge_clyvo.model.Responsavel;
import org.fiap.challenge_clyvo.repository.ResponsavelRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ResponsavelService {

    private final ResponsavelRepository responsavelRepository;

    public ResponsavelService(ResponsavelRepository responsavelRepository) {
        this.responsavelRepository = responsavelRepository;
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "responsaveis", key = "#pageable.pageNumber + '-' + #pageable.pageSize + '-' + #pageable.sort")
    public Page<ResponsavelDTO> listarTodos(Pageable pageable) {
        return responsavelRepository.findAll(pageable).map(this::toDTO);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "responsaveis", key = "#id")
    public ResponsavelDTO buscarPorId(Long id) {
        return toDTO(buscarEntidade(id));
    }

    @Transactional(readOnly = true)
    public Page<ResponsavelDTO> buscarPorNome(String nome, Pageable pageable) {
        return responsavelRepository.findByNomeContainingIgnoreCase(nome, pageable).map(this::toDTO);
    }

    @Transactional
    @CacheEvict(value = "responsaveis", allEntries = true)
    public ResponsavelDTO salvar(ResponsavelDTO dto) {
        if (responsavelRepository.existsByCpf(dto.getCpf())) {
            throw new BusinessException("Já existe um responsável com este CPF");
        }
        if (responsavelRepository.existsByEmail(dto.getEmail())) {
            throw new BusinessException("Já existe um responsável com este email");
        }

        Responsavel responsavel = new Responsavel();
        responsavel.setNome(dto.getNome());
        responsavel.setEmail(dto.getEmail());
        responsavel.setCpf(dto.getCpf());
        responsavel.setDataNascimento(dto.getDataNascimento());

        return toDTO(responsavelRepository.save(responsavel));
    }

    @Transactional
    @CacheEvict(value = "responsaveis", allEntries = true)
    public ResponsavelDTO atualizar(Long id, ResponsavelDTO dto) {
        Responsavel responsavel = buscarEntidade(id);

        if (!responsavel.getCpf().equals(dto.getCpf()) &&
                responsavelRepository.existsByCpf(dto.getCpf())) {
            throw new BusinessException("Já existe um responsável com este CPF");
        }

        if (!responsavel.getEmail().equals(dto.getEmail()) &&
                responsavelRepository.existsByEmail(dto.getEmail())) {
            throw new BusinessException("Já existe um responsável com este email");
        }

        responsavel.setNome(dto.getNome());
        responsavel.setEmail(dto.getEmail());
        responsavel.setCpf(dto.getCpf());
        responsavel.setDataNascimento(dto.getDataNascimento());

        return toDTO(responsavelRepository.save(responsavel));
    }

    @Transactional
    @CacheEvict(value = "responsaveis", allEntries = true)
    public void deletar(Long id) {
        responsavelRepository.delete(buscarEntidade(id));
    }

    private Responsavel buscarEntidade(Long id) {
        return responsavelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Responsável não encontrado com id: " + id));
    }

    private ResponsavelDTO toDTO(Responsavel responsavel) {
        return new ResponsavelDTO(
                responsavel.getId(),
                responsavel.getNome(),
                responsavel.getEmail(),
                responsavel.getCpf(),
                responsavel.getDataNascimento()
        );
    }
}