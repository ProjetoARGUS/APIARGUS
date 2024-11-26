CREATE TABLE sessao_votacao (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    proposta VARCHAR(255) NOT NULL,
    descricao VARCHAR(525) NOT NULL,
    data_inicio DATE NOT NULL,
    data_fim DATE NOT NULL,
    condominio_id BIGINT,
    FOREIGN KEY (condominio_id) REFERENCES condominios(id) ON DELETE CASCADE
);

