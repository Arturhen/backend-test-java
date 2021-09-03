package br.com.estacionamento.domain.service;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import br.com.estacionamento.domain.exception.BusinessException;
import br.com.estacionamento.domain.model.EstacionamentoDomainModel;
import br.com.estacionamento.domain.repository.EstacionamentoRepository;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;


@AllArgsConstructor
@Service
public class EstacionamentoAuthenticationService {

	private EstacionamentoRepository estacionamentoRepository;
	private TokenService tokenService;

	@Transactional
	public EstacionamentoDomainModel authenticate(EstacionamentoDomainModel estacionamentoLogin, String authorization) {
		EstacionamentoDomainModel estacionamento = estacionamentoRepository.findByCnpj(estacionamentoLogin.getCnpj())
				.orElseThrow(BusinessException::new);
				
		if (estacionamentoLogin.getSenha().equals(estacionamento.getSenha()) && !authorization.isEmpty() && this.validate(authorization) ) {
//			String token = tokenService.generateToken(estacionamento);
//			estacionamento.setToken(token);
			return estacionamento;
		}

		throw new BusinessException("Senha ou CNPJ invalidos");

	}

	private boolean validate(String authorization) {
		try {
		String token = authorization.replace("Barrer ", "");
		Claims claims = tokenService.decodeTokem(token);
		
		if(claims.getExpiration().before(new Date(System.currentTimeMillis()))) {
			throw new BusinessException("Token Expirado");
		}
		
		return true;
		}catch(Exception e) {
			throw new BusinessException("Token Invalido");
		}
		
		
		
	}

}
