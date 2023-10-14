package com.br.testenetdeal.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ColaboradorReq {
    private Long id;
    @NotBlank
    private String nome;
    @NotBlank
    private String senha;
    @Positive
    private Long colaboradorPai;
}
