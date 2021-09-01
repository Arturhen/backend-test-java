package br.com.estacionamento.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.estacionamento.domain.model.EstacionamentoDomainModel;

@Repository
public interface EstacionamentoRepository extends JpaRepository<EstacionamentoDomainModel, Long>{

	List<EstacionamentoDomainModel> findByNome(String nome);
	List<EstacionamentoDomainModel> findByNomeContaining(String nome);
	Optional<EstacionamentoDomainModel> findByCnpj(String cnpj);
	
}
