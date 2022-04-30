package br.com.alura.forum.controller.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.com.alura.forum.modelo.Topico;
import br.com.alura.forum.repository.TopicoRepository;

public class AtualizacaoTopicoForm {
	// tem q ser feito essa classe pra ele nao alterar dados q nao podem 
	//ser alterados. ex: id, nome do curso e etc...
	
	@NotNull @NotEmpty @Length(min = 5)// faz parte do bean validation todas essas anotacoes
	private String titulo;
	
	@NotNull @NotEmpty @Length(min = 10) 
	private String mensagem;
	
	

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public Topico atualizar(Long id, TopicoRepository topicoRepository) {
		// a logica é buscar os dados com base no id e alterar os dados modificados
		Topico topico = topicoRepository.getById(id);
		
		topico.setTitulo(this.titulo);
		topico.setMensagem(this.mensagem);
		topico.setTitulo(this.titulo);
		
		return topico;
	}
	
	
	
	
}
