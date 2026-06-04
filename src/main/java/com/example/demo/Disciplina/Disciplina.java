package com.example.demo.Disciplina;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class Disciplina {

    private Integer idDisciplina;
    private String nomeDisciplina;
}
