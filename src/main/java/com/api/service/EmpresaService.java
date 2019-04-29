package com.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import com.api.dtos.EmpresaDto;
import com.api.dtos.FilialDto;
import com.api.entity.Empresa;
import com.api.entity.Filial;
import com.api.repositorie.EmpresaRepository;
import com.api.repositorie.FilialRespository;



@Service
public class EmpresaService {
	
	@Autowired
	EmpresaRepository empresaRepository;
	
	@Autowired
	FilialRespository filialRespository;
	
	public void salvar(EmpresaDto empresa) {
		
		Empresa empresaEntity = new Empresa();
		empresaEntity.setCnpj(empresa.getCnpj());
		empresaEntity.setRazaoSocial(empresa.getRazaoSocial());
	
		Empresa save = empresaRepository.save(empresaEntity);		
		if(empresa.getFiliais().isEmpty()==false) {
			for (FilialDto filial : empresa.getFiliais()) {
				
			  System.out.println(filial.getLogradouro());
			  Filial filialEntity = new Filial();
			  filialEntity.setCep(filial.getCep());
			  filialEntity.setLogradouro(filial.getLogradouro());
			  filialEntity.setEmpresa(save);
			  filialRespository.save(filialEntity);
			}
		}

		
	}
	
	 public Page<Empresa> findAll(PageRequest pageRequest) {
	   	
	        return new PageImpl<>(empresaRepository.findAll(),  pageRequest, pageRequest.getPageSize());
	    }
	



}
