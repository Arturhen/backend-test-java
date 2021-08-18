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

	@Transactional
	public Veiculo create(Veiculo veiculo) {

		Estacionamento estacionamento = crudEstacionamento.find(veiculo.getEstacionamento().getId());

//		verificar se veiculo ja esta estacionado, ver as vagas maximas
		veiculo.setHorarioEntrada(OffsetDateTime.now());
		veiculo.setEstacionamento(estacionamento);

		return veiculoRepository.save(veiculo);
	}

}
