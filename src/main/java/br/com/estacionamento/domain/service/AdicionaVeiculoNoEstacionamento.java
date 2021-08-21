package br.com.estacionamento.domain.service;

import java.time.OffsetDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.estacionamento.domain.exception.BusinessException;
import br.com.estacionamento.domain.model.Estacionamento;
import br.com.estacionamento.domain.model.Veiculo;
import br.com.estacionamento.domain.model.VeiculoType;
import br.com.estacionamento.domain.repository.VeiculoRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class AdicionaVeiculoNoEstacionamento {

	private VeiculoRepository veiculoRepository;
	private CrudEstacionamento crudEstacionamento;

	@Transactional
	public Veiculo create(Veiculo veiculo) {

		Estacionamento estacionamento = crudEstacionamento.find(veiculo.getEstacionamento().getId());

		if(veiculo.getTipo() == VeiculoType.CARRO) {
			this.updateVagasDeCarro(estacionamento, veiculo);
		}
		
		if(veiculo.getTipo() == VeiculoType.MOTO) {
			this.updateVagasDeMoto(estacionamento, veiculo);
		}
		
//		verificar se veiculo ja esta estacionado
		veiculo.setHorarioEntrada(OffsetDateTime.now());
		veiculo.setEstacionamento(estacionamento);

		return veiculoRepository.save(veiculo);
	}

	@Transactional
	public void updateVagasDeMoto(Estacionamento estacionamento, Veiculo veiculo) {
		if(estacionamento.getQuantidadeDeVagasParaMotos() <= estacionamento.getQuantidadeDeMotosEstacionadas()) {
			throw new BusinessException("Esse estacionamento esta lotado de Motos.");
		}
		estacionamento.setQuantidadeDeMotosEstacionadas(estacionamento.getQuantidadeDeMotosEstacionadas() + 1 );
	}
	
	@Transactional
	public void updateVagasDeCarro(Estacionamento estacionamento, Veiculo veiculo) {
		if(estacionamento.getQuantidadeDeVagasParaCarros() <= estacionamento.getQuantidadeDeCarrosEstacionados()) {
			throw new BusinessException("Esse estacionamento esta lotado de Carros.");
		}
		estacionamento.setQuantidadeDeCarrosEstacionados(estacionamento.getQuantidadeDeCarrosEstacionados() + 1 );
	}
	
	
}
