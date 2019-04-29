package com.api.dtos;

public class FilialDto {

	private Long id;
	private String logradouro;
	private String cep;
	private Long empresa_id;

	public Long getEmpresa_id() {
		return empresa_id;
	}

	public void setEmpresa_id(Long empresa_id) {
		this.empresa_id = empresa_id;
	}

	public FilialDto() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	@Override
	public String toString() {
		return "FilialDto [id=" + id + ", logradouro=" + logradouro + ", cep=" + cep + "]";
	}


}
