package com.onezhier.rock.protocol.meta;

import lombok.Getter;

/**
 * 方法参数
 */
@Getter
public class ParameterMeta {

    private String name;

    private String type;

    /**
     * 是否是输入参数
     */
    private Boolean isInput;

    /**
     * 输入参数顺序
     */
    private Integer index;

    public ParameterMeta(String name, String type, Boolean isInput, Integer index) {
        this.name = name;
        this.type = type;
        this.isInput = isInput;
        this.index = index;
    }

    
}
