package br.com.estacionamento.api.model.input;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EstacionamentoInput {

	private String nome;
	private String cnpj;
	private String endereco;
	private String telefone;
	private Integer quantidadeDeVagasParaCarros;
	private Integer quantidadeDeVagasParaMotos;
	
	
}
