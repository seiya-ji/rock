package com.onezhier.rock.protocol.meta;

import java.util.List;

import com.onezhier.rock.protocol.annotation.CreateType;

import lombok.Getter;

/**
 * 模型方法
 */
@Getter
public class MethodMeta {

    private Integer index;
    /**
     * 方法名称
     */
    private String name;

    private String alias;
    /**
     * 访问控制
     */
    private String accessControl;

    private String description;

    private String body;

    private Boolean exposed = false;
    
    private String classify = CreateType.DEFAULT.name();

    private List<ParameterMeta> modelMethodParameters;

    public MethodMeta(Integer index, String name, String accessControl, String description, String body,Boolean exposed,String alias, List<ParameterMeta> modelMethodParameters,String classify) {
        this.index = index;
        this.name = name;
        this.accessControl = accessControl;
        this.description = description;
        this.body = body;
        this.modelMethodParameters = modelMethodParameters;
        this.exposed = exposed;
        this.alias = alias;
        this.classify = classify;
    }

}
