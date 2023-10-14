package com.br.testenetdeal.service;

import com.br.testenetdeal.domain.model.Colaborador;
import com.br.testenetdeal.domain.repository.ColaboradorRepository;
import com.br.testenetdeal.util.Encriptador;
import com.br.testenetdeal.util.PasswordHandler;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Predicate;

import static java.util.Objects.isNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class ColaboradorService {

    @Autowired
    private ColaboradorRepository colaboradorRepository;

    /**
     * lista colaboradores por filtro e ordenado
     * @param sort
     * @return List<Despesa>
     */
    public List<Colaborador> getAllColaboradores(Sort sort) {
        List<Colaborador> colaboradorList = colaboradorRepository.findByColaboradorPaiIsNull(sort);
        return colaboradorList;
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

    /**
     * salva o colaborador
     * @param colaborador
     * @return Colaborador
     */
    public Colaborador saveColaborador(Colaborador colaborador) {
        colaborador.setPontuacao(calculaPontuacaoSenha(colaborador.getSenha()));
        colaborador.setSenha(Encriptador.encode(colaborador.getSenha()));
        return colaboradorRepository.save(colaborador);
    }

    /**
     * atualiza colaborador
     * @param id
     * @param novoColaborador
     * @return Colaborador
     */
    public Colaborador updateColaborador(Long id, Colaborador novoColaborador) {
        Colaborador dbColaborador = getColaboradorById(id);
        dbColaborador.setPontuacao(calculaPontuacaoSenha(novoColaborador.getSenha()));
        dbColaborador.setNome(novoColaborador.getNome());
        dbColaborador.setSenha(Encriptador.encode(novoColaborador.getSenha()));

        Colaborador paiOld = dbColaborador.getColaboradorPai();
//        if(isNull(dbColaborador.getColaboradorPai())){
//            Colaborador colaboradorPaiDb  = colaboradorRepository.findById(novoColaborador.getColaboradorPai().getId()).get();
//            colaboradorPaiDb.setColaboradorPai(null);
//            colaboradorRepository.save(colaboradorPaiDb);
//        }

        dbColaborador.setColaboradorPai(novoColaborador.getColaboradorPai());

        if(!isNull(dbColaborador.getSubColaboradores())
            && !dbColaborador.getSubColaboradores().isEmpty()){
            buscarRescursivaElementoFilho(dbColaborador, novoColaborador.getColaboradorPai().getId());

            Colaborador superior  = colaboradorRepository.findById(novoColaborador.getColaboradorPai().getId()).get();
            superior.setColaboradorPai(paiOld);
            colaboradorRepository.save(superior);

        }
        return colaboradorRepository.save(dbColaborador);
    }

    /**
     * no caso de editar e colocar superior como inferior
     * eu removo (busca recursiva) o superior se ele exisitir em na estrutura como filho
     * "seto o pai" desse item que a acabei de retirar com o pai de quem estou editando
     * @param col
     * @param id
     */
    public void buscarRescursivaElementoFilho(Colaborador col, Long id){
        col.getSubColaboradores().forEach(colaborador -> {

            if(!colaborador.getSubColaboradores().isEmpty()){

                Predicate<Colaborador> isEqual = colab -> colab.getId().equals(id);
                colaborador.getSubColaboradores().removeIf(isEqual);
                colaboradorRepository.save(colaborador);
                buscarRescursivaElementoFilho(colaborador, id);
            }
        });
    }

    /**
     * excluir o colaborador
     * @param id
     */
    public void deleteColaborador(Long id) {
        Colaborador dbColaborador = getColaboradorById(id);
        colaboradorRepository.delete(dbColaborador);
    }

    /**
     * calcula a força(puntuação) da senha
     * @param senha
     * @return int
     */
    private int calculaPontuacaoSenha(String senha) {
        PasswordHandler passwordHandler = new PasswordHandler();
        return passwordHandler.getScore(senha);
    }
}
