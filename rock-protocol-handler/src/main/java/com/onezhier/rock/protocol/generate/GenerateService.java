package com.onezhier.rock.protocol.generate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MemberValuePair;
import com.github.javaparser.ast.expr.NormalAnnotationExpr;
import com.onezhier.rock.common.util.DateUtil;
import com.onezhier.rock.exception.BizException;
import com.onezhier.rock.exception.SysException;
import com.onezhier.rock.protocol.annotation.AssociationRelation.Association;
import com.onezhier.rock.protocol.annotation.Attribute;
import com.onezhier.rock.protocol.annotation.CreateType;
import com.onezhier.rock.protocol.annotation.Method;
import com.onezhier.rock.protocol.configuration.Config;
import com.onezhier.rock.protocol.generate.ProtocolHandler.TypeInfo;
import com.onezhier.rock.protocol.meta.Enum;
import com.onezhier.rock.protocol.meta.ModelMeta;
import com.onezhier.rock.protocol.meta.AttributeMeta;
import com.onezhier.rock.protocol.meta.MethodMeta;
import com.onezhier.rock.protocol.meta.ParameterMeta;
import com.onezhier.rock.protocol.meta.ModelRelationMeta;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author chino
 * @Date 2021/10/27 9:52
 */
@Slf4j
public class GenerateService {
    public static final List<String> BASIC_DATA_TYPES = Arrays.asList("String", "Integer", "int", "Float", "float", "Double", "double", "Long", "long", "Boolean", "boolean", "Date");
    public static final String MODEL_KEY = "models";
    public static final String RELATIONSHIP_KEY = "relationShip";
    public static final String ENUM_INFOS = "enumInfos";
    public static final String PACKAGE = "package ";
    public static final String LOMBOK_DATA_ANNOTATION = "@Data";
    public static final String ENTITY_ANNOTATION = "@Entity(";
    public static final String STORE_CLASS_NAME = " storeClassName = ";
    public static final String ALIAS = " alias =";
    public static final String DESCRIPTION = " description =";
    public static final String GROUP = " group = ";
    public static final String COMMA = " , ";
    public static final String NEW_LINE = "\n";
    public static final String TABS = "\t";
    public static final String BLANK = " ";
    public static final String SEMICOLON = ";";
    public static final String CLOSING_PARENTHESIS = ")";
    public static final String CLASS_DEF_START = "public class";
    public static final String CLASS_DEF_END = "extends EntityObject {";
    public static final String ATTRIBUTE_ANNOTATION = "@Attribute( ";
    public static final String ATTRIBUTE_ANNOTATION_COMMENT = " comment = ";
    public static final String QUOTATION_MARKS = "\"";
    public static final String CLASS_END = "}";
    public static final String IMPORT = "import";
    public static final String JAVA_SUFFIX = ".java";
    public static final String TEMPLATE_SUFFIX = ".ftl";
    public static final String[] FILENAMES = {"service", "entity", "repository", "controller", "convertor", "query", "respDTO", "respListDTO","reqDTO"};
    public static final String[] TODO_GENERATE_FILENAMES = {"Service", "Entity", "Repository", "Controller", "Convertor", "Query", "RespDTO", "RespListDTO","ReqDTO"};
    public static final String mavenPath = "\\src\\main\\java\\";
    public static final Map<String, String> moduleNameMap = new HashMap<>();
    
    public static final String entityPackagePath = "dao.entity";
    

    static {
        moduleNameMap.put("service", "server");
        moduleNameMap.put("entity", "server");
        moduleNameMap.put("repository", "server");
        moduleNameMap.put("controller", "server");
        moduleNameMap.put("convertor", "server");
        moduleNameMap.put("query", "client");
        moduleNameMap.put("respDTO", "client");
        moduleNameMap.put("respListDTO", "client");
        moduleNameMap.put("reqDTO", "client");
       
    }

    private Configuration configuration = Config.buildConfiguration();

    private static final String BASE_DIR = "D:\\test";

    public GenerateService() {
    }

    public String getServiceName(ModelMeta model) {
    	return model.getProjectPackageName()+(StringUtils.isNotBlank(model.getGroupName())? "."+model.getGroupName():"")+".service."+String.format("%s%s%s", this.getShortModelName(model), TODO_GENERATE_FILENAMES[0], "");
    }
    
