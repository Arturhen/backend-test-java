package br.com.estacionamento.api.model.input;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.br.CNPJ;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstacionamentoLoginModel {
	
	@CNPJ
	@NotBlank
	@Size(max = 20)
	@Column(name = "cnpj")
	private String cnpj;
	
	@NotBlank
	@Size(max= 255)
	@Column(name = "senha")
	private String senha;
}
