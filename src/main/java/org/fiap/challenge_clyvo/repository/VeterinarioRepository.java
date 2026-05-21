package org.fiap.challenge_clyvo.repository;

import org.fiap.challenge_clyvo.model.Veterinario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VeterinarioRepository extends JpaRepository<Veterinario, Long> {
    Page<Veterinario> findByNomeContainingIgnoreCase(String nome, Pageable pageable);

    Page<Veterinario> findByCrmvContainingIgnoreCase(String crmv, Pageable pageable);

    boolean existsByCpf(String cpf);

    boolean existsByEmail(String email);

    boolean existsByCrmv(String crmv);
}