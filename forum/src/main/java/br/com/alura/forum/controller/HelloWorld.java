package br.com.alura.forum.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloWorld {

	@RequestMapping("/")//quando cair nesse metodo vai me devolver essa string
	@ResponseBody//sem essa anotacao ele considera que o retorno é uma pagina e vai tentar achar ela
	public String hello() {
		return "Hello World";
	}
	
}
