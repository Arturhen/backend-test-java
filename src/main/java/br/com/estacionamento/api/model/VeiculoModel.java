package br.com.estacionamento.api.model;

import java.time.OffsetDateTime;

import br.com.estacionamento.domain.model.VeiculoType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VeiculoModel {
	
	private Long id;
	private String nomeEstacionamento;
	private String marca;
	private String modelo;
	private String cor;
	private String placa;
	private VeiculoType tipo;
	private OffsetDateTime horarioEntrada;
	
}
