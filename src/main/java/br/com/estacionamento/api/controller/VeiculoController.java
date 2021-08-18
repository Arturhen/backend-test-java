package br.com.estacionamento.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.estacionamento.api.assembler.VeiculoAssembler;
import br.com.estacionamento.api.model.VeiculoModel;
import br.com.estacionamento.api.model.input.VeiculoInput;
import br.com.estacionamento.domain.model.Veiculo;
import br.com.estacionamento.domain.repository.VeiculoRepository;
import br.com.estacionamento.domain.service.AdicionaVeiculoNoEstacionamento;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/veiculos")
public class VeiculoController {


	private VeiculoRepository veiculoRepository;
	private AdicionaVeiculoNoEstacionamento adicionaVeiculoNoEstacionamento;
	private VeiculoAssembler veiculoAssembler;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public VeiculoModel adicionaVeiculo(@Valid @RequestBody VeiculoInput veiculoInput) {
		Veiculo veiculo = veiculoAssembler.toEntity(veiculoInput);
		
		Veiculo veiculoAdiconado = adicionaVeiculoNoEstacionamento.add(veiculo);
		return 	veiculoAssembler.toModel(veiculoAdiconado);
	}
	
	@GetMapping
	public List<VeiculoModel> list(){
		return veiculoAssembler.toCollectionModel(veiculoRepository.findAll());
	}
	
	@GetMapping("/{id}")		
	public ResponseEntity<VeiculoModel> find(@PathVariable Long id){
		return veiculoRepository.findById(id)
				.map(veiculo -> ResponseEntity.ok(veiculoAssembler.toModel(veiculo)))
				.orElse(ResponseEntity.notFound().build());
		
	}
}
