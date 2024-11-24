CREATE TABLE comunicados (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  mensagem VARCHAR(255) NOT NULL,
  usuario_id BIGINT NOT NULL,
  condominio_id BIGINT NOT NULL,
  FOREIGN KEY (usuario_id) REFERENCES usuarios(id),
  FOREIGN KEY (condominio_id) REFERENCES condominios(id)
);