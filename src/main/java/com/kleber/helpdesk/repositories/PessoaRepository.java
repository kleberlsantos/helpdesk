package com.kleber.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kleber.helpdesk.domain.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Integer>{
    
}
