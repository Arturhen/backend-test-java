package br.com.estacionamento.api.model.input;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import br.com.estacionamento.domain.model.VeiculoType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VeiculoInput {
	
	@Valid
	@NotNull
	private EstacionamentoIdInput estacionamento;
	
	@NotBlank
	@Size(max =20)
	private String marca;
	
	@NotBlank
	@Size(max =20)
	private String modelo;
	
	@NotBlank
	@Size(max =20)
	private String cor;
	
	@NotBlank
	@Size(max =20)
	@Pattern(regexp = "[A-Z]{3}[0-9][0-9A-Z][0-9]{2}", message = "Placa padrão mercosul AAA1A23")
	private String placa;
	
	@Enumerated(EnumType.STRING)
	private VeiculoType tipo;
}
