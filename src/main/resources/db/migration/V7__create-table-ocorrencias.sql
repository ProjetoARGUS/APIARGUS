-- V7__create-table-ocorrencias.sql
CREATE TABLE IF NOT EXISTS `ocorrencias` (
  `id` INT AUTO_INCREMENT PRIMARY KEY,
  `titulo` VARCHAR(100) NOT NULL,
  `descricao` TEXT NOT NULL,
  `tipo` ENUM('PROBLEMA_DE_INFRAESTRUTURA', 'DESENTENDIMENTO', 'SOLICITACAO_DE_MANUTENCAO', 'ASSEMBLEIA', 'DESVIO_DE_CONDUTA') NOT NULL,
  `status_aprovacao` ENUM('APROVADO', 'REJEITADO', 'AGUARDANDO') DEFAULT 'AGUARDANDO',
  `status_resolucao` ENUM('PENDENTE', 'EM_ANDAMENTO', 'CONCLUIDA') NOT NULL DEFAULT 'PENDENTE',
  `data_criacao` DATETIME NOT NULL,
  `id_usuario` INT NOT NULL,
  `id_area` INT,

  FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id`),
  FOREIGN KEY (`id_area`) REFERENCES `areas` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;