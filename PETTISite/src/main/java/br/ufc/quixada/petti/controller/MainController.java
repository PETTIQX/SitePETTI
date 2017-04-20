package br.ufc.quixada.petti.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

	@RequestMapping(path="/")
	public String index(){
		return "index";
	}

	@RequestMapping(path="/edital-bolsistas")
	public String edital(){
		return "edital-bolsistas";
	}
	
}
