package com.br.testenetdeal.service;

import com.br.testenetdeal.domain.model.Colaborador;
import com.br.testenetdeal.domain.repository.ColaboradorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ColaboradorServiceTest {

    @InjectMocks
    private ColaboradorService colaboradorService;

    @Mock
    private ColaboradorRepository colaboradorRepository;

    private Colaborador col1;
    private Colaborador col2;

    @BeforeEach
    void setUp() {
        Colaborador.ColaboradorBuilder colaborador1 = Colaborador.builder();
        colaborador1.id(1L);
        colaborador1.nome("John");
        colaborador1.senha("password");
        colaborador1.pontuacao(100);
        colaborador1.colaboradorPai(null);
        this.col1 = colaborador1.build();

        Colaborador.ColaboradorBuilder colaborador2 = Colaborador.builder();
        colaborador2.id(2L);
        colaborador2.nome("Jane");
        colaborador2.senha("password");
        colaborador2.pontuacao(90);
        colaborador2.colaboradorPai(null);
        this.col2 = colaborador2.build();
    }


    @Test
    void testGetAllColaboradorReturnsListColaboradorsWithParentColaboradorNull() {
        List<Colaborador> colaboradoresEsperados = new ArrayList<>();
        colaboradoresEsperados.add(this.col1);
        colaboradoresEsperados.add(this.col2);

        when(colaboradorRepository.findByColaboradorPaiIsNull(Sort.by("id"))).thenReturn(colaboradoresEsperados);
        List<Colaborador> colaboradors = colaboradorService.getAllColaboradores(Sort.by("id"));
        assertEquals(colaboradoresEsperados, colaboradors);
    }

    @Test
    void testGetColaboradorByIdReturnsColaboradorWithGivenId() {
        Long id = 1L;
        when(colaboradorRepository.findById(id)).thenReturn(Optional.of(this.col1));
        Colaborador colaboradorDB = colaboradorService.getColaboradorById(id);
        assertEquals(this.col1, colaboradorDB);
    }

    @Test
    void testCreateColaboradorSavesNewColaboradorDatabase() {
        Colaborador.ColaboradorBuilder colaboradorSalvo = Colaborador.builder();
        colaboradorSalvo.id(1L);
        colaboradorSalvo.nome("John");
        colaboradorSalvo.senha("encoded_password");
        colaboradorSalvo.pontuacao(100);
        colaboradorSalvo.colaboradorPai(null);

        when(colaboradorRepository.save(this.col1)).thenReturn(colaboradorSalvo.build());

        Colaborador colaboradorDB = colaboradorService.saveColaborador(this.col1);

        assertEquals(colaboradorSalvo.build(), colaboradorDB);
    }

    @Test
    void testColaboradorByIdThrowsEntityNotFoundExceptionWhenColaboradorWithGivenIdNotExist() {
        Long id = 1L;
        when(colaboradorRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> colaboradorService.getColaboradorById(id));
    }

    @Test
    void testUpdateColaboradorUpdatesExistingColaborador() {
        Colaborador.ColaboradorBuilder colaboradorEditado = Colaborador.builder();
        colaboradorEditado.id(1L);
        colaboradorEditado.nome("John Doe");
        colaboradorEditado.senha("newpassword");
        colaboradorEditado.pontuacao(0);

        when(colaboradorRepository.findById(1L)).thenReturn(Optional.of(this.col1));
        when(colaboradorRepository.save(this.col1)).thenReturn(colaboradorEditado.build());

        Colaborador result = colaboradorService.updateColaborador(1L, colaboradorEditado.build());
        verify(colaboradorRepository).save(colaboradorEditado.build());
        assertEquals(colaboradorEditado.build(), result);
    }

    @Test
    void testDeleteColaboradorDeleteExistiColaborador() {
        Long id = 1L;
        when(colaboradorRepository.findById(id)).thenReturn(Optional.of(this.col1));
        colaboradorService.deleteColaborador(id);
        verify(colaboradorRepository).delete(this.col1);
    }
}
