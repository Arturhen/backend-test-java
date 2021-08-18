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

import br.com.estacionamento.api.assembler.EstacionamentoAssembler;
import br.com.estacionamento.api.model.EstacionamentoModel;
import br.com.estacionamento.api.model.input.EstacionamentoInput;
import br.com.estacionamento.domain.model.Estacionamento;
import br.com.estacionamento.domain.repository.EstacionamentoRepository;
import br.com.estacionamento.domain.service.CrudEstacionamento;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/estacionamentos")
public class EstacionamentoController {

	private EstacionamentoRepository estacionamentoRespository;
	private CrudEstacionamento crudEstacionamento;
	private EstacionamentoAssembler estacionamentoAssembler;

	@GetMapping
	public List<EstacionamentoModel> list() {
		return estacionamentoAssembler.toCollectionModel(estacionamentoRespository.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<EstacionamentoModel> find(@PathVariable Long id) {
		return estacionamentoRespository.findById(id)
				.map(estacionamento -> ResponseEntity.ok(estacionamentoAssembler.toModel(estacionamento)))
				.orElse(ResponseEntity.notFound().build());
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public EstacionamentoModel post(@Valid @RequestBody EstacionamentoInput estacionamentoInput) {
		Estacionamento estacionamento = estacionamentoAssembler.toEntity(estacionamentoInput);
		
		estacionamento.setQuantidadeDeCarrosEstacionados(0);
		estacionamento.setQuantidadeDeMotosEstacionadas(0);
		
		Estacionamento estacionamentoAdicionado = crudEstacionamento.create(estacionamento);

		return estacionamentoAssembler.toModel(estacionamentoAdicionado);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Estacionamento> put(@Valid @RequestBody Estacionamento estacionamento,
			@PathVariable Long id) {
		if (!estacionamentoRespository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}

		estacionamento.setId(id);
		crudEstacionamento.create(estacionamento);

		return ResponseEntity.ok(estacionamento);

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {

		if (!estacionamentoRespository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}

		crudEstacionamento.delete(id);

		return ResponseEntity.noContent().build();

	}

}
