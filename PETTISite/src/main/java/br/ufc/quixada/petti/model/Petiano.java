package br.ufc.quixada.petti.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class Petiano {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nome;
	private String email;
	private String curso;
	private String linkLattes;
	private String fotoCaricatura;
	private String fotoReal;
	private String senha;
	
	@Column(nullable=false)
	private boolean ativo;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(
			name = "petiano_atividade",
			joinColumns = {@JoinColumn(name="atividade_id")},
			inverseJoinColumns = {@JoinColumn(name="petiano_id")})
	private List<Atividade> atividades;
	
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
	public String getCurso() {
		return curso;
	}
	public void setCurso(String curso) {
		this.curso = curso;
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
	public String getFotoCaricatura() {
		return fotoCaricatura;
	}
	public void setFotoCaricatura(String fotoCaricatura) {
		this.fotoCaricatura = fotoCaricatura;
	}
	public String getFotoReal() {
		return fotoReal;
	}
	public void setFotoReal(String fotoReal) {
		this.fotoReal = fotoReal;
	}
	public List<Atividade> getAtividades() {
		return atividades;
	}
	public void setAtividades(List<Atividade> atividades) {
		this.atividades = atividades;
	}
	public boolean isAtivo() {
		return ativo;
	}
	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
	
}
