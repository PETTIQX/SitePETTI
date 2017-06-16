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

import br.ufc.quixada.petti.model.Atividade;
import br.ufc.quixada.petti.model.Petiano;
import br.ufc.quixada.petti.model.Tutor;
import br.ufc.quixada.petti.model.Usuario;
import br.ufc.quixada.petti.service.AtividadeService;
import br.ufc.quixada.petti.service.PetianoService;
import br.ufc.quixada.petti.service.TutorService;
import br.ufc.quixada.petti.service.UsuarioService;
import br.ufc.quixada.petti.util.CriptUtil;

@Controller
public class MainController {

	@Autowired
	private PetianoService petianoService;
	@Autowired
	private TutorService tutorService;
	@Autowired
	private AtividadeService atividadeService;
	@Autowired
	private UsuarioService usuarioService;
	
	@RequestMapping(path="/")
	public String index(Model model){
		List<Petiano> petianos = petianoService.listActives();
		List<Tutor> tutores = tutorService.listActives();
		List<Atividade> atividades = atividadeService.listAll();
		model.addAttribute("petianos", petianos);
		model.addAttribute("tutores", tutores);
		model.addAttribute("atividades", atividades);
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
	
	@RequestMapping(path="/dashboard")
	public String dashboard(HttpSession session){
		Usuario usuario = (Usuario)session.getAttribute("usuario");
		if(usuario instanceof Petiano){
			return "redirect:/dashboard-petiano";
		}
		return "redirect:/dashboard-tutor";
	}
	
	@RequestMapping(path="/dashboard-petiano")
	public String dashboardPetiano(HttpSession session){
		if(session.getAttribute("usuario") == null)
			return "redirect:/";
		return "private/petianos/dashboard";
	}
	
	@RequestMapping(path="/dashboard-tutor")
	public String dashboardTutor(HttpSession session){
		if(session.getAttribute("usuario") == null)
			return "redirect:/";
		return "private/tutores/dashboard";
	}
	
	@RequestMapping(path="/login", method=RequestMethod.POST)
	public String login(String email, String senha, HttpSession session, RedirectAttributes redAttrs){
		Usuario usuario = usuarioService.getByEmail(email);
		if(usuario != null){
			if(usuario.getSenha().equals(CriptUtil.hashSenha(senha))){
				session.setAttribute("usuario", usuario);
				if(usuario.getTipoUsuario().equals("petiano"))
					return "redirect:/dashboard-petiano";
				else if(usuario.getTipoUsuario().equals("tutor"))
					return "redirect:/dashboard-tutor";
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
	
	@RequestMapping(path="/cadastro")
	public String cadastro(HttpSession session){
		return "private/petianos/cadastro";
	}
	
}
