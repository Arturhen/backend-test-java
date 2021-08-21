package br.com.estacionamento.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.estacionamento.domain.model.Estacionamento;
import br.com.estacionamento.domain.model.Veiculo;

@Repository
public interface VeiculoRepository extends JpaRepository<Veiculo, Long>{
	
	
	Optional<Veiculo> findByPlaca(String placa);

}
