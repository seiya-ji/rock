package com.onezhier.rock.application.component;

import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import com.onezhier.rock.application.common.FrameworkUtils;
import com.onezhier.rock.application.dal.db.BaseDao;
import com.onezhier.rock.application.domain.BaseFactory;
import com.onezhier.rock.client.req.PageQuery;
import com.onezhier.rock.client.resp.PageResponse;
import com.onezhier.rock.framework.application.AbstractLauncheApplication;
import com.onezhier.rock.framework.application.AbstractLauncheApplication.InvokeHandler;
import com.onezhier.rock.framework.dal.db.DO;
import com.onezhier.rock.framework.domain.EntityObject;

@Component
public class DDDInvokeHandler implements InvokeHandler{

	
	public Object invoke(AbstractLauncheApplication launcheApplication,Class modelClass, String methodName,Map<String, Class<?>> paramsClassMap,Long id,List<Object> argsList) {
        BaseFactory baseFactory = launcheApplication.getApplicationContext().getBean(BaseFactory.class);

        EntityObject entityObject;
        if (id == null) {
            entityObject = (EntityObject) baseFactory.create(modelClass);
        } else {
            entityObject = baseFactory.reBuild(id, modelClass);
        }

        Object result = FrameworkUtils.invoke(entityObject, methodName, paramsClassMap.values().toArray(new Class<?>[0]), argsList.toArray());

        if (methodName.equals("destory")) {
            baseFactory.remove(entityObject);
        } else {
            if (result != null && result instanceof EntityObject) {
                result = baseFactory.save(entityObject);
            } else {
                baseFactory.save(entityObject);
            }
        }
        
		return result;
	}

	@Override
	public PageResponse<DO> query(AbstractLauncheApplication launcheApplication, String modelName,
			PageQuery pageQuery) {
		 Pageable pageable = PageRequest.of(pageQuery.getPageIndex() - 1, pageQuery.getPageSize(), Sort.by(Sort.Direction.DESC, "id"));
		 try {
	
        Class<? extends DO> doClazz = FrameworkUtils.getDOClazz((Class<? extends EntityObject>) ClassUtils.forName(modelName, ClassUtils.getDefaultClassLoader()));
		
        DO	example = doClazz.newInstance();
        
        BeanUtils.copyProperties(pageQuery, example);

        BaseDao baseDao = launcheApplication.getApplicationContext().getBean(BaseDao.class);
        
        Page<DO> doPage = baseDao.findAll(example, pageable);
        
      return PageResponse.of(doPage.getContent(), Integer.valueOf(String.valueOf(doPage.getTotalElements())),
      pageQuery.getPageSize(), pageQuery.getPageIndex());
        
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException |LinkageError e) {
		    throw new RuntimeException(e.getMessage(), e);
		} 
	}

	
}
