package br.com.estacionamento.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.estacionamento.domain.model.Estacionamento;

@Repository
public interface EstacionamentoRepository extends JpaRepository<Estacionamento, Long>{

	
	List<Estacionamento> findByNome(String nome);
	List<Estacionamento> findByNomeContaining(String nome);
	Optional<Estacionamento> findByCnpj(String cnpj);
	
}
