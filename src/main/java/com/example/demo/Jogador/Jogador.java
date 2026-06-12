package com.example.demo.Jogador;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class Jogador {
    private Integer idJogador;
    private String nome;
    private Integer numeroCamisa;
    private String posicao;
    private Integer idade;
    private Integer idSelecao;
}


