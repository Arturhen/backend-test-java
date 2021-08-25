package br.com.estacionamento.domain.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.estacionamento.domain.model.Estacionamento;
import br.com.estacionamento.domain.model.Veiculo;
import br.com.estacionamento.domain.repository.VeiculoRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CrudVeiculo {

	private VeiculoRepository veiculoRepository;
	private VeiculosServices veiculosServices;
	private CrudEstacionamento crudEstacionamento;

	@Transactional
	public Veiculo update(Long id, Veiculo veiculo) {

		veiculo.setId(id);

		veiculosServices.existWithSamePlate(veiculo);

		Veiculo veiculoAntigo = veiculoRepository.findById(id).get();
		Estacionamento estacionamentoNovo = crudEstacionamento.find(veiculo.getEstacionamento().getId());

		if (veiculo.getEstacionamento() != veiculoAntigo.getEstacionamento()) {
			veiculosServices.updateVagasEstacionamento(estacionamentoNovo, veiculo);
			veiculosServices.deleteVeiculoInEstacionamento(veiculoAntigo.getEstacionamento(), veiculoAntigo);
		}

		veiculo.setHorarioEntrada(veiculoAntigo.getHorarioEntrada());

		return veiculoRepository.save(veiculo);

	}

}
