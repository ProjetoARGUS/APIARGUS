CREATE TABLE notificacoes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    corpo_de_texto TEXT NOT NULL,   
    data DATE NOT NULL,
    id_comunicados BIGINT,
    id_usuarios BIGINT,
    id_reserva BIGINT,
    CONSTRAINT fk_comunicados FOREIGN KEY (id_comunicados) REFERENCES comunicados(id),
    CONSTRAINT fk_usuarios FOREIGN KEY (id_usuarios) REFERENCES usuarios(id),
    CONSTRAINT fk_reservas FOREIGN KEY (id_reserva) REFERENCES reservas(id)
);