    public String getModelName(ModelMeta model) {
    	return model.getProjectPackageName()+(StringUtils.isNotBlank(model.getGroupName())? "."+model.getGroupName():"")+".dao.entity."+String.format("%s%s%s", this.getShortModelName(model), "", "");
    }
    
    /**
     * 通过json的模型定义数据，拼装成模型类文件
     * @param json
     * @return
     */
    public String assembleEntityFileStrByJsonMeta(String json) {

        if (StringUtils.isEmpty(json)) {
            throw new BizException("该json为空");
        }

        JSONObject jsonObject = JSON.parseObject(json);
        List<ModelMeta> modelDtoList = JSON.parseArray(JSON.toJSONString(jsonObject.getJSONArray(MODEL_KEY)), ModelMeta.class);
        List<ModelRelationMeta> modelRelationShipDtoList = JSON.parseArray(JSON.toJSONString(jsonObject.getJSONArray(RELATIONSHIP_KEY)), ModelRelationMeta.class);
        List<Enum> enumInfoList = JSON.parseArray(JSON.toJSONString(jsonObject.getJSONArray(ENUM_INFOS)), Enum.class);

        StringBuilder result = null;
        for (ModelMeta model : modelDtoList) {
            result = new StringBuilder();
            //package
            result.append(PACKAGE).append(model.getName().substring(0, model.getName().lastIndexOf("."))).append(SEMICOLON).append(NEW_LINE).append(NEW_LINE);

            //import
            for (String importStr : model.getImportContext()) {
                result.append(IMPORT).append(BLANK).append(importStr).append(NEW_LINE);
            }
            result.append(NEW_LINE).append(NEW_LINE);

            //类注解
            result.append(LOMBOK_DATA_ANNOTATION).append(NEW_LINE);
            result.append(ENTITY_ANNOTATION)
                    .append(STORE_CLASS_NAME).append(model.getStoreClassName()).append(COMMA);
            if (StringUtils.isNotEmpty(model.getAlias())) {
                result.append(ALIAS).append(model.getAlias()).append(COMMA);
            }
            if (StringUtils.isNotEmpty(model.getDescription())) {
                result.append(DESCRIPTION).append(model.getDescription()).append(COMMA);
            }
            result.append(GROUP).append(model.getGroupName()).append(CLOSING_PARENTHESIS).append(NEW_LINE);

            //类
            result.append(CLASS_DEF_START).append(BLANK).append(model.getName().substring(model.getName().lastIndexOf(".") + 1, model.getName().length())).append(BLANK)
                    .append(CLASS_DEF_END).append(NEW_LINE);

            //属性
            for (AttributeMeta modelAttribute : model.getModelAttributes()) {
                result.append(TABS).append(ATTRIBUTE_ANNOTATION)
                        .append(ALIAS).append(QUOTATION_MARKS).append(modelAttribute.getDisplayName()).append(QUOTATION_MARKS).append(COMMA)
                        .append(ATTRIBUTE_ANNOTATION_COMMENT).append(QUOTATION_MARKS).append(modelAttribute.getDescription()).append(QUOTATION_MARKS).append(CLOSING_PARENTHESIS)
                        .append(NEW_LINE)
                        .append(TABS)
                        .append(modelAttribute.getAccessControl()).append(BLANK)
                        .append(modelAttribute.getType()).append(BLANK)
                        .append(modelAttribute.getName()).append(BLANK)
                        .append(SEMICOLON)
                        .append(NEW_LINE).append(NEW_LINE);
            }

            //方法
            for (MethodMeta modelMethod : model.getModelMethods()) {
                result.append(TABS).append(modelMethod.getBody()).append(NEW_LINE);
            }

            result.append(CLASS_END);
        }
        return result.toString();
    }

   
    public static void main(String[] args) throws Exception, TemplateException {
        String s = "C:\\work\\test\\src\\main\\java\\com\\pz\\magician\\designer\\manage\\user\\dao\\entity";
//        Map<String, Boolean> map = new HashMap<>();
//        map.put("TopicDomain.java", true);
        new GenerateService().generateCodeByEntity(new File(s),Collections.EMPTY_LIST,"server");
        
//        UniqueConstraint a = ();
        
//        JsonUtils.toJSONString(a);



    }

    
    private String packageToFilePath(String modelName) {
    	String[] strings = modelName.split("\\.");
    	 StringJoiner sj = new StringJoiner(File.separator);
         for (int i = 0; i < strings.length; i++) {
             sj.add(strings[i]);
         }
         return sj.toString();
    }
    
