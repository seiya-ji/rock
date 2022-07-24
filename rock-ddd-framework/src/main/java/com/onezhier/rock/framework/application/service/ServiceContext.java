package com.onezhier.rock.framework.application.service;

import java.util.HashMap;
import java.util.Map;

import com.onezhier.rock.client.DTO;
import com.onezhier.rock.client.Response;
import com.onezhier.rock.framework.dal.db.DO;
import com.onezhier.rock.framework.domain.EntityObject;

import lombok.Data;

@Data
public class ServiceContext {
	
	private  Class<? extends EntityObject> entityClass ;
	
	private  Class<? extends DO> doClass ;
	
	private  Class<? extends Response> listDtoClass ;
	
	private  Class<? extends Response> dtoClass ;
	
	private Map<String, Object> contain = new HashMap<String, Object>();
	
	
}
