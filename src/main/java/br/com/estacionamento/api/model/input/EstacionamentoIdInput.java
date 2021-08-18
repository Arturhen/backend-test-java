package br.com.estacionamento.api.model.input;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstacionamentoIdInput {

	@NotNull
	private Long id;
}
