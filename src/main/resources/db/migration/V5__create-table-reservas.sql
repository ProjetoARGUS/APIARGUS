CREATE TABLE reservas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    areas_comuns_id BIGINT NOT NULL,
    data_reserva DATE NOT NULL,
    FOREIGN KEY (areas_comuns_id) REFERENCES areas_comuns(id)
);