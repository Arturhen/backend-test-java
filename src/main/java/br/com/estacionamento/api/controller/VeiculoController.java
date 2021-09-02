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
import br.com.estacionamento.api.model.input.VeiculoInputModel;
import br.com.estacionamento.domain.model.VeiculoDomainModel;
import br.com.estacionamento.domain.service.VeiculoService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/veiculos")
public class VeiculoController {

	private VeiculoAssembler veiculoAssembler;
	private VeiculoService veiculoService;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public VeiculoModel adicionaVeiculo(@Valid @RequestBody VeiculoInputModel veiculoInput) {

		VeiculoDomainModel veiculo = veiculoAssembler.toEntity(veiculoInput);

		VeiculoDomainModel veiculoAdiconado = veiculoService.create(veiculo);
		return veiculoAssembler.toModel(veiculoAdiconado);
	}

	@GetMapping
	public List<VeiculoModel> list() {
		return veiculoAssembler.toCollectionModel(veiculoService.list());
	}

	@GetMapping("/{id}")
	public ResponseEntity<VeiculoModel> find(@PathVariable Long id) {
		try {
			VeiculoDomainModel veiculo = veiculoService.findById(id);
			return ResponseEntity.ok(veiculoAssembler.toModel(veiculo));
		} catch (Error e) {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		
		if (!veiculoService.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		
		veiculoService.deleteByID(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<VeiculoModel> put(@Valid @RequestBody VeiculoInputModel veiculoInput, @PathVariable Long id) {

		if (!veiculoService.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		VeiculoDomainModel veiculo = veiculoAssembler.toEntity(veiculoInput);

		veiculoService.update(id, veiculo);

		VeiculoModel veiculoModel = veiculoAssembler.toModel(veiculo);

		return ResponseEntity.ok(veiculoModel);

	}

}
