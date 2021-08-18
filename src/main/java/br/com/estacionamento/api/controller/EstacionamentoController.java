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

	@GetMapping
	public List<Estacionamento> list() {
		return estacionamentoRespository.findAll();
	}
	
//	@GetMapping
//	public List<String> list() {
//		List<String> aa = new ArrayList<>();
//		estacionamentoRespository.findAll().forEach(a ->aa.add(a.getId().toString()));
//		
//		return aa;
//	}

	@GetMapping("/{id}")
	public ResponseEntity<Estacionamento> find(@PathVariable Long id) {
		return estacionamentoRespository.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Estacionamento post(@Valid @RequestBody Estacionamento estacionamento) {
		return crudEstacionamento.create(estacionamento);
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
