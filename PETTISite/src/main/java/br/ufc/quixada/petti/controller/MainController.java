package br.ufc.quixada.petti.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.ufc.quixada.petti.model.Petiano;
import br.ufc.quixada.petti.service.PetianoService;

@Controller
public class MainController {

	@Autowired
	private PetianoService petianoService;
	
	@RequestMapping(path="/")
	public String index(Model model){
		
		List<Petiano> petianos = petianoService.listActives();
		model.addAttribute("petianos", petianos);
		
		return "index";
	}

	@RequestMapping(path="/edital-bolsistas")
	public String edital(){
		return "edital-bolsistas";
	}
	
	@RequestMapping(path="/admin")
	public String admin(){
		return "private/index";
	}
	
	@RequestMapping(path="/login", method=RequestMethod.POST)
	public String login(String email, String senha, HttpSession session, RedirectAttributes redAttrs){
		Petiano petiano = petianoService.getByEmail(email);
		if(petiano != null){
			if(petiano.getSenha().equals(senha)){
				session.setAttribute("petiano", petiano);
				return "private/dashboard";
			}
			else{
				redAttrs.addFlashAttribute("erro", "Senha incorreta.");
			}
		}else{
			redAttrs.addFlashAttribute("erro", "E-mail não encontrado.");
		}
		return "redirect:/admin";
	}
	
	@RequestMapping(path="/logout")
	public String logout(HttpSession session){
		session.invalidate();
		return "redirect:/";
	}
	
}
