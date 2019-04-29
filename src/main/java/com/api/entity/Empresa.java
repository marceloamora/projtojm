package com.api.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;



@Entity
@Table(name="empresa")
public class Empresa implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8869569605721717107L;
	private Long id;
	private String razaoSocial;
	private String cnpj;
	private List<Filial> filiais = new ArrayList<>();
	
	@OneToMany(mappedBy="empresa")
	public List<Filial> getFiliais() {
		return filiais;
	}
	public void setFiliais(List<Filial> filiais) {
		this.filiais = filiais;
	}
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "razao_social", nullable = false, length=40)
	public String getRazaoSocial() {
		return razaoSocial;
	}
	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}
	
	@Column(name = "cnpj", nullable = false, length=20)
	public String getCnpj() {
		return cnpj;
	}
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

}
