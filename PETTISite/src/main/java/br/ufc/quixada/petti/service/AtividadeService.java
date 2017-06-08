package br.ufc.quixada.petti.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufc.quixada.petti.model.Atividade;
import br.ufc.quixada.petti.repository.AtividadeRepository;

@Service
public class AtividadeService {

	@Autowired
	private AtividadeRepository atividadeRepository;
	
	public List<Atividade> listAll(){
		return atividadeRepository.findByOrderByNomeAsc();
	}
	
	public Atividade getById(Long id){
		return atividadeRepository.findOne(id);
	}
	
	public void saveAtividade(Atividade atividade){
		atividadeRepository.save(atividade);
	}
	
	public void deleteAtividade(Long id){
		atividadeRepository.delete(id);
	}
	
}
