package br.ufc.quixada.petti.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufc.quixada.petti.model.Usuario;
import br.ufc.quixada.petti.repository.UsuarioRepository;

@Service
public class PetianoService {

	@Autowired
	private UsuarioRepository petianoRepository;
	
	public List<Usuario> listAll(){
		return petianoRepository.findAll();
	}
	
	public List<Usuario> listActives(){
		return petianoRepository.findByAtivoOrderByNomeAsc(true);
	}
	
	public Usuario getById(Long id){
		return petianoRepository.findOne(id);
	}
	
	public Usuario getByEmail(String email){
		List<Usuario> petianos = petianoRepository.findByEmailLike(email);
		if(!petianos.isEmpty())
			return petianos.get(0);
		else
			return null;
	}
	
	public void savePetiano(Usuario petiano){
		petianoRepository.save(petiano);
	}
	
	public void deletePetiano(Long id){
		petianoRepository.delete(id);
	}
	
}
