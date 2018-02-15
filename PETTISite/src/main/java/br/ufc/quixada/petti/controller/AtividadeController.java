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
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.ufc.quixada.petti.model.Atividade;
//import br.ufc.quixada.petti.model.Petiano;
import br.ufc.quixada.petti.service.AtividadeService;
import br.ufc.quixada.petti.service.StorageFileService;
//import br.ufc.quixada.petti.util.CriptUtil;

@Controller
@RequestMapping(path="/atividades")
public class AtividadeController {

	@Autowired
	private StorageFileService storageFileService;

	@Autowired
	private AtividadeService atividadeService;

	@RequestMapping(path="/")
	public String index(Model model){
		List<Atividade> atividades = atividadeService.listAll();
		model.addAttribute("atividades", atividades);
		return "private/atividades/index";
	}

	@RequestMapping(path="/cadastro")
	public String cadastro(){
		return "private/atividades/cadastro";
	}

	@RequestMapping(path="/edicao/{id}")
	public String edicao(@PathVariable("id") Long id, Model model){
		Atividade atividade = atividadeService.getById(id);
		model.addAttribute("atividade", atividade);
		return "private/atividades/edicao";
	}
	
	@RequestMapping(path="/cadastrar", method=RequestMethod.POST, params="action=cadastrar")		
	public String cadastrar(Atividade atividade, @RequestParam("iconeFile") MultipartFile iconeFile){

		Atividade atividadeAntiga = atividadeService.getById(atividade.getId());
		
		if(!iconeFile.isEmpty()){
			String path = AtividadeController.class.getClassLoader().getResource("static/img/").getPath();
			try {
				storageFileService.deleteFile(atividadeAntiga.getIcone());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			String iconeFileName = atividade.getNome().replaceAll(" ", "_") + "_icone_atividade_" + iconeFile.getOriginalFilename();
			atividade.setIcone(iconeFileName);
			try {
				storageFileService.saveFile(iconeFile, path + "iconesAtividades/" + iconeFileName);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			atividade.setIcone(atividadeAntiga.getIcone());
		}

		atividadeService.saveAtividade(atividade);
		return "redirect:/atividades/";
	}

	@RequestMapping(path="/cadastrar", method=RequestMethod.POST, params="action=cancelar")		
	public String cancelarCadastro(){
		return "redirect:/atividades/";
	}
	
	@RequestMapping(path="/editar", method=RequestMethod.POST, params="action=editar")		
	public String editar(Atividade atividade, @RequestParam("iconeFile") MultipartFile iconeFile){

		String path = AtividadeController.class.getClassLoader().getResource("static/img/").getPath();

		if(!iconeFile.isEmpty()){
			String iconeFileName = atividade.getNome().replaceAll(" ", "_") + "_icone_atividade_" + iconeFile.getOriginalFilename();
			atividade.setIcone(iconeFileName);
			try {
				storageFileService.saveFile(iconeFile, path + "iconesAtividades/" + iconeFileName);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		atividadeService.saveAtividade(atividade);
		return "redirect:/atividades/";
	}

	@RequestMapping(path="/editar", method=RequestMethod.POST, params="action=cancelar")		
	public String cancelarEdicao(){
		return "redirect:/atividades/";
	}
	
	@RequestMapping(path="/excluir/{id}")
	public String excluirAtividade(@PathVariable("id") Long id){
		
		Atividade atividade = atividadeService.getById(id);
		String path = AtividadeController.class.getClassLoader().getResource("static/img/").getPath();
		
		if(atividade.getIcone() != null){
			String iconeFileName = atividade.getIcone();
			try {
				storageFileService.deleteFile(path + "iconesAtividades/" + iconeFileName);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		atividadeService.deleteAtividade(id);
		return "redirect:/atividades/";
		
	}

}
