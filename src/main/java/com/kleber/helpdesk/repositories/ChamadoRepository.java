package com.kleber.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kleber.helpdesk.domain.Chamado;

public interface ChamadoRepository extends JpaRepository<Chamado, Integer>{
    
}
