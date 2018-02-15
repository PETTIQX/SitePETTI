package br.ufc.quixada.petti.controller;

//import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.ufc.quixada.petti.model.Petiano;
import br.ufc.quixada.petti.model.Usuario;
import br.ufc.quixada.petti.service.StorageFileService;
import br.ufc.quixada.petti.service.UsuarioService;
import br.ufc.quixada.petti.util.CriptUtil;

@Controller
@RequestMapping(path="/usuarios")
public class UsuarioController {

	@Autowired
	StorageFileService storageFileService;
	
	@Autowired
	UsuarioService usuarioService;
	
	@RequestMapping(path="/edicao")
	public String edicao(HttpSession session){
		Usuario usuario = (Usuario)session.getAttribute("usuario");
		if(usuario != null){
			if(usuario instanceof Petiano)
				return "private/petianos/edicao";
			else return "private/tutores/edicao";
		}
		else{
			return "redirect:/";
		}
	}
	
	@RequestMapping(path="/alterar-senha")
	public String alterarSenhaForm(){
		return "/private/usuarios/alterar-senha";
	}
	
	@RequestMapping(path="/alterarSenha", params="action=alterar")
	public String alterarSenha(@RequestParam(name="senhaAtual")String senhaAtual, @RequestParam(name="novaSenha")String novaSenha, @RequestParam(name="confirmaSenha")String confirmaSenha, HttpSession session, RedirectAttributes redAttrs){
		Usuario usuario = (Usuario)session.getAttribute("usuario");
		
		if(usuario.getSenha().equals(CriptUtil.hashSenha(senhaAtual))){
			if(novaSenha.equals(confirmaSenha)){
				usuario.setSenha(CriptUtil.hashSenha(novaSenha));
				usuarioService.saveUsuario(usuario);
				session.setAttribute("usuario", usuario);
				return "redirect:/dashboard";
			}
			else{
				System.out.println("Erro de senhas novas");
				redAttrs.addFlashAttribute("erro", "Senhas não coincidem.");
				return "redirect:/usuarios/alterar-senha";
			}
		}
		else{
			System.out.println("Erro senha antiga");
			redAttrs.addFlashAttribute("erro", "Senha incorreta.");
			return "redirect:/usuarios/alterar-senha";
		}
	}
	
	@RequestMapping(path="/alterarSenha", params="action=cancelar")
	public String cancelarAlterarSenha(@RequestParam(name="senhaAtual")String senhaAtual, @RequestParam(name="novaSenha")String novaSenha, @RequestParam(name="confirmaSenha")String confirmaSenha, HttpSession session, RedirectAttributes redAttrs){
		return "redirect:/dashboard";
	}
	
}
