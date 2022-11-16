package com.kleber.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kleber.helpdesk.domain.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer>{
    
}
