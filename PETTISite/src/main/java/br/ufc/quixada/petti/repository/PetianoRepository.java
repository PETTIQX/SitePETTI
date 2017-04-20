package br.ufc.quixada.petti.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.ufc.quixada.petti.model.Petiano;

@Repository
@Transactional
public interface PetianoRepository extends JpaRepository<Petiano, Long>{

	//public List<Petiano> findByNome(String nome);
	//public List<Petiano> findById(Long id); 
	
}
