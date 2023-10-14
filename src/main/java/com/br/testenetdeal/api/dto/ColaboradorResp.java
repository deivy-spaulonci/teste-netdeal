package com.br.testenetdeal.api.dto;

import com.br.testenetdeal.domain.model.Colaborador;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class ColaboradorResp {
    private Long id;
    private String nome;
    private Integer pontuacao;
    private Long colaboradorPai;
    private List<ColaboradorResp> subColaboradores;
}
