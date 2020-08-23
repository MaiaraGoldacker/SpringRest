package com.example.demo.domain.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.domain.exception.NegocioException;
import com.example.demo.domain.model.Cliente;
import com.example.demo.domain.repository.ClienteRepository;

@Service
public class CadastroClienteService {

	@Autowired
	private ClienteRepository clienteRepository;
	
	public Cliente salvar(Cliente cli) {
		
		
		Cliente clienteExistente = clienteRepository.findByEmail(cli.getEmail());
		
		
		if (clienteExistente != null && !clienteExistente.equals(cli)) {
			throw new NegocioException("Email já cadastrado!");
		}
		
		return clienteRepository.save(cli);
	}
	
	
	public void remover(@PathVariable Long idcliente) {
		clienteRepository.deleteById(idcliente);
		
	}
		
}
