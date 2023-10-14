package com.br.testenetdeal.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ColaboradorReq {
    private Long id;
    @NotBlank
    @Size(min = 1, max = 100)
    private String nome;
    @NotBlank
    @Size(min = 8, max = 100)
    private String senha;
    @Positive
    private Long colaboradorPai;
}
