package org.fiap.challenge_clyvo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "responsavel")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Responsavel extends Usuario {
    @Column(name = "data_nasc")
    private LocalDate dataNascimento;

    @OneToMany(mappedBy = "responsavel", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Pet> pets = new ArrayList<>();
}