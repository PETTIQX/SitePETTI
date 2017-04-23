package br.ufc.quixada.petti.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import br.ufc.quixada.petti.model.Petiano;
import br.ufc.quixada.petti.service.PetianoService;

@Controller
@RequestMapping(path="/petianos")
public class PetianoController {

	@Autowired
	private PetianoService petianoService;
	
	@RequestMapping(path="/")
	public String index(Model model){
		List<Petiano> petianos = petianoService.listAll();
		model.addAttribute("petianos", petianos);
		return "private/petianos/index";
	}
	
	@RequestMapping(path="/cadastro")
	public String cadastro(){
		return "private/petianos/cadastro";
	}
	
}
