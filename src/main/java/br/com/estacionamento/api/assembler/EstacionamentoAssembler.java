package br.com.estacionamento.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import br.com.estacionamento.api.model.EstacionamentoModel;
import br.com.estacionamento.api.model.input.EstacionamentoInput;
import br.com.estacionamento.domain.model.Estacionamento;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class EstacionamentoAssembler {

	private ModelMapper modelMapper;
	
	public EstacionamentoModel toModel(Estacionamento estacionamento) {
		return modelMapper.map(estacionamento, EstacionamentoModel.class);
	}
	
	public List<EstacionamentoModel> toCollectionModel(List<Estacionamento> estacionamentos){
		return estacionamentos.stream()
				.map(this::toModel)
				.collect(Collectors.toList());
	}
	
	public Estacionamento toEntity(EstacionamentoInput estacionamentoInput) {
		return modelMapper.map(estacionamentoInput, Estacionamento.class);
	}
	
}
