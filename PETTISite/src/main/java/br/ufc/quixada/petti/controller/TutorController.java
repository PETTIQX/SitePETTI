package br.ufc.quixada.petti.controller;

import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.ufc.quixada.petti.model.Tutor;
import br.ufc.quixada.petti.model.Usuario;
import br.ufc.quixada.petti.service.StorageFileService;
import br.ufc.quixada.petti.service.UsuarioService;

@Controller
@RequestMapping(path="/tutores")
public class TutorController {

	@Autowired
	private StorageFileService storageFileService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@RequestMapping(path="/editar", method=RequestMethod.POST, params="action=editar")		
	public String editar(Tutor tutor, HttpSession session, 
			@RequestParam("fotoCaricaturaFile") MultipartFile fotoCaricatura, 
			@RequestParam("fotoRealFile") MultipartFile fotoReal, 
			RedirectAttributes redAttrs){

		String path = PetianoController.class.getClassLoader().getResource("static/img/").getPath();
		Usuario tutorAntigo = (Usuario)session.getAttribute("usuario");
		
		//Deleta a foto caricatura atual caso o usuário realize update de uma nova foto caricatura
		if(!fotoCaricatura.isEmpty()){
			String fotoCaricaturaFileName = tutorAntigo.getFotoCaricatura();
			try {
				storageFileService.deleteFile(path + "fotoCaricatura/" + fotoCaricaturaFileName);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//Salva nova foto caricatura
			fotoCaricaturaFileName = tutor.getNome().replaceAll(" ", "_") + "_caricatura_" + fotoCaricatura.getOriginalFilename();
			tutor.setFotoCaricatura(fotoCaricaturaFileName);
			try {
				storageFileService.saveFile(fotoCaricatura, path + "fotoCaricatura/" + fotoCaricaturaFileName);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			tutor.setFotoCaricatura(tutorAntigo.getFotoCaricatura());
		}
		
		//Deleta a foto real atual caso o usuário realize update de uma nova foto real
		if(!fotoReal.isEmpty()){
			String fotoRealFileName = tutorAntigo.getFotoReal();
			try {
				storageFileService.deleteFile(path + "fotoReal/" + fotoRealFileName);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//Salva nova foto real
			fotoRealFileName = tutor.getNome().replaceAll(" ", "_") + "_real_" + fotoReal.getOriginalFilename();
			tutor.setFotoReal(fotoRealFileName);
			try {
				storageFileService.saveFile(fotoReal, path + "fotoReal/" + fotoRealFileName);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			tutor.setFotoReal(tutorAntigo.getFotoReal());
		}
		
		/* O fato de ter o mesmo id de uma tupla já cadastrada 
		*  faz que a tupla seja apenas atualizada ao ser cadastrada novamente
		*/
		tutor.setSenha(tutorAntigo.getSenha());
		tutor.setAtivo(tutorAntigo.isAtivo());
		tutor.setId(tutorAntigo.getId());
		usuarioService.saveUsuario(tutor);
		session.setAttribute("usuario", tutor);
		return "redirect:/dashboard-tutor";
	}
	
	@RequestMapping(path="/editar", method=RequestMethod.POST, params="action=cancelar")		
	public String cancelarEdicao(){
		return "redirect:/dashboard-tutor";
	}
	
}
