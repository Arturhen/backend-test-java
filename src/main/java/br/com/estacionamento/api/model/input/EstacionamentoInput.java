package br.com.estacionamento.api.model.input;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.br.CNPJ;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EstacionamentoInput {

	@NotBlank
	@Size(max = 60)
	private String nome;
	
	@CNPJ
	@NotBlank
	@Size(max = 20)
	private String cnpj;
	
	@NotBlank
	@Size(max = 255)
	private String endereco;
	
	@NotBlank
	@Size(max = 20)
	private String telefone;
	
	@NotNull
	@PositiveOrZero
	private Integer quantidadeDeVagasParaCarros;
	
	@NotNull
	@PositiveOrZero
	private Integer quantidadeDeVagasParaMotos;
	
	
}
