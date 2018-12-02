package com.acroynon.ssbe.model.adaptor;

import java.util.ArrayList;
import java.util.List;

public abstract class Adaptor<SOURCE, DTO> {
	
	public abstract SOURCE adaptToSource(DTO dto);
	public abstract DTO adaptToDTO(SOURCE source);
	
	public List<SOURCE> adaptAllToSource(List<DTO> dtos){
		List<SOURCE> list = new ArrayList<SOURCE>();
		for(DTO dto : dtos){
			list.add(adaptToSource(dto));
		}
		return list;
	}
	
	public List<DTO> adaptAllToDTO(List<SOURCE> users){
		List<DTO> list = new ArrayList<DTO>();
		for(SOURCE user : users){
			list.add(adaptToDTO(user));
		}
		return list;
	}
}

