package com.onezhier.rock.protocol.meta;

import lombok.Data;

/**
 * 模型属性
 */
@Data
public class AttributeMeta {

    private Integer index;

    private String name;

    private String accessControl;

    private String type;

    private String displayName;

    private String description;
    
    private  Boolean unique;
    
    private  Boolean notNull;
    
    private String classify;

    public AttributeMeta(Integer index,String name, String accessControl, String type, String displayName, String description,Boolean unique,Boolean notNull, String classify) {
        this.index = index;
        this.name = name;
        this.accessControl = accessControl;
        this.type = type;
        this.displayName = displayName;
        this.description = description;
        this.unique = unique;
        this.notNull = notNull;
        this.classify = classify;
    }

   
    
	
	
    
}
