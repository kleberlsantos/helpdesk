package com.kleber.helpdesk;

import java.lang.Thread.State;
import java.lang.reflect.Array;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.kleber.helpdesk.domain.Chamado;
import com.kleber.helpdesk.domain.Cliente;
import com.kleber.helpdesk.domain.Tecnico;
import com.kleber.helpdesk.domain.enums.Perfil;
import com.kleber.helpdesk.domain.enums.Prioridade;
import com.kleber.helpdesk.domain.enums.Status;
import com.kleber.helpdesk.repositories.ChamadoRepository;
import com.kleber.helpdesk.repositories.ClienteRepository;
import com.kleber.helpdesk.repositories.TecnicoRepository;

@SpringBootApplication
public class HelpdeskApplication implements CommandLineRunner{

	@Autowired
	private TecnicoRepository tecnicoRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private ChamadoRepository chamadoRepository;

	public static void main(String[] args) {
		SpringApplication.run(HelpdeskApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Tecnico tec1 = new Tecnico(null, "Tecnico 1", "123456", "tecnico1@mail.com", "123");
		tec1.addPerfis(Perfil.ADMIN);

		Cliente cli1 = new Cliente(null, "Cliente 1", "223456", "cliente1@mail.com", "123");
		
		Chamado c1 = new Chamado(null, Prioridade.MEDIA, Status.ANDAMENTO, "Chamado 01", "Primeiro chamado", cli1, tec1);
		
		tecnicoRepository.saveAll(Arrays.asList(tec1));
		clienteRepository.saveAll(Arrays.asList(cli1));
		chamadoRepository.saveAll(Arrays.asList(c1));
	}

}
