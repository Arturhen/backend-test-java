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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.estacionamento.api.assembler.EstacionamentoAssembler;
import br.com.estacionamento.api.model.EstacionamentoOutputModel;
import br.com.estacionamento.api.model.EstacionamentoPutModel;
import br.com.estacionamento.api.model.input.EstacionamentoInputModel;
import br.com.estacionamento.api.model.input.EstacionamentoLoginModel;
import br.com.estacionamento.domain.exception.BusinessException;
import br.com.estacionamento.domain.model.EstacionamentoDomainModel;
import br.com.estacionamento.domain.service.EstacionamentoAuthenticationService;
import br.com.estacionamento.domain.service.EstacionamentoService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/estacionamentos")
public class EstacionamentoController {

	private EstacionamentoService estacionamentoService;
	private EstacionamentoAuthenticationService estacionamentoAuthenticationService;
	private EstacionamentoAssembler estacionamentoAssembler;

	@GetMapping
	public List<EstacionamentoOutputModel> list() {
		return estacionamentoAssembler.toCollectionModel(estacionamentoService.list());
	}

	@GetMapping("/{id}")
	public ResponseEntity<EstacionamentoOutputModel> find(@PathVariable Long id) {

		Optional<EstacionamentoDomainModel> estacionamentoOptional = estacionamentoService.findById(id);
		
		if (estacionamentoOptional.isPresent()) {
			EstacionamentoDomainModel estacionamento = estacionamentoOptional.get();
			return ResponseEntity.ok(estacionamentoAssembler.toOutPutModel(estacionamento));
		}
		
		return ResponseEntity.notFound().build();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public EstacionamentoOutputModel post(@Valid @RequestBody EstacionamentoInputModel estacionamentoInput) {
		EstacionamentoDomainModel estacionamento = estacionamentoAssembler.toEntity(estacionamentoInput);

		estacionamento.setQuantidadeDeCarrosEstacionados(0);
		estacionamento.setQuantidadeDeMotosEstacionadas(0);

		EstacionamentoDomainModel estacionamentoAdicionado = estacionamentoService.create(estacionamento);

		return estacionamentoAssembler.toOutPutModel(estacionamentoAdicionado);
	}

	@Transactional
	@PutMapping("/{id}")
	public ResponseEntity<EstacionamentoDomainModel> put(@Valid @RequestBody EstacionamentoPutModel estacionamentoPut,
			@PathVariable Long id) {
		System.out.println("aaaa");

		if (!(estacionamentoPut.getTelefone() != null || estacionamentoPut.getEndereco() != null
				|| estacionamentoPut.getQuantidadeDeVagasParaCarros() != null
				|| estacionamentoPut.getQuantidadeDeVagasParaMotos() != null)) {
			throw new BusinessException("Não pode atualizar com dados Vazios");
		}

		Optional<EstacionamentoDomainModel> estacionamentoNoBancoOptional = estacionamentoService.findById(id);

		if (!estacionamentoNoBancoOptional.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		EstacionamentoDomainModel estacionamentoNoBanco = estacionamentoNoBancoOptional.get();

		EstacionamentoDomainModel estacionamento = estacionamentoAssembler.putToEntity(estacionamentoPut);

		estacionamentoNoBanco = estacionamentoService.update(estacionamentoNoBanco, estacionamento, id);

		estacionamentoService.create(estacionamentoNoBanco);

		return ResponseEntity.ok(estacionamentoNoBanco);

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {

		if (!estacionamentoService.existsById(id)) {
			return ResponseEntity.notFound().build();
		}

		estacionamentoService.delete(id);

		return ResponseEntity.noContent().build();

	}

	@PostMapping("/login")
	public ResponseEntity<EstacionamentoOutputModel> authentication(@RequestBody EstacionamentoLoginModel login,@RequestHeader String Authorization ){
		EstacionamentoDomainModel estacionamento = estacionamentoAssembler.loginToEntity(login);
		
		EstacionamentoDomainModel estacionamentoAutenticado = estacionamentoAuthenticationService.authenticate(estacionamento, Authorization);
		
		
		EstacionamentoOutputModel estacionamentoOutput = estacionamentoAssembler.toOutPutModel(estacionamentoAutenticado);

		System.out.println("logado");
		return new ResponseEntity<EstacionamentoOutputModel>(estacionamentoOutput,HttpStatus.ACCEPTED);
		
	}
	
}
