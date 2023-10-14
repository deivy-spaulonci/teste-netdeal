package com.br.testenetdeal.api.mapper;

import com.br.testenetdeal.api.dto.ColaboradorReq;
import com.br.testenetdeal.domain.model.Colaborador;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ColaboradorReqMapper {
    Colaborador colaboradorReqToColaborador(ColaboradorReq colaboradorReq);
    Colaborador idToColaborador(Long id);
}
