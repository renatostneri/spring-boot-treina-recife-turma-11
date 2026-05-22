CREATE TABLE IF NOT EXISTS tarefas (
    idtarefas INT NOT NULL AUTO_INCREMENT,
    titulo VARCHAR(255) NOT NULL,
    descricao VARCHAR(255) NULL,
    data_criacao DATE NOT NULL,
    data_conclusao DATE NULL,
    prioridade VARCHAR(10) NOT NULL,
    status VARCHAR(10) NOT NULL,
    idprojetos INT NULL,
    idusuarios INT NULL,
    PRIMARY KEY (idtarefas),
    FOREIGN KEY (idprojetos) REFERENCES projetos(idprojetos),
    FOREIGN KEY (idusuarios) REFERENCES usuarios(idusuarios)
    );
CREATE INDEX idprojetos ON tarefas(idprojetos);
CREATE INDEX idusuarios ON tarefas(idusuarios);