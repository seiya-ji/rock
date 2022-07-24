//package com.pz.rock.core.generate.service;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//import java.util.StringJoiner;
//import java.util.stream.Collectors;
//
//import org.apache.commons.collections.CollectionUtils;
//import org.apache.commons.lang.StringUtils;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.github.javaparser.StaticJavaParser;
//import com.github.javaparser.ast.CompilationUnit;
//import com.github.javaparser.ast.ImportDeclaration;
//import com.github.javaparser.ast.Modifier;
//import com.github.javaparser.ast.Node;
//import com.github.javaparser.ast.NodeList;
//import com.github.javaparser.ast.PackageDeclaration;
//import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
//import com.github.javaparser.ast.body.FieldDeclaration;
//import com.github.javaparser.ast.body.MethodDeclaration;
//import com.github.javaparser.ast.body.Parameter;
//import com.github.javaparser.ast.body.VariableDeclarator;
//import com.github.javaparser.ast.expr.AnnotationExpr;
//import com.github.javaparser.ast.expr.Expression;
//import com.github.javaparser.ast.expr.MemberValuePair;
//import com.github.javaparser.ast.expr.NormalAnnotationExpr;
//import com.github.javaparser.ast.expr.SimpleName;
//import com.github.javaparser.ast.type.ClassOrInterfaceType;
//import com.github.javaparser.ast.type.Type;
//import com.onezhier.rock.common.util.DateUtil;
//import com.onezhier.rock.exception.BizException;
//import com.onezhier.rock.framework.annotation.Attribute;
//import com.onezhier.rock.framework.annotation.Entity;
//import com.onezhier.rock.framework.annotation.Function;
//import com.pz.rock.core.generate.config.Config;
//import com.pz.rock.core.generate.domain.EnumInfo;
//import com.pz.rock.core.generate.domain.Model;
//import com.pz.rock.core.generate.domain.ModelAttribute;
//import com.pz.rock.core.generate.domain.ModelMethod;
//import com.pz.rock.core.generate.domain.ModelMethodParameter;
//import com.pz.rock.core.generate.domain.ModelRelationShip;
//
//import freemarker.template.Configuration;
//import freemarker.template.Template;
//import freemarker.template.TemplateException;
//import lombok.extern.slf4j.Slf4j;
//
///**
// * @Author chino
// * @Date 2021/10/27 9:52
// */
//@Slf4j
//public class GenerateService {
//    public static final List<String> BASIC_DATA_TYPES = Arrays.asList("String", "Integer", "int", "Float", "float", "Double", "double", "Long", "long", "Boolean", "boolean", "Date");
//    public static final String MODEL_KEY = "models";
//    public static final String RELATIONSHIP_KEY = "relationShip";
//    public static final String ENUM_INFOS = "enumInfos";
//    public static final String PACKAGE = "package ";
//    public static final String LOMBOK_DATA_ANNOTATION = "@Data";
//    public static final String ENTITY_ANNOTATION = "@Entity(";
//    public static final String STORE_CLASS_NAME = " storeClassName = ";
//    public static final String ALIAS = " alias =";
//    public static final String DESCRIPTION = " description =";
//    public static final String GROUP = " group = ";
//    public static final String COMMA = " , ";
//    public static final String NEW_LINE = "\n";
//    public static final String TABS = "\t";
//    public static final String BLANK = " ";
//    public static final String SEMICOLON = ";";
//    public static final String CLOSING_PARENTHESIS = ")";
//    public static final String CLASS_DEF_START = "public class";
//    public static final String CLASS_DEF_END = "extends EntityObject {";
//    public static final String ATTRIBUTE_ANNOTATION = "@Attribute( ";
//    public static final String ATTRIBUTE_ANNOTATION_COMMENT = " comment = ";
//    public static final String QUOTATION_MARKS = "\"";
//    public static final String CLASS_END = "}";
//    public static final String IMPORT = "import";
//    public static final String JAVA_SUFFIX = ".java";
//    public static final String TEMPLATE_SUFFIX = ".ftl";
//    public static final String[] FILENAMES = {"service", "entity", "repository", "addCmd", "modifyCmd", "queryCmd", "listResponseDTO", "singleResponseDTO"};
//    public static final String[] TODO_GENERATE_FILENAMES = {"ServiceImpl", "Entity", "Dao", "AddCmd", "ModifyCmd", "QueryDTO", "ListResponseDTO", "SingleResponseDTO"};
//    public static final String mavenPath = "\\src\\main\\java\\";
//    public static final Map<Integer, String> moduleNameMap = new HashMap<>();
//
//    static {
//        moduleNameMap.put(0, "app");
//        moduleNameMap.put(1, "infrastructure");
//        moduleNameMap.put(2, "infrastructure");
//        for (int i = 3; i <= 7; i++) {
//            moduleNameMap.put(i, "client");
//        }
//    }
//
//    private Configuration configuration = Config.buildConfiguration();
//
//    private static final String BASE_DIR = "D:\\test";
//
//    public GenerateService() {
//    }
//
//    public String generateDomainFileByJsonData(String json) {
//
//        if (StringUtils.isEmpty(json)) {
//            throw new BizException("该json为空");
//        }
//
//        JSONObject jsonObject = JSON.parseObject(json);
//        List<Model> modelDtoList = JSON.parseArray(JSON.toJSONString(jsonObject.getJSONArray(MODEL_KEY)), Model.class);
//        List<ModelRelationShip> modelRelationShipDtoList = JSON.parseArray(JSON.toJSONString(jsonObject.getJSONArray(RELATIONSHIP_KEY)), ModelRelationShip.class);
//        List<EnumInfo> enumInfoList = JSON.parseArray(JSON.toJSONString(jsonObject.getJSONArray(ENUM_INFOS)), EnumInfo.class);
//
//        StringBuilder result = null;
//        for (Model model : modelDtoList) {
//            result = new StringBuilder();
//            //package
//            result.append(PACKAGE).append(model.getName().substring(0, model.getName().lastIndexOf("."))).append(SEMICOLON).append(NEW_LINE).append(NEW_LINE);
//
//            //import
//            for (String importStr : model.getImportContext()) {
//                result.append(IMPORT).append(BLANK).append(importStr).append(NEW_LINE);
//            }
//            result.append(NEW_LINE).append(NEW_LINE);
//
//            //类注解
//            result.append(LOMBOK_DATA_ANNOTATION).append(NEW_LINE);
//            result.append(ENTITY_ANNOTATION)
//                    .append(STORE_CLASS_NAME).append(model.getStoreClassName()).append(COMMA);
//            if (StringUtils.isNotEmpty(model.getAlias())) {
//                result.append(ALIAS).append(model.getAlias()).append(COMMA);
//            }
//            if (StringUtils.isNotEmpty(model.getDescription())) {
//                result.append(DESCRIPTION).append(model.getDescription()).append(COMMA);
//            }
//            result.append(GROUP).append(model.getGroupName()).append(CLOSING_PARENTHESIS).append(NEW_LINE);
//
//            //类
//            result.append(CLASS_DEF_START).append(BLANK).append(model.getName().substring(model.getName().lastIndexOf(".") + 1, model.getName().length())).append(BLANK)
//                    .append(CLASS_DEF_END).append(NEW_LINE);
//
//            //属性
//            for (ModelAttribute modelAttribute : model.getModelAttributes()) {
//                result.append(TABS).append(ATTRIBUTE_ANNOTATION)
//                        .append(ALIAS).append(QUOTATION_MARKS).append(modelAttribute.getDisplayName()).append(QUOTATION_MARKS).append(COMMA)
//                        .append(ATTRIBUTE_ANNOTATION_COMMENT).append(QUOTATION_MARKS).append(modelAttribute.getDescription()).append(QUOTATION_MARKS).append(CLOSING_PARENTHESIS)
//                        .append(NEW_LINE)
//                        .append(TABS)
//                        .append(modelAttribute.getAccessControl()).append(BLANK)
//                        .append(modelAttribute.getType()).append(BLANK)
//                        .append(modelAttribute.getName()).append(BLANK)
//                        .append(SEMICOLON)
//                        .append(NEW_LINE).append(NEW_LINE);
//            }
//
//            //方法
//            for (ModelMethod modelMethod : model.getModelMethods()) {
//                result.append(TABS).append(modelMethod.getBody()).append(NEW_LINE);
//            }
//
//            result.append(CLASS_END);
//        }
//        return result.toString();
//    }
//
//
//    public void generateCrudFile(List<File> fileList,String choice) throws IOException, TemplateException {
//        if(CollectionUtils.isEmpty(fileList)){
//            throw new BizException("没有Domain文件");
//        }
//        String json = parseFile(fileList);
//        generateCode(fileList,json,choice);
//    }
//
//    public static void main(String[] args) throws Exception, TemplateException {
////        String s = "D:\\workbench\\lean-pdca-backend\\lean-pdca-domain\\src\\main\\java\\cn\\leansight\\idoos\\pdca\\domain\\test\\model";
////        Map<String, Boolean> map = new HashMap<>();
////        map.put("TopicDomain.java", true);
////        new GenerateService().generateCrudFile(s, "11111111", map);
//
//        File file = new File("D:\\test\\model");
//        String substring = file.getName().substring(0, file.getName().lastIndexOf(File.separator));
//        System.out.println(substring);
//
//    }
//
//    public void generateCode(List<File> fileList,String json,String choice) throws IOException, TemplateException {
//
//        if (StringUtils.isEmpty(json)) {
//            throw new BizException("该json为空");
//        }
//
//        //解析json
//        JSONObject jsonObject = JSON.parseObject(json);
//        List<Model> modelDtoList = JSON.parseArray(JSON.toJSONString(jsonObject.getJSONArray(MODEL_KEY)), Model.class);
//        List<ModelRelationShip> modelRelationShipDtoList = JSON.parseArray(JSON.toJSONString(jsonObject.getJSONArray(RELATIONSHIP_KEY)), ModelRelationShip.class);
//        List<EnumInfo> enumInfoList = JSON.parseArray(JSON.toJSONString(jsonObject.getJSONArray(ENUM_INFOS)), EnumInfo.class);
//
//        String totalPath = fileList.get(0).toString().substring(0,fileList.get(0).toString().lastIndexOf(File.separator));
//
//        int location = totalPath.lastIndexOf("domain");
//        String parentPath = totalPath.substring(0, location-1);
//        String aggregateRoot = totalPath.substring(location+7,totalPath.lastIndexOf("model")-1);
//
//
//        Map<String, Object> root = null;
//        for (Model model : modelDtoList) {
//            //处理Domain类名
//            String[] strings = model.getName().split("\\.");
//            String modelName = strings[strings.length - 1];
//            if (modelName.contains("Domain")) {
//                modelName = modelName.replace("Domain", "");
//            }
//            String firstCharLow = StrFirstCharToLower(modelName);
//
//            //处理项目名称
//            StringJoiner sj = new StringJoiner(".");
//            for (int i = 0; i < strings.length; i++) {
//                if("domain".equals(strings[i])){
//                    break;
//                }
//                sj.add(strings[i]);
//            }
//            String projectPath = sj.toString();
//
//            //处理属性
//            model.getModelAttributes().forEach((attribute) -> {
//                if (attribute.getType().contains(".")) {
//                    attribute.setType(attribute.getType().substring(attribute.getType().lastIndexOf(".") + 1));
//                }
//            });
//
//            root = new HashMap<>(16);
//            root.put("author", "chino");
//            root.put("dateTime", DateUtil.getNewFormatDateString(new Date()));
//            root.put("modelNameLower", firstCharLow);
//            root.put("modelNameUpper", modelName);
//            root.put("aggregateRoot", aggregateRoot);
//            root.put("projectPath", projectPath);
//            root.put("attributes", model.getModelAttributes());
//
//
//            for (Integer i = 0; i < 8; i++) {
//
//                if ("0".equals(choice.indexOf(i))) {
//                    continue;
//                }
//                String[] splits = parentPath.split("/|\\\\");
//                String pre = null;
//                int index  = -1;
//                for(int j = 0;j<splits.length;j++){
//                    if("src".equals(splits[j])){
//                        break;
//                    }
//                    pre = splits[j];
//                    index = j;
//                }
//                Path pp = null;
//                if (i == 0) {
//                    String replace = pre.replace("domain", "app");
//                    splits[index] = replace;
//                    StringJoiner sj1  =new StringJoiner(File.separator);
//                    for(String x:splits){
//                        sj1.add(x);
//                    }
//                    sj1.add("app");
//                    sj1.add(aggregateRoot);
//                    sj1.add("service");
//
//                    pp = Paths.get(sj1.toString());
//
//                } else if(i == 1){
//                    String replace = pre.replace("domain", "infrastructure");
//                    splits[index] = replace;
//                    StringJoiner sj1  =new StringJoiner(File.separator);
//                    for(String x:splits){
//                        sj1.add(x);
//                    }
//                    sj1.add("infra");
//                    sj1.add(aggregateRoot);
//                    sj1.add("dal");
//                    sj1.add("database");
//                    sj1.add("dataobj");
//
//                    pp = Paths.get(sj1.toString());
//                }else if (i == 2) {
//                    String replace = pre.replace("domain", "infrastructure");
//                    splits[index] = replace;
//                    StringJoiner sj1  =new StringJoiner(File.separator);
//                    for(String x:splits){
//                        sj1.add(x);
//                    }
//                    sj1.add("infra");
//                    sj1.add(aggregateRoot);
//                    sj1.add("dal");
//                    sj1.add("database");
//                    sj1.add("dao");
//
//                    pp = Paths.get(sj1.toString());
//                } else if (i == 3 || i == 4 || i == 5) {
//                    String replace = pre.replace("domain", "client");
//                    splits[index] = replace;
//                    StringJoiner sj1  =new StringJoiner(File.separator);
//                    for(String x:splits){
//                        sj1.add(x);
//                    }
//                    sj1.add("client");
//                    sj1.add(aggregateRoot);
//                    sj1.add("dto");
//                    sj1.add("req");
//
//                    pp = Paths.get(sj1.toString());
//                } else if (i == 6 || i == 7) {
//                    String replace = pre.replace("domain", "client");
//                    splits[index] = replace;
//                    StringJoiner sj1  =new StringJoiner(File.separator);
//                    for(String x:splits){
//                        sj1.add(x);
//                    }
//                    sj1.add("client");
//                    sj1.add(aggregateRoot);
//                    sj1.add("dto");
//                    sj1.add("resp");
//
//                    pp = Paths.get(sj1.toString());
//                }
//
//
//                if(!Files.exists(pp)){
//                    Files.createDirectories(pp);
//                }
//                Path currentPath = Paths.get(pp.toString(), String.format("%s%s%s", modelName, TODO_GENERATE_FILENAMES[i], JAVA_SUFFIX));
//                if (!Files.exists(currentPath)) {
//                    Files.createFile(currentPath);
//                }
//
//                FileWriter writer = new FileWriter(currentPath.toFile());
//                Template template = configuration.getTemplate(String.format("%s%s", FILENAMES[i], TEMPLATE_SUFFIX));
//                template.process(root, writer);
//            }
//
//        }
//    }
//
//    private String StrFirstCharToLower(String name) {
//        char[] value = name.toCharArray();
//        value[0] += 32;
//        return String.valueOf(value);
//    }
//
//    public String parseFile(List<File> fileList) throws IOException {
//
//        List<ModelRelationShip> modelRelationShips = new ArrayList<>(100);
//        List<Model> models = new ArrayList<>(100);
//        //key:类全路径或者枚举全路径，value:如果是枚举，则为true,否则false
//        Map<String, Boolean> classNameOrEnumsMap = new HashMap<>();
//        for (File file : fileList) {
//            CompilationUnit cu = StaticJavaParser.parse(new FileInputStream(file));
//            List<ClassOrInterfaceDeclaration> classOrInterfaceDeclarations = cu.findAll(ClassOrInterfaceDeclaration.class);
//            if (classOrInterfaceDeclarations.isEmpty()) {
//                continue;
//                //throw new BizException("Domain类不可以是枚举类型");
//            } else {
//                ClassOrInterfaceDeclaration classOrInterfaceDeclaration = classOrInterfaceDeclarations.get(0);
//                Optional<AnnotationExpr> entityAnnotation = classOrInterfaceDeclaration.getAnnotationByClass(Entity.class);
//                if (!entityAnnotation.isPresent()) {
//                    continue;
//                }
//                Model model = parseForModel(classOrInterfaceDeclaration, cu, entityAnnotation.get(), modelRelationShips, classNameOrEnumsMap);
//                classNameOrEnumsMap.put(model.getName(), false);
//                models.add(model);
//            }
//        }
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("models", models);
//        jsonObject.put("relationShip", modelRelationShips);
//        return jsonObject.toString();
//    }
//
//    private Model parseForModel(ClassOrInterfaceDeclaration classOrInterfaceDeclaration, CompilationUnit cu, AnnotationExpr annotationExpr, List<ModelRelationShip> modelRelationShips, Map<String, Boolean> classNameOrEnumsMap) {
//        String packageName = cu.findAll(PackageDeclaration.class).get(0).getName().toString();
//        List<ImportDeclaration> importDeclarations = cu.findAll(ImportDeclaration.class);
//
//        List<String> importList = importDeclarations.stream().map((importDeclaration) -> {
//            return importDeclaration.getName().asString();
//        }).collect(Collectors.toList());
//
//        Map<String, String> importMap = new HashMap<>();
//        //处理引入的包
//        for (ImportDeclaration importDeclaration : importDeclarations) {
//            String importName = importDeclaration.getName().toString();
//            String key = importName.substring(importName.lastIndexOf(".") + 1);
//            importMap.put(key, importName);
//        }
//        String name = packageName + "." + classOrInterfaceDeclaration.getName().asString();
//        String storeClassName = "";
//        String modelAlias = classOrInterfaceDeclaration.getName().asString();
//        String modelDescription = "";
//        String group = "default";
//        //处理Entity注解信息
//        if (annotationExpr instanceof NormalAnnotationExpr) {
//            Map<String, Object> properties = extraPropertiesFromNormalAnnotationExpr((NormalAnnotationExpr) annotationExpr);
//            storeClassName = (String) properties.getOrDefault("storeClassName", storeClassName);
//            modelAlias = (String) properties.getOrDefault("alias", modelAlias);
//            modelDescription = (String) properties.getOrDefault("description", modelDescription);
//            group = (String) properties.getOrDefault("group", group);
//        }
//
//        //处理属性信息
//        List<FieldDeclaration> fieldDeclarations = classOrInterfaceDeclaration.getFields();
//        List<ModelAttribute> modelAttributes = new ArrayList<>(20);
//        List<ModelMethod> modelMethods = new ArrayList<>(20);
//        int index = 0;
//        for (FieldDeclaration fieldDeclaration : fieldDeclarations) {
//            Optional<Modifier> first = fieldDeclaration.getModifiers().getFirst();
//            VariableDeclarator variable = fieldDeclaration.getVariable(0);
//            Optional<AnnotationExpr> annotationByClass = fieldDeclaration.getAnnotationByClass(Attribute.class);
//            String displayName = variable.getName().toString();
//            String comment = "";
//            if (annotationByClass.isPresent()) {
//                Map<String, Object> properties = extraPropertiesFromNormalAnnotationExpr((NormalAnnotationExpr) annotationByClass.get());
//                displayName = (String) properties.getOrDefault("alias", displayName);
//                comment = (String) properties.getOrDefault("comment", comment);
//            }
//            TypeInfo typeFullName = getType(variable.getType(), importMap, packageName);
//
//            //生成一个模型属性对象
//            ModelAttribute modelAttribute = new ModelAttribute(index++, variable.getName().toString(), first.get().getKeyword().asString(), typeFullName.getName(), displayName, comment);
//            modelAttributes.add(modelAttribute);
//            if (!BASIC_DATA_TYPES.contains(variable.getType().toString()) && variable.getType().isReferenceType()) {
//                TypeInfo typeInfo = getType(variable.getType(), importMap, packageName);
//                ModelRelationShip modelRelationShip = new ModelRelationShip(name, typeInfo.getName(), typeInfo.isList);
//                modelRelationShips.add(modelRelationShip);
//            }
//
//        }
//        List<MethodDeclaration> allMethod = cu.findAll(MethodDeclaration.class);
//        index = 0;
//        for (MethodDeclaration methodDeclaration : allMethod) {
//            Optional<AnnotationExpr> annotationByClass = methodDeclaration.getAnnotationByClass(Function.class);
//            Boolean exposed = false;
//            String alias = methodDeclaration.getNameAsString();
//            String comment = "";
//            if (methodDeclaration.getComment().isPresent()) {
//                comment = methodDeclaration.getComment().get().getContent();
//            }
//            if (annotationByClass.isPresent()) {
//                Map<String, Object> properties = extraPropertiesFromNormalAnnotationExpr((NormalAnnotationExpr) annotationByClass.get());
//                comment = (String) properties.getOrDefault("comment", comment);
//                alias = (String) properties.getOrDefault("alias", alias);
//                exposed = (Boolean) properties.getOrDefault("exposed", exposed);
//            }
//            List<ModelMethodParameter> modelMethodParameters = new ArrayList<>();
//            NodeList<Parameter> parameters = methodDeclaration.getParameters();
//            int pIndex = 0;
//            for (com.github.javaparser.ast.body.Parameter parameter : parameters) {
//                TypeInfo typeInfo = getType(parameter.getType(), importMap, packageName);
//                ModelMethodParameter modelMethodParameter = new ModelMethodParameter(parameter.getName().asString().replaceAll("\"", ""), typeInfo.getName(), true, pIndex++);
//                modelMethodParameters.add(modelMethodParameter);
//            }
//            String returnType = methodDeclaration.getType().toString();
//            if (!"void".equals(returnType)) {
//                TypeInfo typeInfo = getType(methodDeclaration.getType(), importMap, packageName);
//                ModelMethodParameter modelMethodParameter = new ModelMethodParameter("returnVal", typeInfo.getName(), false, 0);
//                modelMethodParameters.add(modelMethodParameter);
//            }
//
//            ModelMethod modelMethod = new ModelMethod(index++, methodDeclaration.getNameAsString(), methodDeclaration.getModifiers().getFirst().get().getKeyword().asString(), comment, methodDeclaration.toString(), exposed, alias, modelMethodParameters);
//            modelMethods.add(modelMethod);
//        }
//
//        return new Model(group, name, modelAlias, modelDescription, storeClassName, modelAttributes, modelMethods, importList);
//    }
//
//    private Map<String, Object> extraPropertiesFromNormalAnnotationExpr(NormalAnnotationExpr normalAnnotationExpr) {
//        Map<String, Object> properties = new HashMap<>(20);
//        for (MemberValuePair pair : normalAnnotationExpr.getPairs()) {
//            String key = pair.getName().toString();
//            Expression value = pair.getValue();
//            String valueStr = pair.getValue().toString().replaceAll("\"", "");
//            if (value.isCharLiteralExpr()) {
//                properties.put(key, valueStr);
//                continue;
//            }
//            if (value.isIntegerLiteralExpr()) {
//                properties.put(key, Integer.valueOf(valueStr));
//                continue;
//            }
//            if (value.isLongLiteralExpr()) {
//                properties.put(key, Long.valueOf(valueStr));
//                continue;
//            }
//            if (value.isBooleanLiteralExpr()) {
//                properties.put(key, Boolean.valueOf(valueStr));
//                continue;
//            }
//            if (value.isStringLiteralExpr()) {
//                properties.put(key, valueStr);
//                continue;
//            }
//        }
//        return properties;
//    }
//
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
//
//
//}
