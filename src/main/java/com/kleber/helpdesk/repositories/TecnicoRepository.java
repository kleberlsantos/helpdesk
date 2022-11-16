package com.kleber.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kleber.helpdesk.domain.Tecnico;

public interface TecnicoRepository extends JpaRepository<Tecnico, Integer>{
    
}
