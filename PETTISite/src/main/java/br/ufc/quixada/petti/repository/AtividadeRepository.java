package br.ufc.quixada.petti.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.ufc.quixada.petti.model.Atividade;

@Repository
@Transactional
public interface AtividadeRepository extends JpaRepository<Atividade, Long>{

	public List<Atividade> findByOrderByNomeAsc();
	
}
