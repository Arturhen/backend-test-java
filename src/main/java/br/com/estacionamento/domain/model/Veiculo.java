package br.com.estacionamento.domain.model;

import java.time.OffsetDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Veiculo {

	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
//	@Valid
//	@ConvertGroup(from = Default.class, to = ValidationGroups.EstacionamentoID.class)
//	@NotNull
	@ManyToOne
	@JoinColumn(name = "estacionamento_id")
	private Estacionamento estacionamento;
	
//	@NotBlank
//	@Size(max =20)
	private String marca;
	
//	@NotBlank
//	@Size(max =20)
	private String modelo;
	
//	@NotBlank
//	@Size(max =20)
	private String cor;
	
//	@NotBlank
//	@Size(max =20)
//	@Pattern(regexp = "[A-Z]{3}[0-9][0-9A-Z][0-9]{2}", message = "Placa padrão mercosul AAA1A23")
	private String placa;
	
	@Enumerated(EnumType.STRING)
	private VeiculoType tipo;
	
	@JsonProperty(access = Access.READ_ONLY)
	private OffsetDateTime horarioEntrada;
	
	
}
