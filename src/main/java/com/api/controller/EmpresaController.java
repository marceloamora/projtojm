package com.api.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.dtos.EmpresaDto;
import com.api.entity.Empresa;
import com.api.response.Response;
import com.api.service.EmpresaService;
import com.api.service.SMSFuncionarioService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;


@RestController
@RequestMapping("/api/empresas")
public class EmpresaController {
	
	@Autowired
	SMSFuncionarioService sMSFuncionarioService;
	
	
	@Autowired
	EmpresaService empresaService;
	
	


	@Value("${paginacao.qtd_por_pagina}")
	private int qtdPorPagina;

	@PostMapping
	public ResponseEntity<Response<EmpresaDto>> cadastrar(@Valid @RequestBody EmpresaDto empresaDto,
			BindingResult result) {
		Response<EmpresaDto> response = new Response<EmpresaDto>();
		 String json = null;
		if (result.hasErrors()) {
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		empresaDto.setId(1L);
		 ObjectMapper mapper = new ObjectMapper();
		  mapper.enable(SerializationFeature.INDENT_OUTPUT);
		  try {
			json = mapper.writeValueAsString(empresaDto);
		} catch (JsonProcessingException e) {

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
		response.setData(empresaDto);
		this.sMSFuncionarioService.enviar(json);
		
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "/all/")
	public ResponseEntity<Response<Page<EmpresaDto>>> all(
	
			@RequestParam(value = "pag", defaultValue = "0") int pag,
			@RequestParam(value = "ord", defaultValue = "id") String ord,
			@RequestParam(value = "dir", defaultValue = "DESC") String dir) {

		Response<Page<EmpresaDto>> response = new Response<Page<EmpresaDto>>();

		PageRequest pageRequest = new PageRequest(pag, this.qtdPorPagina, Direction.valueOf(dir), ord);
		Page<Empresa> empresas = this.empresaService.findAll(pageRequest);
		Page<EmpresaDto> EmpresasDto = empresas.map(empresa-> this.converterEmpresaDto(empresa));

		response.setData(EmpresasDto);
		return ResponseEntity.ok(response);
	}
	
	
	
	private EmpresaDto converterEmpresaDto(Empresa emp) {
		EmpresaDto dto = new EmpresaDto();
		dto.setId(emp.getId());
		dto.setCnpj(emp.getCnpj());
		dto.setRazaoSocial(emp.getRazaoSocial());


		return dto;
	}

}
