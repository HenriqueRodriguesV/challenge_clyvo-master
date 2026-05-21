package org.fiap.challenge_clyvo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "med_vet")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Veterinario extends Usuario {
    @Column(nullable = false, unique = true, length = 20)
    private String crmv;

    @OneToMany(mappedBy = "veterinario", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("veterinario")
    private List<Prontuario> prontuarios = new ArrayList<>();
}