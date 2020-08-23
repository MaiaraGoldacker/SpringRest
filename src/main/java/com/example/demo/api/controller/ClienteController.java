package com.example.demo.api.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.model.Cliente;
import com.example.demo.domain.repository.ClienteRepository;
import com.example.demo.domain.service.CadastroClienteService;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private CadastroClienteService cadastroClienteService;
	
	@GetMapping
	public List<Cliente> listar(){
	 return clienteRepository.findAll();
		
	}
	
	@GetMapping("/{idcliente}")
	public ResponseEntity<Cliente> listar(@Valid @PathVariable Long idcliente){
	  Optional<Cliente> cli = clienteRepository.findById(idcliente);
	  
	  if (cli.isPresent()) {
		  return ResponseEntity.ok(cli.get());
	  }
	  
	  return ResponseEntity.notFound().build();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente adicionar(@Valid @RequestBody Cliente cli) {
		return cadastroClienteService.salvar(cli);
	}
	
	@PutMapping("/{idcliente}")
	public ResponseEntity<Cliente> alterar(@PathVariable Long idcliente, @RequestBody Cliente cli) {
		if(!clienteRepository.existsById(idcliente)) {
			return ResponseEntity.notFound().build();
		}
		cli.setId(idcliente);
		cli = cadastroClienteService.salvar(cli);
		return ResponseEntity.ok(cli);
	}

	@DeleteMapping("/{idcliente}")
	public ResponseEntity<Void> remover(@PathVariable Long idcliente) {
		if(!clienteRepository.existsById(idcliente)) {
			return ResponseEntity.notFound().build();
		}
		
		cadastroClienteService.remover(idcliente);
		return ResponseEntity.noContent().build();
	}
	
}
