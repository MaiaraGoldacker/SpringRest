package com.example.demo.api.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.api.model.OrdemServicoInput;
import com.example.demo.api.model.OrdemServicoModel;
import com.example.demo.domain.model.OrdemServico;
import com.example.demo.domain.repository.OrdemServicoRepository;
import com.example.demo.domain.service.GestaoOrdemServicoService;

@RestController
@RequestMapping("/ordens-servico")
public class OrdemServicoController {
	
	@Autowired
	private GestaoOrdemServicoService gestaoOrdemServicoService;
	
	@Autowired
	private OrdemServicoRepository ordemServicoRepository;

	@Autowired
	private ModelMapper modelMapper;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public OrdemServicoModel adicionar(@Valid @RequestBody OrdemServicoInput ordemServicoInput) {
		OrdemServico os = toEntity(ordemServicoInput);
		return toModel(gestaoOrdemServicoService.criar(os));
	}
	
	@GetMapping
	public List<OrdemServicoModel> listar(){
		return toCollectionModel(ordemServicoRepository.findAll());		
	}
	
	@GetMapping("/{idordem}")
	public ResponseEntity<OrdemServicoModel> listar(@PathVariable Long idordem){
	  Optional<OrdemServico> ordem = ordemServicoRepository.findById(idordem);
	  
	  if (ordem.isPresent()) {
		  OrdemServicoModel ordemServicoModel = toModel(ordem.get());		  
		  return ResponseEntity.ok(ordemServicoModel);
	  }
	  
	  return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/{idordem}/finalizacao")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void finalizar(@PathVariable Long idordem) {
		gestaoOrdemServicoService.finalizar(idordem);
	}
	
	private OrdemServicoModel toModel(OrdemServico ordemServico) {
		return modelMapper.map(ordemServico, OrdemServicoModel.class);
	}
	
	private List<OrdemServicoModel> toCollectionModel(List<OrdemServico> ordensServico){
		return ordensServico.stream().map(ordemServico 
				->toModel(ordemServico))
				.collect(Collectors.toList());
	}
	
	private OrdemServico toEntity(OrdemServicoInput ordemServicoInput) {
		return  modelMapper.map(ordemServicoInput, OrdemServico.class);
	}
}
