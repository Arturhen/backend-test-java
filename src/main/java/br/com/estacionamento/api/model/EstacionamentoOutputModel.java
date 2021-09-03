package br.com.estacionamento.api.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EstacionamentoOutputModel {

	private Long id;
	private String nome;
	private String endereco;
	private String telefone;
	private Integer quantidadeDeVagasParaMotos;
	private Integer quantidadeDeMotosEstacionadas;
	private Integer quantidadeDeVagasParaCarros;
	private Integer quantidadeDeCarrosEstacionados;
	private String token;
}
