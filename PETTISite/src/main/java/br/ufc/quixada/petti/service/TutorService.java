package br.ufc.quixada.petti.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufc.quixada.petti.model.Tutor;
import br.ufc.quixada.petti.repository.TutorRepository;

@Service
public class TutorService {

	@Autowired
	private TutorRepository tutorRepository;
	
	public List<Tutor> listAll(){
		return tutorRepository.findAll();
	}
	
	public List<Tutor> listActives(){
		return tutorRepository.findByAtivoOrderByNomeAsc(true);
	}
	
	public Tutor getById(Long id){
		return tutorRepository.findOne(id);
	}
	
	public Tutor getByEmail(String email){
		List<Tutor> tutor = tutorRepository.findByEmailLike(email);
		if(!tutor.isEmpty())
			return tutor.get(0);
		else
			return null;
	}
	
}
