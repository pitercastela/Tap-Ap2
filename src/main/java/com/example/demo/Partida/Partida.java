package com.example.demo.Partida;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class Partida {
    private Integer idPartida;
    private LocalDate dataPartida;
    private String estadio;
    private String faseCompeticao;
    private String placar;
}
