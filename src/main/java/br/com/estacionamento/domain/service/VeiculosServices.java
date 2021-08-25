package br.com.estacionamento.domain.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.estacionamento.domain.exception.BusinessException;
import br.com.estacionamento.domain.model.Estacionamento;
import br.com.estacionamento.domain.model.Veiculo;
import br.com.estacionamento.domain.model.VeiculoType;
import br.com.estacionamento.domain.repository.VeiculoRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class VeiculosServices {

	private VeiculoRepository veiculoRepository;

	public void existWithSamePlate(Veiculo veiculo) {
		Optional<Veiculo> veiculoNoBanco = veiculoRepository.findByPlaca(veiculo.getPlaca());

		if (veiculoNoBanco.isPresent()) {
			if (!veiculoNoBanco.get().equals(veiculo)) {
				throw new BusinessException("Ja existe um Veiculo com Essa placa Estacionado");
			}
		}
	}
	
	public void updateVagasEstacionamento(Estacionamento estacionamento, Veiculo veiculo) {
		if(veiculo.getTipo() == VeiculoType.CARRO) {
			this.updateVagasDeCarro(estacionamento, veiculo);
		}
		
		if(veiculo.getTipo() == VeiculoType.MOTO) {
			this.updateVagasDeMoto(estacionamento, veiculo);
		}
	}
	
	private void updateVagasDeMoto(Estacionamento estacionamento, Veiculo veiculo) {
		if(estacionamento.getQuantidadeDeVagasParaMotos() <= estacionamento.getQuantidadeDeMotosEstacionadas()) {
			throw new BusinessException("Esse estacionamento esta lotado de Motos.");
		}
		estacionamento.setQuantidadeDeMotosEstacionadas(estacionamento.getQuantidadeDeMotosEstacionadas() + 1 );
	}
	
	private void updateVagasDeCarro(Estacionamento estacionamento, Veiculo veiculo) {
		if(estacionamento.getQuantidadeDeVagasParaCarros() <= estacionamento.getQuantidadeDeCarrosEstacionados()) {
			throw new BusinessException("Esse estacionamento esta lotado de Carros.");
		}
		estacionamento.setQuantidadeDeCarrosEstacionados(estacionamento.getQuantidadeDeCarrosEstacionados() + 1 );
	}
	
	public void deleteVeiculoInEstacionamento(Estacionamento estacionamento, Veiculo veiculo) {
		if(veiculo.getTipo() == VeiculoType.CARRO) {
			estacionamento.setQuantidadeDeCarrosEstacionados(estacionamento.getQuantidadeDeCarrosEstacionados()-1);
		}
		
		if(veiculo.getTipo() == VeiculoType.MOTO) {
			estacionamento.setQuantidadeDeMotosEstacionadas(estacionamento.getQuantidadeDeMotosEstacionadas()-1);
		}
	}
	
}
