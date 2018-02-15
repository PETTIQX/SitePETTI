package br.ufc.quixada.petti.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.ufc.quixada.petti.model.Petiano;
//import br.ufc.quixada.petti.model.Tutor;
import br.ufc.quixada.petti.model.Usuario;
import br.ufc.quixada.petti.service.PetianoService;
import br.ufc.quixada.petti.service.StorageFileService;
//import br.ufc.quixada.petti.service.TutorService;
import br.ufc.quixada.petti.service.UsuarioService;
import br.ufc.quixada.petti.util.CriptUtil;

@Controller
@RequestMapping(path="/petianos")
public class PetianoController {

	@Autowired
	private PetianoService petianoService;
	
	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private StorageFileService storageFileService;

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
	public String cadastrar(Petiano petiano, @RequestParam(name="confirmaSenha") String confirmaSenha, 
			@RequestParam("fotoCaricaturaFile") MultipartFile fotoCaricatura, 
			@RequestParam("fotoRealFile") MultipartFile fotoReal, 
			RedirectAttributes redAttrs){

		if(petiano.getSenha().equals(confirmaSenha)){
			petiano.setSenha(CriptUtil.hashSenha(petiano.getSenha()));
			
			String path = PetianoController.class.getClassLoader().getResource("static/img/").getPath();
			
			if(!fotoCaricatura.isEmpty()){
				String fotoCaricaturaFileName = petiano.getNome().replaceAll(" ", "_") + "_caricatura_" + fotoCaricatura.getOriginalFilename();
				petiano.setFotoCaricatura(fotoCaricaturaFileName);
				try {
					storageFileService.saveFile(fotoCaricatura, path + "fotoCaricatura/" + fotoCaricaturaFileName);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if(!fotoReal.isEmpty()){
				String fotoRealFileName = petiano.getNome().replaceAll(" ", "_") + "_real_" + fotoReal.getOriginalFilename();
				petiano.setFotoReal(fotoRealFileName);
				try {
					storageFileService.saveFile(fotoReal, path + "fotoReal/" + fotoRealFileName);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			petiano.setAtivo(true);
			usuarioService.saveUsuario(petiano);
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
	
	@RequestMapping(path="/editar", method=RequestMethod.POST, params="action=editar")		
	public String editar(Petiano petiano, HttpSession session, 
			@RequestParam("fotoCaricaturaFile") MultipartFile fotoCaricatura, 
			@RequestParam("fotoRealFile") MultipartFile fotoReal, 
			RedirectAttributes redAttrs){

		String path = PetianoController.class.getClassLoader().getResource("static/img/").getPath();
		Usuario petianoAntigo = (Usuario)session.getAttribute("usuario");
		
		//Deleta a foto caricatura atual caso o usuário realize update de uma nova foto caricatura
		if(!fotoCaricatura.isEmpty()){
			String fotoCaricaturaFileName = petianoAntigo.getFotoCaricatura();
			try {
				storageFileService.deleteFile(path + "fotoCaricatura/" + fotoCaricaturaFileName);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//Salva nova foto caricatura
			fotoCaricaturaFileName = petiano.getNome().replaceAll(" ", "_") + "_caricatura_" + fotoCaricatura.getOriginalFilename();
			petiano.setFotoCaricatura(fotoCaricaturaFileName);
			try {
				storageFileService.saveFile(fotoCaricatura, path + "fotoCaricatura/" + fotoCaricaturaFileName);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			petiano.setFotoCaricatura(petianoAntigo.getFotoCaricatura());
		}
		
		//Deleta a foto real atual caso o usuário realize update de uma nova foto real
		if(!fotoReal.isEmpty()){
			String fotoRealFileName = petianoAntigo.getFotoReal();
			try {
				storageFileService.deleteFile(path + "fotoReal/" + fotoRealFileName);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//Salva nova foto real
			fotoRealFileName = petiano.getNome().replaceAll(" ", "_") + "_real_" + fotoReal.getOriginalFilename();
			petiano.setFotoReal(fotoRealFileName);
			try {
				storageFileService.saveFile(fotoReal, path + "fotoReal/" + fotoRealFileName);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			petiano.setFotoReal(petianoAntigo.getFotoReal());
		}
		
		/* O fato de ter o mesmo id de uma tupla já cadastrada 
		*  faz que a tupla seja apenas atualizada ao ser cadastrada novamente
		*/
		petiano.setSenha(petianoAntigo.getSenha());
		petiano.setAtivo(petianoAntigo.isAtivo());
		petiano.setId(petianoAntigo.getId());
		usuarioService.saveUsuario(petiano);
		session.setAttribute("usuario", petiano);
		return "redirect:/dashboard-petiano";
	}
	
	@RequestMapping(path="/editar", method=RequestMethod.POST, params="action=cancelar")		
	public String cancelarEdicao(){
		return "redirect:/dashboard-petiano";
	}
	
	@RequestMapping(path="/excluir/{id}")
	public String excluirPetiano(@PathVariable("id") Long id){
		Usuario petiano = petianoService.getById(id);

		String path = PetianoController.class.getClassLoader().getResource("static/img/").getPath();
		String fotoCaricaturaFileName = petiano.getFotoCaricatura();
		String fotoRealFileName = petiano.getFotoReal();

		try {
			storageFileService.deleteFile(path + "fotoCaricatura/" + fotoCaricaturaFileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			storageFileService.deleteFile(path + "fotoReal/" + fotoRealFileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		usuarioService.deleteUsuario(id);
		return "redirect:/petianos/";
	}

}
