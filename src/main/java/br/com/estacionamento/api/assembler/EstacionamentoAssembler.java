package br.com.estacionamento.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import br.com.estacionamento.api.model.EstacionamentoOutputModel;
import br.com.estacionamento.api.model.EstacionamentoPutModel;
import br.com.estacionamento.api.model.input.EstacionamentoInputModel;
import br.com.estacionamento.domain.model.EstacionamentoDomainModel;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class EstacionamentoAssembler {

	private ModelMapper modelMapper;
	
	public EstacionamentoOutputModel toModel(EstacionamentoDomainModel estacionamento) {
		return modelMapper.map(estacionamento, EstacionamentoOutputModel.class);
	}
	
	public List<EstacionamentoOutputModel> toCollectionModel(List<EstacionamentoDomainModel> estacionamentos){
		return estacionamentos.stream()
				.map(this::toModel)
				.collect(Collectors.toList());
	}
	
	public EstacionamentoDomainModel toEntity(EstacionamentoInputModel estacionamentoInput) {
		return modelMapper.map(estacionamentoInput, EstacionamentoDomainModel.class);
	}
	
	public EstacionamentoDomainModel putToEntity(EstacionamentoPutModel estacionamentoPut) {
		return modelMapper.map(estacionamentoPut, EstacionamentoDomainModel.class);
	}
	
	
}
