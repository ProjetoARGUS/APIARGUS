package com.argus.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.argus.api.domain.model.Notificacoes;

@Repository
public interface NotificacaoRepository extends JpaRepository<Notificacoes, Long> {

	// Busca as 10 últimas notificações
	List<Notificacoes> findTop10ByOrderByIdDesc();
}

