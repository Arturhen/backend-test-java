package br.com.estacionamento.domain.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import br.com.estacionamento.domain.exception.BusinessException;
import br.com.estacionamento.domain.model.Estacionamento;
import br.com.estacionamento.domain.model.Veiculo;
import br.com.estacionamento.domain.model.VeiculoType;
import br.com.estacionamento.domain.repository.VeiculoRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class DeleteVeiculo {

	private VeiculoRepository veiculoRepository;
	
	@Transactional
	public void deleteByID(Long id) {
		Optional<Veiculo> veiculoOptional = veiculoRepository.findById(id);
		
		if(!veiculoOptional.isPresent()) {
			throw new BusinessException("veiculo a ser deletado não existe");
		}
		
		Veiculo veiculo = veiculoOptional.get();
		
		Estacionamento estacionamento = veiculo.getEstacionamento();
		
		if(veiculo.getTipo() == VeiculoType.CARRO) {
			estacionamento.setQuantidadeDeCarrosEstacionados(estacionamento.getQuantidadeDeCarrosEstacionados() - 1 );
		}
		
		if(veiculo.getTipo() == VeiculoType.MOTO) {
			estacionamento.setQuantidadeDeMotosEstacionadas(estacionamento.getQuantidadeDeMotosEstacionadas() - 1 );
		}
		veiculoRepository.delete(veiculo);
		
	}
	

	
}
