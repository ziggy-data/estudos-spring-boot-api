package br.com.alura.forum.controller;

import java.net.URI;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.alura.forum.controller.form.AtualizacaoTopicoForm;
import br.com.alura.forum.controller.form.TopicoForm;
import br.com.alura.forum.dto.DetalhesDoTopicoDto;
import br.com.alura.forum.dto.TopicoDto;
import br.com.alura.forum.modelo.Topico;
import br.com.alura.forum.repository.CursoRepository;
import br.com.alura.forum.repository.TopicoRepository;

@RestController
@RequestMapping("/topicos")//esse controller responde a tudo q for "/topicos"
public class TopicosController {
	
	@Autowired // é tipo importar 
	private TopicoRepository topicoRepository;
	
	@Autowired
	private CursoRepository cursoRepository;

	@GetMapping//se colocar um nome d curso na url exemplo "/topicos?nomeCurso=Spring" ele aceita
	public List<TopicoDto> lista(String nomeCurso){
		if(nomeCurso == null) {
			List<Topico> topicos = topicoRepository.findAll();
			return TopicoDto.converter(topicos);
		}else {
			List<Topico> topicos = topicoRepository.findByCursoNome(nomeCurso);
			return TopicoDto.converter(topicos);//findBy<entidade de relacionamento><Nome do atributo>, CursoNome
		}
		/*
		List<Topico> topicos = topicoRepository.findByTitulo(nomeTitulo);// pra usar o do spring tem q seguir esse padao d nomenclatura
		return TopicoDto.converter(topicos);//NÃO EXITE findByTitulo feito por nos MAS ELE ACEITA ESSA NOMENCLATURA SE TIVER O ATRIBUTO NA TABELA, TEM Q DECLARAR O METODO ABSTRATO NA INTERFACE
		nesse exemplo titulo pertence a entidade entao nao tem q ir em outra tabela
	*/
	}
	
	@PostMapping//ja que é post os dados vao pelo corpo da pagina
	@Transactional//pra ter certeza que vai funcionar colocamos nos deletar, atualizar e cadastrar pq se mudar o bd ainda funciona
	public ResponseEntity<TopicoDto> cadastrar(@RequestBody @Valid TopicoForm form, UriComponentsBuilder uriBuilder) {//form dados que chegam do cliente pra api
		Topico topico = form.converter(cursoRepository);//@Valid: qnd injetar os dados do form ele roda as validaçoes, se nao valer o cliente recebe erro 400
		topicoRepository.save(topico);
		
		URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri(); 
		return  ResponseEntity.created(uri).body(new TopicoDto(topico));
	}
	
	@GetMapping("/{id}")//id virou um parametro dinamico
	public DetalhesDoTopicoDto detalhar(@PathVariable Long id){// pathvariable é a url
	//ele associa o nome id no getmapping com o id parametro com path variable
	//se eu colocasse outro nome no parametro teria q colocar ex: "@PathVariable("id") Long codigo" 
		Topico topico = topicoRepository.getById(id);
		return new DetalhesDoTopicoDto(topico);
	}
	
	@PutMapping("/{id}")//vou usar o put pq n sei se ja existe - e é mais usado no mercado
	//diferenca do put e patch: put muda tudo e patch so algumas coisas(que eu quiser)
	@Transactional//avisa o spring commitar no bd qnd terminar o metodo se nao tiverr uma exception
	public ResponseEntity<TopicoDto> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizacaoTopicoForm form){
		Topico topico = form.atualizar(id, topicoRepository);//qnd termina o metodo ele ja atualizou no bd
		
		return ResponseEntity.ok(new TopicoDto(topico));// parametro ok vai ser devolvido como resposta pelo servidor
	}
	
	
	@DeleteMapping("/{id}")
	@Transactional//pra ter certeza que vai funcionar colocamos nos deletar, atualizar e cadastrar pq se mudar o bd ainda funciona
	public ResponseEntity<?> remover(@PathVariable Long id){
		topicoRepository.deleteById(id);
		
		return ResponseEntity.ok().build();
	}
		
}
	
	
	

