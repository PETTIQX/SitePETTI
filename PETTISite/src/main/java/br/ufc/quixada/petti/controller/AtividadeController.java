package br.ufc.quixada.petti.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path="/atividades")
public class AtividadeController {

	@RequestMapping(path="/")
	public String index(){
		return "private/atividades/index";
	}
	
	@RequestMapping(path="/cadastro")
	public String cadastro(){
		return "private/atividades/cadastro";
	}
	
}