    public String getEntityFilePath(ModelMeta model,String projectCodePath) {
    	
    	model.setPackageName(model.getProjectPackageName()+ (model.getGroupName()!=null? "."+model.getGroupName():"")+"."+entityPackagePath);
    	String path = projectCodePath+File.separator+this.packageToFilePath(model.getPackageName());
    	
    
       //处理项目名称
       StringJoiner sj = new StringJoiner(File.separator);
       sj.add(path);
   		String fileName = StringUtils.capitalize(this.getShortModelName(model));
   		//Path destPath = Paths.get(sj.toString(), String.format("%s%s%s", fileName, TODO_GENERATE_FILENAMES[1], JAVA_SUFFIX));
   		Path destPath = Paths.get(sj.toString(), String.format("%s%s%s", fileName, "", JAVA_SUFFIX));
   		//model.setName(model.getProjectPackagePath()+ (model.getGroupName()!=null? "."+model.getGroupName():"")+"."+entityPackagePath+"."+fileName);
   		
   		return destPath.toString();
    }
    
    public String getServiceFilePath(ModelMeta model,String projectCodePath) {
    	String path = projectCodePath+File.separator+this.packageToFilePath(model.getProjectPackageName())+File.separator+model.getGroupName();
    	
    
       //处理项目名称
       StringJoiner sj = new StringJoiner(File.separator);
       sj.add(path);
   		sj.add("service");
   		Path destPath = Paths.get(sj.toString(), String.format("%s%s%s", this.getShortModelName(model), TODO_GENERATE_FILENAMES[0], JAVA_SUFFIX));
   		return destPath.toString();
    }
    
    /**
     * 按模型结构生成模型类
     * @param model
     * @param projectCodePath
     * @throws IOException
     * @throws TemplateException
     */
    public String generateEntityCodeByTemplate(ModelMeta model,String projectCodePath,String packageParentPath) throws IOException, TemplateException {
    	
    	
    	Path destPath = Paths.get(this.getEntityFilePath(model, projectCodePath));
    	if(Files.exists(destPath)) {
    		FileUtils.delete(destPath.toFile());
    	}
    	
        if(!Files.exists(destPath.getParent())) {
        	FileUtils.createParentDirectories(destPath.toFile());
        }
        	
            Files.createFile(destPath);

        Template template = configuration.getTemplate(String.format("%s%s", "entity", TEMPLATE_SUFFIX));
        template.process(this.getTemplateDataModel(model),  new FileWriter(destPath.toFile()));
        return destPath.toString();
    }
    
    /**
     * 解析类文件为模型结构
     * @param entityFile
     * @return
     * @throws FileNotFoundException 
     */
    public ModelMeta parse(File entityFile) throws FileNotFoundException {
    	
    	CompilationUnit cu = StaticJavaParser.parse(new FileInputStream(entityFile));
        List<ClassOrInterfaceDeclaration> classOrInterfaceDeclarations = cu.findAll(ClassOrInterfaceDeclaration.class);
        if(classOrInterfaceDeclarations.isEmpty()) {
        	throw new SysException("Domain类不可以是枚举类型");
        }
       
            ClassOrInterfaceDeclaration classOrInterfaceDeclaration = classOrInterfaceDeclarations.get(0);
            Optional<AnnotationExpr> entityAnnotation = classOrInterfaceDeclaration.getAnnotationByClass(com.onezhier.rock.protocol.annotation.Model.class);
            if (!entityAnnotation.isPresent()) {
            	throw new SysException("不是entity类");
            }
           

    	return parseForModel(classOrInterfaceDeclaration, cu, entityAnnotation.get(), new LinkedList<>(), new HashMap<>());
    }
    
    
    private String getProjectPath(ModelMeta model) {
    	  String[] strings = model.getName().split("\\.");
    	  StringJoiner sj = new StringJoiner(".");
          for (int i = 0; i < strings.length-1; i++) {
          	
              sj.add(strings[i]);
          }
         return sj.toString();
    }
    
    private String getShortModelName(ModelMeta model) {
    	  String[] strings = model.getName().split("\\.");
          String modelName = strings[strings.length - 1];
          return modelName;
    }
    
