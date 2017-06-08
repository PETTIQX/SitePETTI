package br.ufc.quixada.petti.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.ufc.quixada.petti.model.Tutor;

@Repository
@Transactional
public interface TutorRepository extends JpaRepository<Tutor, Long>{

	public List<Tutor> findByEmailLike(String email);
	public List<Tutor> findByAtivoOrderByNomeAsc(boolean ativo);
	
}
