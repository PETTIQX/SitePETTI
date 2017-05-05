package br.ufc.quixada.petti.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	
	@RequestMapping(path="/cadastrar", method=RequestMethod.POST, params="action=cadastrar")		
	public String cadastrar(Petiano petiano, @RequestParam(name="confirmaSenha") String confirmaSenha, RedirectAttributes redAttrs){
		if(petiano.getSenha().equals(confirmaSenha)){
			petianoService.savePetiano(petiano);
			return "redirect:/petianos/";
		}else{
			redAttrs.addFlashAttribute("erro", "Senhas não coincidem");
			return "redirect:/petianos/cadastro";
		}
	}
	
	@RequestMapping(path="/cadastrar", method=RequestMethod.POST, params="action=cancelar")		
	public String cancelarCadastro(){
		return "redirect:/petianos/";
	}
	
}
