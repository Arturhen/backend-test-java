package br.com.estacionamento.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.estacionamento.domain.model.EstacionamentoDomainModel;
import br.com.estacionamento.domain.model.VeiculoDomainModel;

@Repository
public interface VeiculoRepository extends JpaRepository<VeiculoDomainModel, Long>{
	
	
	Optional<VeiculoDomainModel> findByPlaca(String placa);
	List<VeiculoDomainModel> findByEstacionamento(EstacionamentoDomainModel estacionamento);
	
}
