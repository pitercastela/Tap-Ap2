package com.example.demo.Aluno;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class Aluno {

    private Integer matricula;
    private String nome;
    private String sexo;
    private LocalDate dataNascimento;
    private Integer idCurso;
}