    private Map<String, Object> getTemplateDataModel(ModelMeta model){
    	
        
    	 Map<String, Object> root = new HashMap<>(16);
         root.put("author", "魔法师平台");
         root.put("dateTime", DateUtil.getNewFormatDateString(new Date()));
         root.put("modelNameLower", StringUtils.uncapitalize(this.getShortModelName(model)));
         root.put("modelNameUpper", StringUtils.capitalize(this.getShortModelName(model)));
         root.put("module", model.getGroupName());
         //this.getProjectPath(model);
         root.put("projectPath", model.getProjectPackageName() );
         root.put("attributes", model.getModelAttributes());
         root.put("methodes", model.getModelMethods());
         root.put("model", model);
         return root;
    }
    
    
    public void generateCodeByEntity(File entityDir,List<String> filterList,String entityModuleName) {
    	
    	if(FileUtils.isDirectory(entityDir)) {
    		Iterator<File> it = FileUtils.iterateFiles(entityDir, new String[] {"java"}, true);
    		while(it.hasNext()) {
    			try {
					this.generateCodeByEntityFile(it.next(),filterList,entityModuleName);
				} catch (IOException | TemplateException e) {
					throw new SysException(e.getMessage(),e);
				}
    		}
    		
    		return ;
    	}
    	
    	
    	try {
    		
			this.generateCodeByEntityFile(entityDir,filterList,entityModuleName);
		} catch (IOException | TemplateException e) {
			throw new SysException(e.getMessage(),e);
		}
    	
    	
    }
    
    
    public void generateCodeByEntityFiles(List<File> entityFiles,List<String> filterList,String entityModuleName) {
    	
    	for(File file : entityFiles) {
    		try {
				this.generateCodeByEntityFile(file, filterList,entityModuleName);
			} catch (IOException | TemplateException e) {
				throw new SysException(e.getMessage(),e);
			}
    	}
    	
    }
    
    
    
