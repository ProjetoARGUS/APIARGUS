CREATE TABLE reservas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    areas_comuns_id BIGINT NOT NULL,
    data_reserva DATE NOT NULL,
    hora_inicio TIME NOT NULL,
    hora_fim TIME NOT NULL,
    FOREIGN KEY (areas_comuns_id) REFERENCES areas_comuns(id)
);
