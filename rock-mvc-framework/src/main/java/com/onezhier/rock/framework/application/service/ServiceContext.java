package com.onezhier.rock.framework.application.service;

import java.util.HashMap;
import java.util.Map;

import com.onezhier.rock.client.DTO;
import com.onezhier.rock.client.Response;
import com.onezhier.rock.framework.dal.db.DO;

import lombok.Data;

@Data
public class ServiceContext {
	
	//private  Class<? extends EntityObject> entityClass ;
	
	private  Class<? extends DO> doClass ;
	
	private  Class<? extends Response> listDtoClass ;
	
	private  Class<? extends Response> dtoClass ;
	
	private Map<String, Object> container = new HashMap<String, Object>();
	
	private DTO currentDTO;
	
	public void putDate(String key , Object value) {
		this.container.put(key, value);
	}
	
	public Object getDate(String key ) {
		return this.container.get(key);
	}
}
