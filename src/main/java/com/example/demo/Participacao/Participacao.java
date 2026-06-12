package com.example.demo.Participacao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class Participacao {
    private Integer idSelecao;
    private Integer idPartida;
}