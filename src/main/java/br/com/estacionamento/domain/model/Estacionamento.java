package br.com.estacionamento.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.br.CNPJ;

import br.com.estacionamento.domain.ValidationGroups;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
@Entity
@Table(name = "estacionamento")
public class Estacionamento {

	@NotNull(groups = ValidationGroups.EstacionamentoID.class)
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Size(max = 60)
	@Column(name = "nome")
	private String nome;

	@CNPJ
	@NotBlank
	@Size(max = 20)
	@Column(name = "cnpj")
	private String cnpj;

	@NotBlank
	@Size(max = 255)
	@Column(name = "endereco")
	private String endereco;

	@NotBlank
	@Size(max = 20)
	@Column(name = "telefone")
	private String telefone;

	@NotNull
	@PositiveOrZero
	@Column(name = "vagas_para_motos")
	private Integer quantidadeDeVagasParaMotos;

	@NotNull
	@PositiveOrZero
	@Column(name = "vagas_para_carros")
	private Integer quantidadeDeVagasParaCarros;
	
	@NotNull
	@Column(name = "qtd_carros_estacionados")
	private Integer quantidadeDeCarrosEstacionados;
	
	@NotNull
	@Column(name = "qtd_motos_estacionadas")
	private Integer quantidadeDeMotosEstacionadas;


}
