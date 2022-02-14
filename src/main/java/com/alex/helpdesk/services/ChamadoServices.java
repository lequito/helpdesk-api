package com.alex.helpdesk.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alex.helpdesk.domain.Chamado;
import com.alex.helpdesk.domain.Cliente;
import com.alex.helpdesk.domain.Tecnico;
import com.alex.helpdesk.domain.dtos.ChamadoDTO;
import com.alex.helpdesk.domain.enums.Prioridade;
import com.alex.helpdesk.domain.enums.Status;
import com.alex.helpdesk.repositories.ChamadoRepository;
import com.alex.helpdesk.services.exceptions.ObjectNotFoundException;

@Service
public class ChamadoServices {

	@Autowired
	private ChamadoRepository repository;
	
	@Autowired
	private TecnicoServices tecnicoServices;
	
	@Autowired
	private ClienteServices clienteServices;
	
	public Chamado findById(Integer id) {
		Optional<Chamado> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Chamado não encontrado ID: " + id));
	}

	public List<Chamado> findAll() {
		return repository.findAll();
	}

	public Chamado create(@Valid ChamadoDTO objDTO) {
		return repository.save(newChamado(objDTO));
	}
	
	public Chamado update(Integer id, @Valid ChamadoDTO objDto) {
		objDto.setId(id);
		Chamado oldObj = findById(id);
		oldObj = newChamado(objDto);
		return repository.save(oldObj);
	}
	
	private Chamado newChamado(ChamadoDTO obj){
		Tecnico tecnico = tecnicoServices.findById(obj.getTecnico());
		Cliente cliente = clienteServices.findById(obj.getCliente());

		Chamado chamado = new Chamado();
		
		if(obj.getId() != null) {
			chamado.setId(obj.getId());
		}
		
		if(obj.getStatus().equals(2)){
			chamado.setDataFechamento(LocalDate.now());
		}
		
		chamado.setTecnico(tecnico);
		chamado.setCliente(cliente);
		chamado.setPrioridade(Prioridade.toEnum(obj.getPrioridade()));
		chamado.setStatus(Status.toEnum(obj.getStatus()));
		chamado.setTitulo(obj.getTitulo());
		chamado.setObservacao(obj.getObservacao());
		return chamado;
	}
	
}








