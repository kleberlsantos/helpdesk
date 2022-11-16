package com.kleber.helpdesk.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kleber.helpdesk.domain.Pessoa;
import com.kleber.helpdesk.domain.Tecnico;
import com.kleber.helpdesk.domain.dtos.TecnicoDTO;
import com.kleber.helpdesk.repositories.PessoaRepository;
import com.kleber.helpdesk.repositories.TecnicoRepository;
import com.kleber.helpdesk.services.exceptions.DataIntegrityViolationException;
import com.kleber.helpdesk.services.exceptions.ObjectnotFoundException;

@Service
public class TecnicoService {
    @Autowired
    private TecnicoRepository repository;

    @Autowired
    private PessoaRepository pessoaRepository;

    public Tecnico findById(Integer id) {
        Optional<Tecnico> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectnotFoundException("Objeto não encontrado! Id: " + id));
    }

    public List<Tecnico> findAll() {
        return repository.findAll();
    }

    public Tecnico create(TecnicoDTO objDTO) {
        objDTO.setId(null);
        validaPorCpfEEmail(objDTO);
        Tecnico newObj = new Tecnico(objDTO);
       return repository.save(newObj);
    }

    private void validaPorCpfEEmail(TecnicoDTO objDTO) {
        Optional<Pessoa> obj= pessoaRepository.findByCpf(objDTO.getCpf());
        if(obj.isPresent() && obj.get().getId()!=objDTO.getId()){
            throw new DataIntegrityViolationException("CPF já cadastrada no sistema!");
        }

        obj= pessoaRepository.findByEmail(objDTO.getEmail());
        if(obj.isPresent() && obj.get().getId()!=objDTO.getId()){
            throw new DataIntegrityViolationException("E-mail já cadastrado no sistema!");
        }
    }
}