/**
 * Copyright (c) 2010-2022 PuZhong.Co.Ltd. All Rights Reserved..
 */
package com.onezhier.rock.client.req;

import java.util.LinkedList;
import java.util.List;

import com.onezhier.rock.client.Request;

import lombok.Data;


/**
 * <p>Title: Query </p>
 * <p>
 * Query request from Client.
 * </p>
 *
 * @author stephen.ji
 * @version v0.1
 * @date 2021/3/26 4:29 下午
 */
@Data
public abstract class Query extends Request {
    private static final long serialVersionUID = 1L;
    
    
    private List<MatcherCfg> matcheres = new LinkedList<>();
    
    private List<NullCfg> nullConfigs = new LinkedList<>();
    
    @Data
    public static class NullCfg{
    	
    	private String name;
    	
    	private boolean not;
    }
    
    @Data
    public static class MatcherCfg {
    	
    	private String name;
    	
    	private String type;
    	
    }

}
