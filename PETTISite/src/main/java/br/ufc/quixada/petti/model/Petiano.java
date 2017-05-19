package br.ufc.quixada.petti.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;

@Entity
@DiscriminatorValue(value = "petiano")
public class Petiano extends Usuario{

	private String curso;

	public String getCurso() {
		return curso;
	}

	public void setCurso(String curso) {
		this.curso = curso;
	}
	
}
