package br.ufc.quixada.petti.controller;

import java.io.IOException;
import java.util.List;

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
import br.ufc.quixada.petti.service.PetianoService;
import br.ufc.quixada.petti.service.StorageFileService;
import br.ufc.quixada.petti.util.CriptUtil;

@Controller
@RequestMapping(path="/petianos")
public class PetianoController {

	@Autowired
	private PetianoService petianoService;
	
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
			String fotoCaricaturaFileName = petiano.getNome().replaceAll(" ", "_") + "_caricatura_" + fotoCaricatura.getOriginalFilename();
			String fotoRealFileName = petiano.getNome().replaceAll(" ", "_") + "_real_" + fotoReal.getOriginalFilename();
			
			petiano.setFotoCaricatura(fotoCaricaturaFileName);
			petiano.setFotoReal(fotoRealFileName);
			petiano.setAtivo(true);
			
			try {
				storageFileService.saveFile(fotoCaricatura, path + "fotoCaricatura/" + fotoCaricaturaFileName);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				storageFileService.saveFile(fotoReal, path + "fotoReal/" + fotoRealFileName);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
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
	
	@RequestMapping(path="/excluir/{id}")
	public String excluirPetiano(@PathVariable("id") Long id){
		Petiano petiano = petianoService.getById(id);
		
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
		
		petianoService.deletePetiano(id);
		return "redirect:/petianos/";
	}
	
}
