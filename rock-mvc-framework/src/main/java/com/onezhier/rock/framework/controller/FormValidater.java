package com.onezhier.rock.framework.controller;

import java.util.List;

import com.onezhier.rock.client.DTO;

public interface FormValidater<D extends DTO> {
	
	public default void  createValidate(D dto) {
		return ;
	}

	
	public default void  modifyValidate(Object id,DTO dto) {
		return ;
	}
	
	public default void  createValidate(List<D> dtoList) {
		for(D dto :dtoList) {
			this.createValidate(dto);
		}
	}

	
}
