package br.ufc.quixada.petti.servers;

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
	
	public Petiano getById(Long id){
		return petianoRepository.findOne(id);
	}
	
}
