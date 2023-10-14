package com.br.testenetdeal.api.mapper;

import com.br.testenetdeal.api.dto.ColaboradorResp;
import com.br.testenetdeal.domain.model.Colaborador;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ColaboradorRespMapper {

    @Mapping(target = "colaboradorPai", source = "colaboradorPai.id")
    ColaboradorResp colaboradorToResp(Colaborador colaborador);
}
