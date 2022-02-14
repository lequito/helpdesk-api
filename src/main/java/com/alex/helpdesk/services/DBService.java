package com.alex.helpdesk.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.alex.helpdesk.domain.Chamado;
import com.alex.helpdesk.domain.Cliente;
import com.alex.helpdesk.domain.Tecnico;
import com.alex.helpdesk.domain.enums.Perfil;
import com.alex.helpdesk.domain.enums.Prioridade;
import com.alex.helpdesk.domain.enums.Status;
import com.alex.helpdesk.repositories.ChamadoRepository;
import com.alex.helpdesk.repositories.ClienteRepository;
import com.alex.helpdesk.repositories.TecnicoRepository;

@Service
public class DBService {

	@Autowired
	private TecnicoRepository tecnicoRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private ChamadoRepository chamadoRepository;
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	public void instanciaDB() {
		Tecnico t1 = new Tecnico(null, "Alex Dias", "944.074.550-98", "tec1@mail.com", encoder.encode("123"));
		t1.addPerfil(Perfil.ADMIN);
		
		Tecnico t2 = new Tecnico(null, "Jair", "815.579.920-45", "tec2@mail.com", encoder.encode("123"));
		t2.addPerfil(Perfil.TECNICO);
		
		Tecnico t3 = new Tecnico(null, "Juninho play", "995.218.470-00", "te32@mail.com", encoder.encode("123"));
		t3.addPerfil(Perfil.TECNICO);
		
		Cliente c1 = new Cliente(null, "Marcelo Neto", "730.814.080-67", "mn@mail.com", encoder.encode("123"));
		Cliente c2 = new Cliente(null, "Liz Regina", "645.779.260-08", "liz@mail.com", encoder.encode("123"));
		Cliente c3 = new Cliente(null, "katia regina", "935.235.390-05", "kr@mail.com", encoder.encode("123"));
		Cliente c4 = new Cliente(null, "Linus torvards", "904.591.260-08", "lt@mail.com", encoder.encode("123"));
		
		Chamado chamado1 = new Chamado(null, Prioridade.MEDIA, Status.ANDAMENTO, "chamado 1", "novo chamado cliente 1", t1, c1);
		Chamado chamado2 = new Chamado(null, Prioridade.BAIXA, Status.ABERTO, "chamado 2", "novo chamado cliente 2", t2, c2);
		Chamado chamado3 = new Chamado(null, Prioridade.MEDIA, Status.ANDAMENTO, "chamado 3", "novo chamado cliente 3", t1, c3);
		Chamado chamado4 = new Chamado(null, Prioridade.ALTA, Status.ANDAMENTO, "chamado 4", "novo chamado cliente 4", t2, c4);
		
		tecnicoRepository.saveAll(Arrays.asList(t1, t2, t3));
		clienteRepository.saveAll(Arrays.asList(c1, c2, c3, c4));
		chamadoRepository.saveAll(Arrays.asList(chamado1,chamado2,chamado3, chamado4));
	}
}
