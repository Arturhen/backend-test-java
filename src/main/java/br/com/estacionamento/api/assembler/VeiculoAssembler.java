package br.com.estacionamento.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import br.com.estacionamento.api.model.VeiculoModel;
import br.com.estacionamento.api.model.input.VeiculoInputModel;
import br.com.estacionamento.domain.model.VeiculoDomainModel;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class VeiculoAssembler {

	private ModelMapper modelMapper;
	
	public VeiculoModel toModel(VeiculoDomainModel veiculo) {
		return modelMapper.map(veiculo, VeiculoModel.class);
	}
	
	public List<VeiculoModel> toCollectionModel(List<VeiculoDomainModel> veiculos){
		return veiculos.stream()
				.map(this::toModel)
				.collect(Collectors.toList());
	}
	
	public VeiculoDomainModel toEntity(VeiculoInputModel veiculoInput) {
		return modelMapper.map(veiculoInput, VeiculoDomainModel.class);
	}
	
}
