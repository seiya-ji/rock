package com.onezhier.rock.framework.component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.onezhier.rock.client.req.PageQuery;
import com.onezhier.rock.client.resp.PageResponse;
import com.onezhier.rock.framework.application.AbstractLauncheApplication;
import com.onezhier.rock.framework.application.AbstractLauncheApplication.InvokeHandler;
import com.onezhier.rock.framework.application.service.ServiceContextHolder;
import com.onezhier.rock.framework.common.FrameworkBaseUtils;
import com.onezhier.rock.framework.dal.db.DO;
import com.onezhier.rock.protocol.annotation.CreateType;
import com.onezhier.rocke.framework.service.BaseService;

@Component
public class MVCInvokeHandler implements InvokeHandler{

	
	public Object invoke(AbstractLauncheApplication launcheApplication,Class modelClass, String methodName,Map<String, Class<?>> paramsClassMap,Long id,List<Object> argsList) {
        
		Class<BaseService<DO>> serviceClazz =  (Class<BaseService<DO>>)ServiceContextHolder.get().getDate("serviceClass");
		
		BaseService<DO> service = launcheApplication.getApplicationContext().getBean(serviceClazz);
		
		CreateType methodCreateType = (CreateType)ServiceContextHolder.get().getDate("createType");
		Object result = null;
		
		if(methodCreateType==CreateType.DEFAULT) {
			  result = FrameworkBaseUtils.invoke(service, methodName, paramsClassMap.values().toArray(new Class<?>[0]), argsList.toArray());
		}else {
			// public Object invokeScripte(String scriptName,String scripteContent,Map<String, Object> params) 
			Map<String, Object> paramMap =  new HashMap<String, Object>();
			for(String paramName : paramsClassMap.keySet()) {
				Class paraType =  paramsClassMap.get(paramName);
				for(Object arg : argsList) {
					if(arg.getClass() == paraType ) {
						paramMap.put(paramName, arg);
					}
				}
				
			}
			result = FrameworkBaseUtils.invoke(service, "invokeScripte", new Class[] {String.class,String.class,Map.class}, paramMap);
		}
		
        
        
		return result;
	}

	@Override
	public PageResponse<DO> query(AbstractLauncheApplication launcheApplication, String modelName,
			PageQuery pageQuery) {
		 try {
			 
			 Class<BaseService<DO>> serviceClazz =  (Class<BaseService<DO>>)ServiceContextHolder.get().getDate("serviceClass");
			
			BaseService<DO> service = launcheApplication.getApplicationContext().getBean(serviceClazz);
			Page<DO> doPage = service.queryPage(pageQuery);
	        
	      return PageResponse.of(doPage.getContent(), Integer.valueOf(String.valueOf(doPage.getTotalElements())),
	      pageQuery.getPageSize(), pageQuery.getPageIndex());
	        
		} catch (LinkageError e) {
		    throw new RuntimeException(e.getMessage(), e);
		} 
	}

	
}
