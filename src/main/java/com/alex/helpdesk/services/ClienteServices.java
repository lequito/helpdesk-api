package com.alex.helpdesk.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.alex.helpdesk.domain.Cliente;
import com.alex.helpdesk.domain.Pessoa;
import com.alex.helpdesk.domain.dtos.ClienteDTO;
import com.alex.helpdesk.repositories.ClienteRepository;
import com.alex.helpdesk.repositories.PessoaRepository;
import com.alex.helpdesk.services.exceptions.DataIntegrityViolationExcepiton;
import com.alex.helpdesk.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteServices {

	
	@Autowired
	private ClienteRepository repository;
	@Autowired
	private PessoaRepository pessoaRepository;
	@Autowired
	private BCryptPasswordEncoder encoder;
	

	public Cliente findById(Integer id) {
		Optional<Cliente> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado ID: " + id));
	}

	public List<Cliente> findAll() {
		return repository.findAll();
	}

	public Cliente create(ClienteDTO objDTO) {
		objDTO.setId(null);
		objDTO.setSenha(encoder.encode(objDTO.getSenha()));
		Cliente newObj = new Cliente(objDTO);
		validaPorCpfEemail(objDTO);
		return repository.save(newObj);
	}
	
	public Cliente update(Integer id, @Valid ClienteDTO objDTO) {
		objDTO.setId(id);
		Cliente oldObj = findById(id);
		validaPorCpfEemail(objDTO);
		oldObj = new Cliente(objDTO);
		return repository.save(oldObj);
	}
	
	public void delete (Integer id) {
		Cliente obj = findById(id);
		if(obj.getChamados().size() > 0){
			throw new DataIntegrityViolationExcepiton("Técnico não pode ser deletado, pois possui chamados atribuidos ");
		}
		repository.deleteById(id);
	}

	private void validaPorCpfEemail(ClienteDTO objDTO) {
		
		Optional<Pessoa> obj = pessoaRepository.findByCpf(objDTO.getCpf());
		if(obj.isPresent() && obj.get().getId() != objDTO.getId()) {
			throw new DataIntegrityViolationExcepiton("CPF Já existe para outro usuário");
		}
		obj = pessoaRepository.findByEmail(objDTO.getEmail());
		if(obj.isPresent() && obj.get().getId() != objDTO.getId()) {
			throw new DataIntegrityViolationExcepiton("E-mail Já existe para outro usuário");
		}
	}

	
	
}
