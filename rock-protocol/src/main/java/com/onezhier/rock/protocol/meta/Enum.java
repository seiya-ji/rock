package com.onezhier.rock.protocol.meta;

import java.util.List;

/**
 * 枚举信息
 */
public class Enum {

    private String classPath;

    private String name;

    private String alias;

    private List<EnumItem> enumItems;

    public Enum(String classPath,String name, String alias, List<EnumItem> enumItems) {
        this.classPath = classPath;
        this.name = name;
        this.alias = alias;
        this.enumItems = enumItems;
    }

    public String getName() {
        return name;
    }

    public String getAlias() {
        return alias;
    }

    public List<EnumItem> getEnumItems() {
        return enumItems;
    }

    public String getClassPath() {
        return classPath;
    }
}
