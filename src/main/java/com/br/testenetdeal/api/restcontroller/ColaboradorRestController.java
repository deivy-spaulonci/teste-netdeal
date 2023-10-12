package com.br.testenetdeal.api.restcontroller;

import com.br.testenetdeal.api.dto.ColaboradorReq;
import com.br.testenetdeal.api.dto.ColaboradorResp;
import com.br.testenetdeal.api.mapper.ColaboradorReqMapper;
import com.br.testenetdeal.api.mapper.ColaboradorResponseMapper;
import com.br.testenetdeal.domain.model.Colaborador;
import com.br.testenetdeal.service.ColaboradorService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.Objects.isNull;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api/v1/colaborador")
public class ColaboradorRestController {

    @Autowired
    ColaboradorService colaboradorService;

    private final ColaboradorResponseMapper colaboradorRespMapper;
    private final ColaboradorReqMapper colaboradorReqMapper;

    /**
     * api devolve a lista de colaboradores pais ordenado ou nao //sort=id,desc
     * @param sort
     * @return List<ColaboradorResp>
     */
    @GetMapping()
    public ResponseEntity<List<ColaboradorResp>> getAllColaboradores(Sort sort) {
        List<Colaborador> persons = colaboradorService.getAllColaboradores(sort);
        List<ColaboradorResp> responseList = persons.stream().map(colaboradorRespMapper::colaboradorToResp).toList();
        return ResponseEntity.ok(responseList);
    }

    /**
     * busca o colaborador pelo id
     * @param id
     * @return ColaboradorResp
     */
    @GetMapping("/{id}")
    public ResponseEntity<ColaboradorResp> getColaboradorById(@PathVariable Long id) {
        ColaboradorResp response = colaboradorRespMapper.colaboradorToResp(colaboradorService.getColaboradorById(id));
        if (isNull(response))
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(response);
    }

    /**
     * cria um novo colaborador valido
     * @param colaboradorReq
     * @return ColaboradorResp
     */
    @PostMapping()
    public ResponseEntity<ColaboradorResp> createPerson(@RequestBody @Valid ColaboradorReq colaboradorReq) {
        Colaborador colaborador = colaboradorReqMapper.colaboradorReqToColaborador(colaboradorReq);
        ColaboradorResp response = colaboradorRespMapper.colaboradorToResp(colaboradorService.saveColaborador(colaborador));
        return ResponseEntity.ok(response);
    }

    /**
     * ataliza colaborador
     * @param id
     * @param colaboradorReq
     * @return ColaboradorResp
     */

    @PutMapping("/{id}")
    public ResponseEntity<ColaboradorResp> updatePerson(@PathVariable @NotNull Long id, @RequestBody @Valid ColaboradorReq colaboradorReq) {
        Colaborador colaborador = colaboradorReqMapper.colaboradorReqToColaborador(colaboradorReq);
        ColaboradorResp response = colaboradorRespMapper.colaboradorToResp(colaboradorService.updatePerson(id, colaborador));
        return ResponseEntity.ok(response);
    }

    /**
     * excluir um colaborador pelo id
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable @NotNull Long id) {
        colaboradorService.deletePerson(id);
        return ResponseEntity.ok().build();
    }
}
