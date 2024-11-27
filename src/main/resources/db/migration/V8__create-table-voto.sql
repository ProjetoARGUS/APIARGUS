CREATE TABLE voto (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    voto BOOLEAN NOT NULL,
    sessao_votacao_id BIGINT NOT NULL,
    usuario_id BIGINT NOT NULL,
    CONSTRAINT fk_sessao_votacao FOREIGN KEY (sessao_votacao_id) REFERENCES sessao_votacao(id),
    CONSTRAINT fk_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);
