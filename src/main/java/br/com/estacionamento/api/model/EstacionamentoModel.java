package br.com.estacionamento.api.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EstacionamentoModel {

	private Long id;
	private String nome;
	private String endereco;
	private String telefone;
	private Integer quantidadeDeVagasParaMotos;
	private Integer quantidadeDeVagasParaCarros;
	private Integer quantidadeDeCarrosEstacionados;
	private Integer quantidadeDeMotosEstacionadas;
}
