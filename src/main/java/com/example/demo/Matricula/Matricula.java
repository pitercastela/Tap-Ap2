package com.example.demo.Matricula;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class Matricula {
    private Integer matriculaAluno;
    private Integer idDisciplina;
}