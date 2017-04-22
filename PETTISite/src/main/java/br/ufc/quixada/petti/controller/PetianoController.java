package br.ufc.quixada.petti.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path="/petianos")
public class PetianoController {

	@RequestMapping(path="/")
	public String index(){
		return "private/petianos/index";
	}
	
	@RequestMapping(path="/cadastro")
	public String cadastro(){
		return "private/petianos/cadastro";
	}
	
}
