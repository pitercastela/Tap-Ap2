package com.example.demo.Curso;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class Curso {

    private Integer idCurso;
    private String nomeCurso;
}
