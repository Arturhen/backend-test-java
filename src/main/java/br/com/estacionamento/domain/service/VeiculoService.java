package br.com.estacionamento.domain.service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.estacionamento.domain.exception.BusinessException;
import br.com.estacionamento.domain.model.EstacionamentoDomainModel;
import br.com.estacionamento.domain.model.VeiculoDomainModel;
import br.com.estacionamento.domain.model.VeiculoType;
import br.com.estacionamento.domain.repository.VeiculoRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class VeiculoService {

	private VeiculoRepository veiculoRepository;
	private VeiculoService veiculosServices;
	private EstacionamentoService crudEstacionamento;

	public VeiculoDomainModel create(VeiculoDomainModel veiculo) {

		veiculosServices.existWithSamePlate(veiculo);

		EstacionamentoDomainModel estacionamento = crudEstacionamento.find(veiculo.getEstacionamento().getId());

		veiculosServices.updateVagasEstacionamento(estacionamento, veiculo);

		veiculo.setHorarioEntrada(OffsetDateTime.now());// set normalmente nao é publico olhar lombok
		veiculo.setEstacionamento(estacionamento); // set normalmente nao é publico olhar lombok

		return veiculoRepository.save(veiculo);
	}

	public VeiculoDomainModel findById(Long id) {
		return veiculoRepository.findById(id).orElseThrow(() -> new BusinessException("Veiculo nao encontrado"));
	}

	public List<VeiculoDomainModel> list() {
		return veiculoRepository.findAll();
	}

	@Transactional
	public VeiculoDomainModel update(Long id, VeiculoDomainModel veiculo) {

		veiculo.setId(id);

		veiculosServices.existWithSamePlate(veiculo);

		VeiculoDomainModel veiculoAntigo = veiculoRepository.findById(id).get();
		EstacionamentoDomainModel estacionamentoNovo = crudEstacionamento.find(veiculo.getEstacionamento().getId());

		if (veiculo.getEstacionamento() != veiculoAntigo.getEstacionamento()) {
			veiculosServices.updateVagasEstacionamento(estacionamentoNovo, veiculo);
			veiculosServices.deleteVeiculoInEstacionamento(veiculoAntigo.getEstacionamento(), veiculoAntigo);
		}

		veiculo.setHorarioEntrada(veiculoAntigo.getHorarioEntrada());

		return veiculoRepository.save(veiculo);

	}

	@Transactional
	public void deleteByID(Long id) {
		Optional<VeiculoDomainModel> veiculoOptional = veiculoRepository.findById(id);

		if (!veiculoOptional.isPresent()) {
			throw new BusinessException("veiculo a ser deletado não existe");
		}

		VeiculoDomainModel veiculo = veiculoOptional.get();

		EstacionamentoDomainModel estacionamento = veiculo.getEstacionamento();

		veiculosServices.deleteVeiculoInEstacionamento(estacionamento, veiculo);

		veiculoRepository.delete(veiculo);

	}

	public boolean existsById(Long id) {
		if (veiculoRepository.findById(id).isPresent()) {
			return true;
		}

		return false;
	}

	public void existWithSamePlate(VeiculoDomainModel veiculo) {
		Optional<VeiculoDomainModel> veiculoNoBanco = veiculoRepository.findByPlaca(veiculo.getPlaca());

		if (veiculoNoBanco.isPresent()) {
			if (!veiculoNoBanco.get().equals(veiculo)) {
				throw new BusinessException("Ja existe um Veiculo com Essa placa Estacionado");
			}
		}
	}

	public void updateVagasEstacionamento(EstacionamentoDomainModel estacionamento, VeiculoDomainModel veiculo) {
		// SWITCH DICA DO GIGA
		if (veiculo.getTipo() == VeiculoType.CARRO) {
			this.updateVagasDeCarro(estacionamento, veiculo);
		}

		if (veiculo.getTipo() == VeiculoType.MOTO) {
			this.updateVagasDeMoto(estacionamento, veiculo);
		}
	}

	private void updateVagasDeMoto(EstacionamentoDomainModel estacionamento, VeiculoDomainModel veiculo) {
		if (estacionamento.getQuantidadeDeVagasParaMotos() <= estacionamento.getQuantidadeDeMotosEstacionadas()) {
			throw new BusinessException("Esse estacionamento esta lotado de Motos.");
		}
		estacionamento.setQuantidadeDeMotosEstacionadas(estacionamento.getQuantidadeDeMotosEstacionadas() + 1);
	}

	private void updateVagasDeCarro(EstacionamentoDomainModel estacionamento, VeiculoDomainModel veiculo) {
		if (estacionamento.getQuantidadeDeVagasParaCarros() <= estacionamento.getQuantidadeDeCarrosEstacionados()) {
			throw new BusinessException("Esse estacionamento esta lotado de Carros.");
		}
		estacionamento.setQuantidadeDeCarrosEstacionados(estacionamento.getQuantidadeDeCarrosEstacionados() + 1);
	}

	public void deleteVeiculoInEstacionamento(EstacionamentoDomainModel estacionamento, VeiculoDomainModel veiculo) {
		if (veiculo.getTipo() == VeiculoType.CARRO) {
			estacionamento.setQuantidadeDeCarrosEstacionados(estacionamento.getQuantidadeDeCarrosEstacionados() - 1);
		}

		if (veiculo.getTipo() == VeiculoType.MOTO) {
			estacionamento.setQuantidadeDeMotosEstacionadas(estacionamento.getQuantidadeDeMotosEstacionadas() - 1);
		}
	}

}
