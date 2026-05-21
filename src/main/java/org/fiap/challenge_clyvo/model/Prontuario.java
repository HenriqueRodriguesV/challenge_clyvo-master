package org.fiap.challenge_clyvo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "prontuario")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Prontuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_prontuario")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_pet", nullable = false)
    @JsonIgnoreProperties({"prontuarios", "responsavel"})
    private Pet pet;

    @Column(nullable = false, length = 200)
    private String procedimento;

    @Column(name = "data_procedimento", nullable = false)
    private LocalDate dataProcedimento;

    @Column(name = "local_atendimento", nullable = false, length = 100)
    private String localAtendimento;

    @ManyToOne
    @JoinColumn(name = "id_med_vet", nullable = false)
    @JsonIgnoreProperties({"pets", "prontuarios"})
    private Veterinario veterinario;
}