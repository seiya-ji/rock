package com.onezhier.rock.framework.utils;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import org.springframework.context.ApplicationContext;
import org.springframework.core.ResolvableType;

import com.onezhier.rock.client.resp.PageResponse;
import com.onezhier.rock.common.util.CommonBeanUtils;
import com.onezhier.rock.framework.dal.db.DO;
import com.onezhier.rocke.framework.service.BaseService;


public  class FrameworkBeanUtils extends CommonBeanUtils{
	
	

	public static <D,T> void copy2DTO(D do_ ,T dto ,BiConsumer<D, T> consumer ) {
		consumer.accept(do_, dto);
	}
	
	public static <D,T> void copy2DTO(D do_ ,PageResponse<T> page ,BiConsumer<D, T> consumer ) {
		page.getData().forEach(t->{
			FrameworkBeanUtils.copy2DTO(do_, t, consumer);
		});
	}
	
	public static  <T> void fillDTO(PageResponse<T> page ,Consumer<T> consumer ) {
		page.getData().forEach(t->{
			consumer.accept(t);
		});
	}
	
	public static <T extends DO> BaseService<T> getBaseService(Class<T> clazz,ApplicationContext context){
		
		String[] beanNamesForType = context.getBeanNamesForType(ResolvableType.forClassWithGenerics(BaseService.class, clazz));

		// If you expect several beans of the same generic type then extract them as you wish. Otherwise, just take the first
		return (BaseService<T>) context.getBean(beanNamesForType[0]);
	}
	
}
