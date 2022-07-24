package com.onezhier.rock.protocol.meta;

import com.onezhier.rock.protocol.annotation.AssociationRelation.Association;

import lombok.Getter;
import lombok.Setter;

/**
 * 模型之间的关系
 */
@Getter
@Setter
public class ModelRelationMeta {

    private String sourceModel;

    private String targetModel;

    private Boolean mapTooMany;
    
    private String sourceClassName;
    
    private String targetClassName;
    
    // add type
    
    private Association associationType;
    
    private String targetAttrName;
    
    private String sourceAttrName;
    
    

    public ModelRelationMeta(String sourceClassName, String targetClassName, Boolean mapTooMany,String sourceModel, String targetModel,Association associationType ) {
        this.sourceClassName = sourceClassName;
        this.targetClassName = targetClassName;
        this.mapTooMany = mapTooMany;
        
        this.sourceModel = sourceModel;
        this.targetModel = targetModel;
        
        this.associationType = associationType;
    }

   
}
