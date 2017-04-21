package br.ufc.quixada.petti.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import br.ufc.quixada.petti.model.Petiano;
import br.ufc.quixada.petti.servers.PetianoService;

@Controller
public class MainController {

	@Autowired
	private PetianoService petianoService;
	
	@RequestMapping(path="/")
	public String index(Model model){
		
		List<Petiano> petianos = petianoService.listAll();
		model.addAttribute("petianos", petianos);
		
		return "index";
	}

	@RequestMapping(path="/edital-bolsistas")
	public String edital(){
		return "edital-bolsistas";
	}
	
	@RequestMapping("/admin")
	public String admin(){
		return "private/index";
	}
	
}
