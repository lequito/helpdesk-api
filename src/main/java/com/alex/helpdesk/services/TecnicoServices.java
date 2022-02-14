package com.alex.helpdesk.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.alex.helpdesk.domain.Pessoa;
import com.alex.helpdesk.domain.Tecnico;
import com.alex.helpdesk.domain.dtos.TecnicoDTO;
import com.alex.helpdesk.repositories.PessoaRepository;
import com.alex.helpdesk.repositories.TecnicoRepository;
import com.alex.helpdesk.services.exceptions.DataIntegrityViolationExcepiton;
import com.alex.helpdesk.services.exceptions.ObjectNotFoundException;

@Service
public class TecnicoServices {

	@Autowired
	private TecnicoRepository repository;
	@Autowired
	private PessoaRepository pessoaRepository;
	@Autowired
	private BCryptPasswordEncoder encoder;

	public Tecnico findById(Integer id) {
		Optional<Tecnico> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado ID: " + id));
	}

	public List<Tecnico> findAll() {
		return repository.findAll();
	}

	public Tecnico create(TecnicoDTO objDTO) {
		objDTO.setId(null);
		objDTO.setSenha(encoder.encode(objDTO.getSenha()));
		Tecnico newObj = new Tecnico(objDTO);
		validaPorCpfEemail(objDTO);
		return repository.save(newObj);
	}
	
	public Tecnico update(Integer id, @Valid TecnicoDTO objDTO) {
		objDTO.setId(id);
		Tecnico oldObj = findById(id);
		validaPorCpfEemail(objDTO);
		oldObj = new Tecnico(objDTO);
		return repository.save(oldObj);
	}
	
	public void delete (Integer id) {
		Tecnico obj = findById(id);
		if(obj.getChamados().size() > 0){
			throw new DataIntegrityViolationExcepiton("Técnico não pode ser deletado, pois possui chamados atribuidos ");
		}
		repository.deleteById(id);
	}

	private void validaPorCpfEemail(TecnicoDTO objDTO) {
		
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
