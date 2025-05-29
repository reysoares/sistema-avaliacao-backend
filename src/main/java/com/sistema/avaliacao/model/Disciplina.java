package com.sistema.avaliacao.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Disciplina {

    @Id
    private String codigo;
    private String nome;
    private String curso;
    private String semestre;
    @ManyToOne
    @JoinColumn(name = "professor_disciplina")
    private Professor professor;
    private int cargaHoraria;

}
