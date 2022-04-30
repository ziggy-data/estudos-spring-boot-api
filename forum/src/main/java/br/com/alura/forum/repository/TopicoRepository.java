package br.com.alura.forum.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.alura.forum.modelo.Topico;

public interface TopicoRepository extends JpaRepository<Topico, Long> {

	List<Topico> findByCursoNome(String nomeCurso);// tem q seguir a logica,findBy <entidade de relacionamento> <Nome do atributo>, CursoNome
	//se nao seguir o padrao d nome ele nao consegue fazer a busca
	
	
	@Query("SELECT t FROM Topico t WHERE t.curso.nome = :nomeCurso")
    List<Topico> carregarPorNomeDoCurso(@Param("nomeCurso") String nomeCurso);
	// se nao seguir a nomenclatura, tem q escrever na mao e seguir a sintaxe do JPQL
	
	
	//uma aplicação que utiliza o Spring Data JPA, o acesso ao banco 
	//de dados é feito com a criação de uma interface, seguindo o padrão Repository.
	
	// a aplicação o Spring cria dinâmicamente uma classe que implementa essa
	//interface. Essa classe criada que será injetada e terá os métodos implementados de fato.
}//o Spring Boot tem uma configuração que automaticamente faz o scan 
//de quem herda de tais interfaces
