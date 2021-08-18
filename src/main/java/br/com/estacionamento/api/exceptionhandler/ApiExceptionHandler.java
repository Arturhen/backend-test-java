package br.com.estacionamento.api.exceptionhandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.estacionamento.domain.exception.BusinessException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler{

	private MessageSource messageSource;
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		List<Field> fields = new ArrayList<>();
		
		ex.getBindingResult().getAllErrors()
			.forEach(err -> fields.add(new Field(((FieldError) err).getField(),messageSource.getMessage(err, LocaleContextHolder.getLocale()))));;
		
		Problem problem = new Problem();
		problem.setStatus(status.value());
		problem.setDateHour(LocalDateTime.now());
		problem.setTitle("Um ou mais campos estão invalidos. Verifique o preenchimento");
		problem.setFields(fields);
		
		return super.handleExceptionInternal(ex, problem, headers, status, request);
	}
	
	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<Object> handleBusiness(BusinessException ex, WebRequest request){
		
		HttpStatus status = HttpStatus.BAD_REQUEST;
		
//		Abstrair problema
		Problem problem = new Problem();
		problem.setStatus(status.value());
		problem.setDateHour(LocalDateTime.now());
		problem.setTitle(ex.getMessage());
		
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
}
