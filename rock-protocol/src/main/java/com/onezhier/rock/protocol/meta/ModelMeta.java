package com.onezhier.rock.protocol.meta;

import java.util.List;

import lombok.Getter;



/**
 * 模型
 */
@Getter
public class ModelMeta {

    private String groupName;

    private String name;

    private String alias;

    private String description;

    private String storeClassName;

    private List<AttributeMeta> modelAttributes;

    private List<MethodMeta> modelMethods;

    private List<String> importContext;
    
    
    // new add attr jihb 2022-06-24
    private Boolean autoAudit;
    
    private Boolean logic;
    
    private List<UniqueConstraintMeta> uniqueConstraints;
    
    /**
     * 模型类的包名,如模型类为：com.xyz.user.UserInfo,包名为：com.xyz.user
     */
    private transient String packageName;
    
    /**
     * 项目父包名，如模型类为：com.xyz.user.UserInfo,项目父包名为：com.xyz 项目包名创建应用时定下的
     */
    private transient String projectPackageName;
    
    
//    public String getProjectPath(String modelRelativePath) {
//    	modelRelativePath = "."+modelRelativePath;
//    	return this.packageName.replace(modelRelativePath, "");
//    }
    
    
    public ModelMeta(String groupName, String name, String alias, String description, String storeClassName) {
		super();
		this.groupName = groupName;
		this.name = name;
		this.alias = alias;
		this.description = description;
		this.storeClassName = storeClassName;
	}

	public  ModelMeta(String groupName, String name, String alias, String description, String storeClassName, List<AttributeMeta> modelAttributes, List<MethodMeta> modelMethods) {
    	   this.groupName = groupName;
           this.name = name;
           this.alias = alias;
           this.description = description;
           this.storeClassName = storeClassName;
           this.modelAttributes = modelAttributes;
           this.modelMethods = modelMethods;
    }
    
    public ModelMeta(String groupName, String name, String alias, String description, String storeClassName, List<AttributeMeta> modelAttributes, List<MethodMeta> modelMethods, List<String> importContext) {
        this.groupName = groupName;
        this.name = name;
        this.alias = alias;
        this.description = description;
        this.storeClassName = storeClassName;
        this.modelAttributes = modelAttributes;
        this.modelMethods = modelMethods;
        this.importContext = importContext;
    }


	public void setAutoAudit(boolean autoAudit) {
		this.autoAudit = autoAudit;
	}

	public void setLogic(boolean logic) {
		this.logic = logic;
	}

	public void setUniqueConstraints(List<UniqueConstraintMeta> uniqueConstraints) {
		this.uniqueConstraints = uniqueConstraints;
	}

	public void setModelAttributes(List<AttributeMeta> modelAttributes) {
		this.modelAttributes = modelAttributes;
	}

	public void setModelMethods(List<MethodMeta> modelMethods) {
		this.modelMethods = modelMethods;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}


	public void setProjectPackageName(String projectPackageName) {
		this.projectPackageName = projectPackageName;
	}


	public void setName(String name) {
		this.name = name;
	}


	
	
    
    
}
