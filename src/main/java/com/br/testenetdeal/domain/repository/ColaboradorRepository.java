package com.br.testenetdeal.domain.repository;

import com.br.testenetdeal.domain.model.Colaborador;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ColaboradorRepository extends JpaRepository<Colaborador, Long> {

    List<Colaborador> findByColaboradorPaiIsNull(Sort sort);

//    @Query(value = "SELECT c FROM Colaborador c ORDER BY c.nome")
//    List<Colaborador> findIdNomeColaborador();

}
