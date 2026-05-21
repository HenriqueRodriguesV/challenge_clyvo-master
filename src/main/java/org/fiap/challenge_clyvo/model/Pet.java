package org.fiap.challenge_clyvo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pet")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pet")
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(length = 200)
    private String descricao;

    @Column(length = 100)
    private String raca;

    @Column(name = "data_nasc")
    private LocalDate dataNascimento;

    @ManyToOne
    @JoinColumn(name = "id_resp", nullable = false)
    @JsonIgnoreProperties("pets")
    private Responsavel responsavel;

    @OneToMany(mappedBy = "pet", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("pet")
    private List<Prontuario> prontuarios = new ArrayList<>();
}