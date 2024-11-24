CREATE TABLE areas_comuns (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    disponivel BOOLEAN NOT NULL DEFAULT FALSE,
    condominio_id BIGINT NOT NULL,
    FOREIGN KEY (condominio_id) REFERENCES condominios(id)
);