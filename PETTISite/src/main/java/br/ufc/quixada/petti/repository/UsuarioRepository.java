package br.ufc.quixada.petti.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.ufc.quixada.petti.model.Usuario;

@Repository
@Transactional
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	
	public List<Usuario> findByEmailLike(String email);
	public List<Usuario> findByAtivoOrderByNomeAsc(boolean ativo);
	
}
