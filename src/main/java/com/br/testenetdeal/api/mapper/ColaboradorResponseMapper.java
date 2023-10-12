package com.br.testenetdeal.api.mapper;

import com.br.testenetdeal.api.dto.ColaboradorResp;
import com.br.testenetdeal.domain.model.Colaborador;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ColaboradorResponseMapper {
    ColaboradorResp colaboradorToResp(Colaborador colaborador);
}
