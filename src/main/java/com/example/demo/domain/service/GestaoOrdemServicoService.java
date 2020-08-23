package com.example.demo.domain.service;

import java.time.OffsetDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.exception.EntidadeNaoEncontradaException;
import com.example.demo.domain.exception.NegocioException;
import com.example.demo.domain.model.Cliente;
import com.example.demo.domain.model.Comentario;
import com.example.demo.domain.model.OrdemServico;
import com.example.demo.domain.model.StatusOrdemServico;
import com.example.demo.domain.repository.ClienteRepository;
import com.example.demo.domain.repository.ComentarioRepository;
import com.example.demo.domain.repository.OrdemServicoRepository;

@Service
public class GestaoOrdemServicoService {
	
	@Autowired
	private OrdemServicoRepository ordemServicoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private ComentarioRepository comentarioRepository;
	
	public OrdemServico criar(OrdemServico ordemServico) {
		
		Cliente cliente =clienteRepository.findById(ordemServico.getCliente().getId()).orElseThrow(()
				->  new NegocioException("Cliente não encontrado"));
		
		ordemServico.setCliente(cliente);
		ordemServico.setStatus(StatusOrdemServico.ABERTA);
		ordemServico.setDataAbertura(OffsetDateTime.now());
		
		return ordemServicoRepository.save(ordemServico);
	}
	
	public void finalizar(Long ordemServicoId) {
		OrdemServico ordemServico = buscar(ordemServicoId);
		
		ordemServico.finalizar();
		
		ordemServicoRepository.save(ordemServico);
	}
	
	private OrdemServico buscar(Long ordemServicoId) {
		return ordemServicoRepository.findById(ordemServicoId).orElseThrow(()
				->  new EntidadeNaoEncontradaException("Ordem serviço não encontrada"));
	}
	
	public Comentario adicionarComanetario(Long ordemServicoId, String descricao) {
		OrdemServico ordemServico = buscar(ordemServicoId);

		var comantario = new Comentario();
		comantario.setDataEnvio(OffsetDateTime.now());
		comantario.setDescricao(descricao);
		comantario.setOrdemServico(ordemServico);
		
		return comentarioRepository.save(comantario);
		
	}
}
