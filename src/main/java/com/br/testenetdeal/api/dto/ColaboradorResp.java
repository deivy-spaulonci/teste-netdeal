package com.br.testenetdeal.api.dto;

import lombok.Data;

import java.util.List;

@Data
public class ColaboradorResp {
    private Long id;
    private String nome;
    private Integer pontuacao;
    private List<ColaboradorResp> subColaboradores;
}
