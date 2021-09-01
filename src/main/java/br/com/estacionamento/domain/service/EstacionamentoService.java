package br.com.estacionamento.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.estacionamento.domain.exception.BusinessException;
import br.com.estacionamento.domain.model.EstacionamentoDomainModel;
import br.com.estacionamento.domain.repository.EstacionamentoRepository;
import br.com.estacionamento.domain.repository.VeiculoRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class EstacionamentoService {

	private EstacionamentoRepository estacionamentoRepository;
	private VeiculoRepository veiculoRepository;

	@Transactional
	public EstacionamentoDomainModel create(EstacionamentoDomainModel estacionamento) {

		Optional<EstacionamentoDomainModel> estacionamentoNoBanco = estacionamentoRepository.findByCnpj(estacionamento.getCnpj());

		if (estacionamentoNoBanco.isPresent()) {
			if (!estacionamentoNoBanco.get().equals(estacionamento)) {
				throw new BusinessException("Ja existe uma empresa com Este CNPJ");
			}
		}
		return estacionamentoRepository.save(estacionamento);
	}
	public List<EstacionamentoDomainModel> list() {
		return estacionamentoRepository.findAll();
	}
	
	public void delete(Long id) {
		veiculoRepository.findByEstacionamento(estacionamentoRepository.findById(id).get()).stream()
				.forEach(veiculo -> veiculoRepository.delete(veiculo));

		estacionamentoRepository.deleteById(id);
	}

	public EstacionamentoDomainModel findById(Long id) {
		return estacionamentoRepository.findById(id)
				.orElseThrow(() -> new BusinessException("Estacionamento nao encontrado"));
	}

	public EstacionamentoDomainModel update(EstacionamentoDomainModel estacionamentoNoBanco, EstacionamentoDomainModel estacionamentoUpdates, Long id) {

		estacionamentoNoBanco.setId(id);

		if (estacionamentoUpdates.getEndereco() != null) {
			estacionamentoNoBanco.setEndereco(estacionamentoUpdates.getEndereco());
		}

		if (estacionamentoUpdates.getTelefone() != null) {
			estacionamentoNoBanco.setTelefone(estacionamentoUpdates.getTelefone());
		}

		if (estacionamentoUpdates.getQuantidadeDeVagasParaCarros() != null) {
			if (estacionamentoUpdates.getQuantidadeDeVagasParaCarros() < estacionamentoNoBanco
					.getQuantidadeDeCarrosEstacionados()) {
				throw new BusinessException(
						"O número de vagas para carro deve ser igual ou superior a quantidade de carros estacionados");
			}
			estacionamentoNoBanco
					.setQuantidadeDeVagasParaCarros(estacionamentoUpdates.getQuantidadeDeVagasParaCarros());
		}

		if (estacionamentoUpdates.getQuantidadeDeVagasParaMotos() != null) {
			if (estacionamentoUpdates.getQuantidadeDeVagasParaMotos() < estacionamentoNoBanco
					.getQuantidadeDeMotosEstacionadas()) {
				throw new BusinessException(
						"O número de vagas para motos deve ser igual ou superior a quantidade de motos estacionados");
			}
			estacionamentoNoBanco.setQuantidadeDeVagasParaMotos(estacionamentoUpdates.getQuantidadeDeVagasParaMotos());
		}
		return estacionamentoNoBanco;

	}
	public boolean existsById(Long id) {
		if (estacionamentoRepository.findById(id).isPresent()) {
			return true;
		}

		return false;
	}

}
