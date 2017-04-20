package br.ufc.quixada.petti.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Petiano {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String nome;
	private String email;
	private String linkLattes;
	private String senha;
	
	//private List<Atividade> atividades;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getLinkLattes() {
		return linkLattes;
	}
	public void setLinkLattes(String linkLattes) {
		this.linkLattes = linkLattes;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	/*public List<Atividade> getAtividades() {
		return atividades;
	}
	public void setAtividades(List<Atividade> atividades) {
		this.atividades = atividades;
	}*/
	
}
