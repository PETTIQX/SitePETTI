package br.ufc.quixada.petti.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufc.quixada.petti.model.Usuario;
import br.ufc.quixada.petti.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public List<Usuario> listAll(){
		return usuarioRepository.findAll();
	}
	
	public List<Usuario> listActives(){
		return usuarioRepository.findByAtivoOrderByNomeAsc(true);
	}
	
	public Usuario getById(Long id){
		return usuarioRepository.findOne(id);
	}
	
	public Usuario getByEmail(String email){
		List<Usuario> usuarios = usuarioRepository.findByEmailLike(email);
		if(!usuarios.isEmpty())
			return usuarios.get(0);
		else
			return null;
	}
	
	public void saveUsuario(Usuario usuario){
		usuarioRepository.save(usuario);
	}
	
	public void deleteUsuario(Long id){
		usuarioRepository.delete(id);
	}
	
}
