package br.com.estacionamento.api.controller;

import java.util.List;

import javax.validation.Valid;

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

import br.com.estacionamento.api.assembler.VeiculoAssembler;
import br.com.estacionamento.api.model.VeiculoModel;
import br.com.estacionamento.api.model.input.VeiculoInput;
import br.com.estacionamento.domain.model.Veiculo;
import br.com.estacionamento.domain.repository.VeiculoRepository;
import br.com.estacionamento.domain.service.AdicionaVeiculoNoEstacionamento;
import br.com.estacionamento.domain.service.CrudVeiculo;
import br.com.estacionamento.domain.service.DeleteVeiculo;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/veiculos")
public class VeiculoController {

	private VeiculoRepository veiculoRepository;
	private AdicionaVeiculoNoEstacionamento adicionaVeiculoNoEstacionamento;
	private VeiculoAssembler veiculoAssembler;
	private DeleteVeiculo deleteVeiculo;
	private CrudVeiculo crudVeiculo;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public VeiculoModel adicionaVeiculo(@Valid @RequestBody VeiculoInput veiculoInput) {
		System.out.println("VAMOS JOGAAAAAAAAAAAAAAAR");

		Veiculo veiculo = veiculoAssembler.toEntity(veiculoInput);

		Veiculo veiculoAdiconado = adicionaVeiculoNoEstacionamento.create(veiculo);
		return veiculoAssembler.toModel(veiculoAdiconado);
	}

	@GetMapping
	public List<VeiculoModel> list() {
		return veiculoAssembler.toCollectionModel(veiculoRepository.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<VeiculoModel> find(@PathVariable Long id) {
		return veiculoRepository.findById(id).map(veiculo -> ResponseEntity.ok(veiculoAssembler.toModel(veiculo)))
				.orElse(ResponseEntity.notFound().build());

	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id){
		deleteVeiculo.deleteByID(id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<VeiculoModel> put(@Valid @RequestBody VeiculoInput veiculoInput,
			@PathVariable Long id) {
		
		if (!veiculoRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		Veiculo veiculo = veiculoAssembler.toEntity(veiculoInput);
		
		crudVeiculo.update(id, veiculo);
		
		VeiculoModel veiculoModel= veiculoAssembler.toModel(veiculo);
		
		return ResponseEntity.ok(veiculoModel);

	}
	
	
}
