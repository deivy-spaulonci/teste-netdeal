package com.br.testenetdeal.domain.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
@Entity
@Table(name = "colaborador")
public class Colaborador implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    @Column(name = "nome")
    private String nome;

    @NotBlank(message = "Senha é obrigatória")
    @Column(name = "senha")
    private String senha;

    @JsonBackReference
    @Column(name = "pontuacao")
    private Integer pontuacao;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pai_id")
    private Colaborador colaboradorPai;

    @OneToMany(mappedBy = "colaboradorPai", cascade = CascadeType.ALL)
    private List<Colaborador> subColaboradores;

}