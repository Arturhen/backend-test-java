package br.com.estacionamento.api.model;

import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EstacionamentoPut {

	@Size(max = 255)
	private String endereco;
	
	@Size(max = 20)
	private String telefone;
	
	@PositiveOrZero
	private Integer quantidadeDeVagasParaMotos;
	
	@PositiveOrZero
	private Integer quantidadeDeVagasParaCarros;
}