    /**
     * 按实体类文件生成相应代码
     * @param entityFile
     * @throws IOException
     * @throws TemplateException
     */
    private void generateCodeByEntityFile(File entityFile,List<String> filterList,String entityModuleName) throws IOException, TemplateException {

        String entityFilePath = entityFile.getAbsolutePath().toString().substring(0,entityFile.getAbsoluteFile().toString().lastIndexOf(File.separator));
        int projectIndex = entityFilePath.lastIndexOf(mavenPath);
        String projectDirPath = entityFilePath.substring(0,projectIndex);
        
        int groupIndex = entityFilePath.lastIndexOf("dao");
        String groupDirPath = entityFilePath.substring(0, groupIndex-1);
        
        String relativeGroupPath = entityFilePath.substring(projectIndex, groupIndex-1);
        
    	ModelMeta model = this.parse(entityFile);
    	
        //处理属性
        model.getModelAttributes().forEach((attribute) -> {
            if (attribute.getType().contains(".")) {
                attribute.setType(attribute.getType().substring(attribute.getType().lastIndexOf(".") + 1));
            }
        });

        
        
//        String[] splits = moduleDirPath.split("/|\\\\");
//        
//        StringJoiner parentJoiner  =new StringJoiner(File.separator);
//        for(String x:splits){
//        	parentJoiner.add(x);
//        }

        for (Integer i = 0; i < FILENAMES.length; i++) {
        	
        	 if(filterList.contains(TODO_GENERATE_FILENAMES[i])) {
        		 continue;
        	 }
        	projectDirPath = projectDirPath.replace(entityModuleName, moduleNameMap.get(FILENAMES[i]));
             groupDirPath =  projectDirPath+relativeGroupPath;
             
        	 StringJoiner relativeJoiner  =new StringJoiner(File.separator);
        	 
        	if(FILENAMES[i].equals("repository")) {
        		relativeJoiner.add("dao");
        		relativeJoiner.add("repository");
        		 
        	}
        	if(FILENAMES[i].equals("service")) {
        		relativeJoiner.add("service");
        		 
        	}
        	if(FILENAMES[i].equals("controller")) {
        		relativeJoiner.add("controller");
        		 
        	}
        	if(FILENAMES[i].equals("convertor")) {
        		relativeJoiner.add("convertor");
        		 
        	}
        	
        	//PZ23-client\ 
        	
        	if(FILENAMES[i].equals("query")) {
        		relativeJoiner.add("dto");
        		relativeJoiner.add("req");
        		relativeJoiner.add("query");
        		 
        	}
        	
        	if(FILENAMES[i].equals("respDTO")) {
        		relativeJoiner.add("dto");
        		relativeJoiner.add("resp");
        		 
        	}
        	
        	if(FILENAMES[i].equals("respListDTO")) {
        		relativeJoiner.add("dto");
        		relativeJoiner.add("resp");
        		 
        	}
        	if(FILENAMES[i].equals("reqDTO")) {
        		relativeJoiner.add("dto");
        		relativeJoiner.add("req");
        		 
        	}
        	
        	
        	
           
        	Path destDir = Paths.get(groupDirPath.toString()+File.separator+relativeJoiner.toString());
            if(!Files.exists(destDir)){
                Files.createDirectories(destDir);
            }
            
            
            Path destPath = Paths.get(destDir.toString(), String.format("%s%s%s", this.getShortModelName(model), TODO_GENERATE_FILENAMES[i], JAVA_SUFFIX));
            if(Files.exists(destPath)) {
            	FileUtils.delete(destPath.toFile());
            }
                Files.createFile(destPath);

            Template template = configuration.getTemplate(String.format("%s%s", FILENAMES[i], TEMPLATE_SUFFIX));
            FileWriter writer = new FileWriter(destPath.toFile());
            template.process(this.getTemplateDataModel(model),  writer);
            writer.close();
        }

    
    
    }
    
    
    
    
    /**
     * 解析模型的类文件
     * @param fileList
     * @return
     * @throws IOException
     */
    public String parseFile(List<File> fileList) throws IOException {

        List<ModelRelationMeta> modelRelationShips = new ArrayList<>(100);
        List<ModelMeta> models = new ArrayList<>(100);
        //key:类全路径或者枚举全路径，value:如果是枚举，则为true,否则false
        Map<String, Boolean> classNameOrEnumsMap = new HashMap<>();
        for (File file : fileList) {
            CompilationUnit cu = StaticJavaParser.parse(new FileInputStream(file));
            List<ClassOrInterfaceDeclaration> classOrInterfaceDeclarations = cu.findAll(ClassOrInterfaceDeclaration.class);
            if (classOrInterfaceDeclarations.isEmpty()) {
                continue;
                //throw new BizException("Domain类不可以是枚举类型");
            } else {
                ClassOrInterfaceDeclaration classOrInterfaceDeclaration = classOrInterfaceDeclarations.get(0);
                Optional<AnnotationExpr> entityAnnotation = classOrInterfaceDeclaration.getAnnotationByClass(com.onezhier.rock.protocol.annotation.Model.class);
                if (!entityAnnotation.isPresent()) {
                    continue;
                }
                ModelMeta model = parseForModel(classOrInterfaceDeclaration, cu, entityAnnotation.get(), modelRelationShips, classNameOrEnumsMap);
                classNameOrEnumsMap.put(model.getName(), false);
                models.add(model);
            }
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("models", models);
        jsonObject.put("relationShip", modelRelationShips);
        return jsonObject.toString();
    }

    /**
     * 通过编译，抽取model元数据对象
     * @param classOrInterfaceDeclaration
     * @param cu
     * @param annotationExpr
     * @param modelRelationShips
     * @param classNameOrEnumsMap
     * @return
     */
    private ModelMeta parseForModel(ClassOrInterfaceDeclaration classOrInterfaceDeclaration, CompilationUnit cu, AnnotationExpr annotationExpr, List<ModelRelationMeta> modelRelationShips, Map<String, Boolean> classNameOrEnumsMap) {
        String packageName = cu.findAll(PackageDeclaration.class).get(0).getName().toString();
        List<ImportDeclaration> importDeclarations = cu.findAll(ImportDeclaration.class);

        List<String> importList = importDeclarations.stream().map((importDeclaration) -> {
            return importDeclaration.getName().asString();
        }).collect(Collectors.toList());

        Map<String, String> importMap = new HashMap<>();
        //处理引入的包
        for (ImportDeclaration importDeclaration : importDeclarations) {
            String importName = importDeclaration.getName().toString();
            String key = importName.substring(importName.lastIndexOf(".") + 1);
            importMap.put(key, importName);
        }
        String name = packageName + "." + classOrInterfaceDeclaration.getName().asString();
        String storeClassName = "";
        String modelAlias = classOrInterfaceDeclaration.getName().asString();
        String modelDescription = "";
        String group = "default";
        //处理Entity注解信息
        if (annotationExpr instanceof NormalAnnotationExpr) {
            Map<String, Object> properties = extraPropertiesFromNormalAnnotationExpr((NormalAnnotationExpr) annotationExpr);
            storeClassName = (String) properties.getOrDefault("storeClassName", storeClassName);
            modelAlias = (String) properties.getOrDefault("alias", modelAlias);
            modelDescription = (String) properties.getOrDefault("description", modelDescription);
            group = (String) properties.getOrDefault("group", group);
        }

        //处理属性注解信息
        List<FieldDeclaration> fieldDeclarations = classOrInterfaceDeclaration.getFields();
        List<AttributeMeta> modelAttributes = new ArrayList<>(20);
        List<MethodMeta> modelMethods = new ArrayList<>(20);
        int index = 0;
        for (FieldDeclaration fieldDeclaration : fieldDeclarations) {
            Optional<Modifier> first = fieldDeclaration.getModifiers().getFirst();
            VariableDeclarator variable = fieldDeclaration.getVariable(0);
            Optional<AnnotationExpr> annotationByClass = fieldDeclaration.getAnnotationByClass(Attribute.class);
            String displayName = variable.getName().toString();
            String comment = "";
            boolean notNull = true;
            boolean unique = false;
            String classify = "";
            if (annotationByClass.isPresent()) {
                Map<String, Object> properties = extraPropertiesFromNormalAnnotationExpr((NormalAnnotationExpr) annotationByClass.get());
                displayName = (String) properties.getOrDefault("alias", displayName);
                comment = (String) properties.getOrDefault("comment", comment);
                unique = (boolean) properties.getOrDefault("unique", false);
                notNull = (boolean) properties.getOrDefault("notNull", true);
                classify = (String) properties.getOrDefault("classify", CreateType.CUSTOMIZED.name());
            }
            
            TypeInfo typeFullName = ProtocolHandler.getType(variable.getType(), importMap, packageName);

            //生成一个模型属性对象
            AttributeMeta modelAttribute = new AttributeMeta(index++, variable.getName().toString(), first.get().getKeyword().asString(), typeFullName.getName(), displayName, comment,unique,notNull,classify);
            modelAttributes.add(modelAttribute);
            if (!BASIC_DATA_TYPES.contains(variable.getType().toString()) && variable.getType().isReferenceType()) {
                TypeInfo typeInfo = ProtocolHandler.getType(variable.getType(), importMap, packageName);
                ModelRelationMeta modelRelationShip = new ModelRelationMeta(name, typeInfo.getName(), typeInfo.getIsList(),classOrInterfaceDeclaration.getName().asString(),typeInfo.getSimpleName(),Association.AGGREGATION);
                modelRelationShips.add(modelRelationShip);
            }

        }
        List<MethodDeclaration> allMethod = cu.findAll(MethodDeclaration.class);
        index = 0;
        for (MethodDeclaration methodDeclaration : allMethod) {
            Optional<AnnotationExpr> annotationByClass = methodDeclaration.getAnnotationByClass(Method.class);
            Boolean exposed = false;
            String alias = methodDeclaration.getNameAsString();
            String comment = "";
            String classify = "";
            if (methodDeclaration.getComment().isPresent()) {
                comment = methodDeclaration.getComment().get().getContent();
            }
            if (annotationByClass.isPresent()) {
                Map<String, Object> properties = extraPropertiesFromNormalAnnotationExpr((NormalAnnotationExpr) annotationByClass.get());
                comment = (String) properties.getOrDefault("comment", comment);
                alias = (String) properties.getOrDefault("alias", alias);
                exposed = (Boolean) properties.getOrDefault("exposed", exposed);
                classify = (String) properties.getOrDefault("classify", CreateType.CUSTOMIZED.name());
            }
            List<ParameterMeta> modelMethodParameters = new ArrayList<>();
            NodeList<Parameter> parameters = methodDeclaration.getParameters();
            int pIndex = 0;
            for (com.github.javaparser.ast.body.Parameter parameter : parameters) {
                TypeInfo typeInfo = ProtocolHandler.getType(parameter.getType(), importMap, packageName);
                ParameterMeta modelMethodParameter = new ParameterMeta(parameter.getName().asString().replaceAll("\"", ""), typeInfo.getName(), true, pIndex++);
                modelMethodParameters.add(modelMethodParameter);
            }
            String returnType = methodDeclaration.getType().toString();
            if (!"void".equals(returnType)) {
                TypeInfo typeInfo = ProtocolHandler.getType(methodDeclaration.getType(), importMap, packageName);
                ParameterMeta modelMethodParameter = new ParameterMeta("returnVal", typeInfo.getName(), false, 0);
                modelMethodParameters.add(modelMethodParameter);
            }

            MethodMeta modelMethod = new MethodMeta(index++, methodDeclaration.getNameAsString(), methodDeclaration.getModifiers().getFirst().get().getKeyword().asString(), comment, methodDeclaration.toString(), exposed, alias, modelMethodParameters,classify);
            modelMethods.add(modelMethod);
        }

        ModelMeta model =  new ModelMeta(group, name, modelAlias, modelDescription, storeClassName, modelAttributes, modelMethods, importList);
        model.setPackageName(packageName);
        if(StringUtils.isBlank(model.getGroupName())) {
        	model.setProjectPackageName(packageName.substring(0, packageName.lastIndexOf("dao")-1));
        }else {
        	model.setProjectPackageName(packageName.substring(0, packageName.lastIndexOf(model.getGroupName())-1));
        }
        return model ;
    }
    
    /**
     * 获取注解的元数据的信息
     * @param normalAnnotationExpr
     * @return
     */
    private Map<String, Object> extraPropertiesFromNormalAnnotationExpr(NormalAnnotationExpr normalAnnotationExpr) {
        Map<String, Object> properties = new HashMap<>(20);
        for (MemberValuePair pair : normalAnnotationExpr.getPairs()) {
            String key = pair.getName().toString();
            Expression value = pair.getValue();
            String valueStr = pair.getValue().toString().replaceAll("\"", "");
            if (value.isCharLiteralExpr()) {
                properties.put(key, valueStr);
                continue;
            }
            if (value.isIntegerLiteralExpr()) {
                properties.put(key, Integer.valueOf(valueStr));
                continue;
            }
            if (value.isLongLiteralExpr()) {
                properties.put(key, Long.valueOf(valueStr));
                continue;
            }
            if (value.isBooleanLiteralExpr()) {
                properties.put(key, Boolean.valueOf(valueStr));
                continue;
            }
            if (value.isStringLiteralExpr()) {
                properties.put(key, valueStr);
                continue;
            }
        }
        return properties;
    }

//    /**
//     * 获取类型的信息
//     * @param type
//     * @param importMap
//     * @param packageName
//     * @return
//     */
//    private TypeInfo getType(Type type, Map<String, String> importMap, String packageName) {
//        String typeName = type.toString();
//        if (BASIC_DATA_TYPES.contains(typeName)) {
//            return new TypeInfo(typeName, false, false);
//        }
//        if (importMap.containsKey(typeName)) {
//            return new TypeInfo(importMap.get(typeName), false, false);
//        }
//        if (type instanceof ClassOrInterfaceType) {
//            List<Node> childNodes = type.getChildNodes();
//            Node firstNode = childNodes.get(0);
//            if (firstNode instanceof SimpleName && "List".equals(((SimpleName) firstNode).getIdentifier())) {
//                StringBuilder sb = new StringBuilder("List<");
//                for (int i = 1; i < childNodes.size(); i++) {
//                    Node node = childNodes.get(i);
//                    if (node instanceof SimpleName) {
//                        break;
//                    } else {
//                        ClassOrInterfaceType nodeType = (ClassOrInterfaceType) node;
//                        String s = nodeType.getElementType().toString();
//                        sb.append(importMap.getOrDefault(s, packageName + "." + s));
//                    }
//                }
//                sb.append(">");
//                return new TypeInfo(sb.toString(), true, false);
//            }
//            if (firstNode instanceof SimpleName && "Map".equals(((SimpleName) firstNode).getIdentifier())) {
//                return new TypeInfo(typeName, false, true);
//            }
//        }
//        return new TypeInfo(packageName + "." + typeName, false, false);
//    }
//
//    private static class TypeInfo {
//        private String name;
//        private Boolean isList;
//        private Boolean isMap;
//
//        public TypeInfo(String name, Boolean isList, Boolean isMap) {
//            this.name = name;
//            this.isList = isList;
//            this.isMap = isMap;
//        }
//
//        public String getName() {
//            return name;
//        }
//
//        public Boolean getList() {
//            return isList;
//        }
//
//        public Boolean getMap() {
//            return isMap;
//        }
//    }


}
