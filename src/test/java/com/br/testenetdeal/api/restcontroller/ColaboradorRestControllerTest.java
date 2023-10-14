package com.br.testenetdeal.api.restcontroller;

import com.br.testenetdeal.api.dto.ColaboradorReq;
import com.br.testenetdeal.api.dto.ColaboradorResp;
import com.br.testenetdeal.api.mapper.ColaboradorReqMapper;
import com.br.testenetdeal.api.mapper.ColaboradorRespMapper;
import com.br.testenetdeal.domain.model.Colaborador;
import com.br.testenetdeal.service.ColaboradorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ColaboradorRestControllerTest {

    @InjectMocks
    private ColaboradorRestController colaboradorRestController;
    @Mock
    private ColaboradorService colaboradorService;
    @Mock
    private ColaboradorRespMapper colaboradorRespMapper;
    @Mock
    private ColaboradorReqMapper colaboradorReqMapper;

    private final String _JOAO = "João";
    private final String _FULANO = "Fulano de tal";
    private final String _SENHA = "senha123456";
    private Colaborador col1;
    private Colaborador col2;

    private ColaboradorReq colReq1;
    private ColaboradorReq colReq2;
    private ColaboradorResp colResp1;

    @BeforeEach
    void setUp() {
        this.col1 = Colaborador.builder().id(1L).nome(_JOAO).senha(_SENHA).pontuacao(25).colaboradorPai(null).subColaboradores(null).build();
        this.col2 = Colaborador.builder().id(2L).nome(_FULANO).senha(_SENHA).pontuacao(50).colaboradorPai(null).subColaboradores(null).build();

        this.colReq1 = ColaboradorReq.builder().id(1L).nome(_JOAO).senha(_SENHA).colaboradorPai(null).build();
        this.colReq2 = ColaboradorReq.builder().id(2L).nome(_FULANO).senha(_SENHA).colaboradorPai(null).build();

        this.colResp1 = ColaboradorResp.builder().id(1L).nome(_JOAO).pontuacao(25).colaboradorPai(null).build();
    }

    @Test
    void testGetAllColaboradoresReturnsListOfColaboradorResp() {
        List<Colaborador> colaboradores = new ArrayList<>();

        colaboradores.add(col1);
        colaboradores.add(col2);

        ColaboradorResp colResp1 = ColaboradorResp.builder().id(1L).nome(_JOAO).colaboradorPai(null).build();
        ColaboradorResp colResp2 = ColaboradorResp.builder().id(2L).nome(_FULANO).colaboradorPai(null).build();

        when(colaboradorService.getAllColaboradores(Sort.by("id"))).thenReturn(colaboradores);
        when(colaboradorRespMapper.colaboradorToResp(colaboradores.get(0))).thenReturn(colResp1);
        when(colaboradorRespMapper.colaboradorToResp(colaboradores.get(1))).thenReturn(colResp2);

        ResponseEntity<List<ColaboradorResp>> response = colaboradorRestController.getAllColaboradores(Sort.by("id"));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        assertEquals(_JOAO, response.getBody().get(0).getNome());
        assertEquals(_FULANO, response.getBody().get(1).getNome());
    }

    @Test
    void testGetColaboradorByIdReturnsColaboradorResponse() {
        Long id = 1L;

        when(colaboradorService.getColaboradorById(id)).thenReturn(this.col1);
        when(colaboradorRespMapper.colaboradorToResp(this.col1)).thenReturn(this.colResp1);

        ResponseEntity<ColaboradorResp> response = colaboradorRestController.getColaboradorById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(_JOAO, response.getBody().getNome());
    }

    @Test
    void testSaveColaboradorReturnsPersonResponse() {
        when(colaboradorRespMapper.colaboradorToResp(this.col1)).thenReturn(this.colResp1);
        when(colaboradorService.saveColaborador(this.col1)).thenReturn(this.col1);
        when(colaboradorRespMapper.colaboradorToResp(this.col1)).thenReturn(colResp1);

        ResponseEntity<ColaboradorResp> response = colaboradorRestController.saveColaborador(this.colReq1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(_JOAO, response.getBody().getNome());
    }

    @Test
    void testGetAllColaboradoresReturnsEmptyList() {
        List<Colaborador> persons = new ArrayList<>();
        when(colaboradorService.getAllColaboradores(Sort.by("nome"))).thenReturn(persons);
        ResponseEntity<List<ColaboradorResp>> response = colaboradorRestController.getAllColaboradores(Sort.by("nome").ascending());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isEmpty());
    }

    @Test
    void testGetColaboradorByIdReturnsNotFound() {
        Long id = 1L;
        when(colaboradorService.getColaboradorById(id)).thenReturn(null);
        when(colaboradorRespMapper.colaboradorToResp(null)).thenReturn(null);
        ResponseEntity<ColaboradorResp> response = colaboradorRestController.getColaboradorById(id);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

//

//
//    @Test
//    void test_update_person_name_only() {
//        PersonRequest personRequest = new PersonRequest();
//        personRequest.setName("John Doe Updated");
//        personRequest.setPassword("MuitoComplicado@345@12");
//
//        PersonResponse personResponse = new PersonResponse();
//        personResponse.setId(1L);
//        personResponse.setName("John Doe Updated");
//        personResponse.setScore(100);
//
//        Person updatedPerson = new Person();
//        updatedPerson.setId(1L);
//        updatedPerson.setName("John Doe Updated");
//        updatedPerson.setPassword("MuitoComplicado@345@12");
//
//        Mockito.when(personRequestMapper.personRequestToPerson(personRequest)).thenReturn(updatedPerson);
//
//        Mockito.when(personService.updatePerson(1L, updatedPerson)).thenReturn(updatedPerson);
//
//        Mockito.when(personResponseMapper.personToPersonResponse(updatedPerson)).thenReturn(personResponse);
//
//        ResponseEntity<PersonResponse> response = personController.updatePerson(1L, personRequest);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(personResponse, response.getBody());
//    }

    @Test
    void test_Ok_delete_Colaborador_with_valid_id() {
        ResponseEntity<Void> response = colaboradorRestController.deleteColaborador(1L);

        verify(colaboradorService,  times(1)).deleteColaborador(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

}
