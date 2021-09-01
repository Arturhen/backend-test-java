package br.com.estacionamento.api.model.input;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstacionamentoIdInputModel {

	@NotNull
	private Long id;
}
