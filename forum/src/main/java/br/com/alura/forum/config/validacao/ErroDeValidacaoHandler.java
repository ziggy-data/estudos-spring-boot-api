package br.com.alura.forum.config.validacao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErroDeValidacaoHandler {

	// toda vez q tiver um erro desse tipo, ("nao respeitando o minimo d caracter em titulo")
	// o spring vai chamar o metodo handler, considerando que o erro é 400, ele retorna pro cliente 
	//esse tratamento e devolve erro 400 pq passamos no ResponseStatus, e melhora o json de resposta
	
	@Autowired
	private MessageSource messageSource;	

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public List<ErroDeFormularioDto> handle(MethodArgumentNotValidException exception){
		List<ErroDeFormularioDto> dto = new ArrayList<>();
		
		
		List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
		fieldErrors.forEach(e-> { // lambda
			String mensagem = messageSource.getMessage(e, LocaleContextHolder.getLocale());
							// locale é so pra trazer a mensagem no idioma correto
			ErroDeFormularioDto erro = new ErroDeFormularioDto(e.getField(), mensagem); 
			dto.add(erro);
		});
		return dto;
	}
	
	
}
