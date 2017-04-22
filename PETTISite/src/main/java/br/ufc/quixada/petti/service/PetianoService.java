package br.ufc.quixada.petti.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufc.quixada.petti.model.Petiano;
import br.ufc.quixada.petti.repository.PetianoRepository;

@Service
public class PetianoService {

	@Autowired
	private PetianoRepository petianoRepository;
	
	public List<Petiano> listAll(){
		return petianoRepository.findAll();
	}
	
	public List<Petiano> listActives(){
		return petianoRepository.findByAtivoOrderByNomeAsc(true);
	}
	
	public Petiano getById(Long id){
		return petianoRepository.findOne(id);
	}
	
	public Petiano getByEmail(String email){
		List<Petiano> petianos = petianoRepository.findByEmailLike(email);
		if(!petianos.isEmpty())
			return petianos.get(0);
		else
			return null;
	}
	
}
