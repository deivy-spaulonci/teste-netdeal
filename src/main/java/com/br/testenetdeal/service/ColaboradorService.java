package com.br.testenetdeal.service;

import com.br.testenetdeal.domain.model.Colaborador;
import com.br.testenetdeal.domain.repository.ColaboradorRepository;
import com.br.testenetdeal.util.Encriptador;
import com.br.testenetdeal.util.PasswordHandler;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ColaboradorService {

    @Autowired
    ColaboradorRepository colaboradorRepository;

    /**
     * lista colaboradores por filtro e ordenado
     * @param sort
     * @return List<Despesa>
     */
    public List<Colaborador> getAllColaboradores(Sort sort) {
        List<Colaborador> personList = colaboradorRepository.findByColaboradorPaiIsNull(sort);
        return personList;
    }

    /**
     * busca colaborador por id
     * @param id
     * @return Colaborador
     */
    public Colaborador getColaboradorById(Long id) {
        Colaborador colaborador = colaboradorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Colaborador não encontrado com esse id: " + id));
        return colaborador;
    }

    public Colaborador saveColaborador(Colaborador colaborador) {
        colaborador.setPontuacao(calculatePasswordScore(colaborador.getSenha()));
        colaborador.setSenha(Encriptador.encode(colaborador.getSenha()));
        Colaborador saved = colaboradorRepository.save(colaborador);
        return saved;
    }

    public Colaborador updatePerson(Long id, Colaborador novoColaborador) {
        Colaborador dbColaborador = getColaboradorById(id);
        dbColaborador.setPontuacao(calculatePasswordScore(novoColaborador.getSenha()));
        dbColaborador.setSenha(Encriptador.encode(novoColaborador.getSenha()));
        dbColaborador.setColaboradorPai(novoColaborador.getColaboradorPai());
        Colaborador result = colaboradorRepository.save(novoColaborador);
        return result;
    }

    public void deletePerson(Long id) {
        Colaborador dbColaborador = getColaboradorById(id);
        colaboradorRepository.delete(dbColaborador);
    }

    private int calculatePasswordScore(String password) {
        PasswordHandler passwordHandler = new PasswordHandler();
        return passwordHandler.getScore(password);
    }
}
