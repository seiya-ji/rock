//package com.pz.rock.core.generate.domain;
//
//import java.util.List;
//
///**
// * 模型方法
// */
//public class ModelMethod {
//
//    private Integer index;
//    /**
//     * 方法名称
//     */
//    private String name;
//
//    private String alias;
//    /**
//     * 访问控制
//     */
//    private String accessControl;
//
//    private String description;
//
//    private String body;
//
//    private Boolean exposed = false;
//
//    private List<ModelMethodParameter> modelMethodParameters;
//
//    public ModelMethod(Integer index, String name, String accessControl, String description, String body,Boolean exposed,String alias, List<ModelMethodParameter> modelMethodParameters) {
//        this.index = index;
//        this.name = name;
//        this.accessControl = accessControl;
//        this.description = description;
//        this.body = body;
//        this.modelMethodParameters = modelMethodParameters;
//        this.exposed = exposed;
//        this.alias = alias;
//    }
//
//    public Integer getIndex() {
//        return index;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public String getAccessControl() {
//        return accessControl;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public String getBody() {
//        return body;
//    }
//
//    public Boolean getExposed() {
//        return exposed;
//    }
//
//    public String getAlias() {
//        return alias;
//    }
//
//    public List<ModelMethodParameter> getModelMethodParameters() {
//        return modelMethodParameters;
//    }
//}
