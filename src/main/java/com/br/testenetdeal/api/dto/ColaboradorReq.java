package com.br.testenetdeal.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Data
@Builder
public class ColaboradorReq {
    @NotBlank
    private String nome;
    @NotBlank
    private String senha;
    @Positive
    private Long colaboradorPai;
}
