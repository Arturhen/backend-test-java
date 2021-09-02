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
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import br.com.estacionamento.domain.ValidationGroups;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "veiculo")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class VeiculoDomainModel {

	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Valid
	@ConvertGroup(from = Default.class, to = ValidationGroups.EstacionamentoID.class)
	@NotNull
	@ManyToOne
	@JoinColumn(name = "estacionamento_id")
	private EstacionamentoDomainModel estacionamento;
	
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
	
	@JsonProperty(access = Access.READ_ONLY)
	private OffsetDateTime horarioEntrada;
	
	
}
