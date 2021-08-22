package br.com.estacionamento.api.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
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
import br.com.estacionamento.api.model.EstacionamentoPut;
import br.com.estacionamento.api.model.input.EstacionamentoInput;
import br.com.estacionamento.domain.exception.BusinessException;
import br.com.estacionamento.domain.model.Estacionamento;
import br.com.estacionamento.domain.repository.EstacionamentoRepository;
import br.com.estacionamento.domain.service.CrudEstacionamento;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/estacionamentos")
public class EstacionamentoController {

	private EstacionamentoRepository estacionamentoRepository;
	private CrudEstacionamento crudEstacionamento;
	private EstacionamentoAssembler estacionamentoAssembler;

	@GetMapping
	public List<EstacionamentoModel> list() {
		return estacionamentoAssembler.toCollectionModel(estacionamentoRepository.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<EstacionamentoModel> find(@PathVariable Long id) {
		return estacionamentoRepository.findById(id)
				.map(estacionamento -> ResponseEntity.ok(estacionamentoAssembler.toModel(estacionamento)))
				.orElse(ResponseEntity.notFound().build());
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public EstacionamentoModel post(@Valid @RequestBody EstacionamentoInput estacionamentoInput) {
		Estacionamento estacionamento = estacionamentoAssembler.toEntity(estacionamentoInput);

		estacionamento.setQuantidadeDeCarrosEstacionados(0);
		estacionamento.setQuantidadeDeMotosEstacionadas(0);
		System.out.println("VAMOS JOGAAAAAAAAAAAAAAAR");

		Estacionamento estacionamentoAdicionado = crudEstacionamento.create(estacionamento);
		System.out.println("VAMOS JOGAR VAMOS JOGAR FREE FIRE");

		return estacionamentoAssembler.toModel(estacionamentoAdicionado);
	}

	@Transactional
	@PutMapping("/{id}")
	public ResponseEntity<Estacionamento> put(@Valid @RequestBody EstacionamentoPut estacionamentoPut,
			@PathVariable Long id) {

		if (!(estacionamentoPut.getTelefone() != null || estacionamentoPut.getEndereco() != null
				|| estacionamentoPut.getQuantidadeDeVagasParaCarros() != null
				|| estacionamentoPut.getQuantidadeDeVagasParaMotos() != null)) {
			throw new BusinessException("Não pode atualizar com dados Vazios");
		}

		Optional<Estacionamento> estacionamentoNoBancoOptional = estacionamentoRepository.findById(id);

		if (!estacionamentoNoBancoOptional.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		Estacionamento estacionamentoNoBanco = estacionamentoNoBancoOptional.get();

		Estacionamento estacionamento = estacionamentoAssembler.putToEntity(estacionamentoPut);

		estacionamentoNoBanco = crudEstacionamento.update(estacionamentoNoBanco, estacionamento, id);

		crudEstacionamento.create(estacionamentoNoBanco);

		return ResponseEntity.ok(estacionamentoNoBanco);

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {

		if (!estacionamentoRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}

		crudEstacionamento.delete(id);

		return ResponseEntity.noContent().build();

	}

}
