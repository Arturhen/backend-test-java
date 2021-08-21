package br.com.estacionamento.domain.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.estacionamento.domain.exception.BusinessException;
import br.com.estacionamento.domain.model.Estacionamento;
import br.com.estacionamento.domain.repository.EstacionamentoRepository;
import br.com.estacionamento.domain.repository.VeiculoRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CrudEstacionamento {

	private EstacionamentoRepository estacionamentoRepository;
	private VeiculoRepository veiculoRepository;

	@Transactional
	public Estacionamento create(Estacionamento estacionamento) {

		Optional<Estacionamento> estacionamentoNoBanco = estacionamentoRepository.findByCnpj(estacionamento.getCnpj());

		if (estacionamentoNoBanco.isPresent()) {
			if (!estacionamentoNoBanco.get().equals(estacionamento)) {
				throw new BusinessException("Ja existe uma empresa com Este CNPJ");
			}
		}
		return estacionamentoRepository.save(estacionamento);
	}

	public void delete(Long id) {
		veiculoRepository.findByEstacionamento(estacionamentoRepository.findById(id).get()).stream()
				.forEach(veiculo -> veiculoRepository.delete(veiculo));
		

		estacionamentoRepository.deleteById(id);
	}

	public Estacionamento find(Long id) {
		return estacionamentoRepository.findById(id)
				.orElseThrow(() -> new BusinessException("Estacionamento nao encontrado"));
	}

}
