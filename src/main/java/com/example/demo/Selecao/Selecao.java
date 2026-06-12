package com.example.demo.Selecao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class Selecao {
    private Integer idSelecao;
    private String nomePais;
    private String tecnico;
    private Integer rankingFifa;
}