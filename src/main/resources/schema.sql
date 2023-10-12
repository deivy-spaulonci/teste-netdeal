DROP TABLE colaborador;

CREATE TABLE IF NOT EXISTS colaborador (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(255) NOT NULL,
    senha VARCHAR(255) NOT NULL,
    pontuacao INT NOT NULL,
    pai_id BIGINT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_person_parent FOREIGN KEY (pai_id) REFERENCES colaborador(id) ON DELETE CASCADE
);

INSERT INTO colaborador (nome, senha, pontuacao) VALUES ( "FULANO 1", "1234", 0);
INSERT INTO colaborador (nome, senha, pontuacao) VALUES ( "FULANO 2", "3333", 0);
INSERT INTO colaborador (nome, senha, pontuacao) VALUES ( "FULANO 3", "2222", 0);