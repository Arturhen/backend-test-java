package br.com.estacionamento.domain.service;

import java.time.OffsetDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.estacionamento.domain.model.Estacionamento;
import br.com.estacionamento.domain.model.Veiculo;
import br.com.estacionamento.domain.repository.VeiculoRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class AdicionaVeiculoNoEstacionamento {

	private VeiculoRepository veiculoRepository;
	private CrudEstacionamento crudEstacionamento;
	private VeiculosServices veiculosServices;

	@Transactional
	public Veiculo create(Veiculo veiculo) {

		veiculosServices.existWithSamePlate(veiculo);

		Estacionamento estacionamento = crudEstacionamento.find(veiculo.getEstacionamento().getId());

		veiculosServices.updateVagasEstacionamento(estacionamento, veiculo);

//		verificar se veiculo ja esta estacionado
		veiculo.setHorarioEntrada(OffsetDateTime.now());
		veiculo.setEstacionamento(estacionamento);

		return veiculoRepository.save(veiculo);
	}

}
