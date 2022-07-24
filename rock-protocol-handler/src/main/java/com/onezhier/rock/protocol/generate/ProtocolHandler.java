package com.onezhier.rock.protocol.generate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.ReflectionUtils.FieldCallback;
import org.springframework.util.ReflectionUtils.FieldFilter;
import org.springframework.util.ReflectionUtils.MethodCallback;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.EnumConstantDeclaration;
import com.github.javaparser.ast.body.EnumDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.ArrayInitializerExpr;
import com.github.javaparser.ast.expr.BooleanLiteralExpr;
import com.github.javaparser.ast.expr.ClassExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.expr.MarkerAnnotationExpr;
import com.github.javaparser.ast.expr.MemberValuePair;
import com.github.javaparser.ast.expr.Name;
import com.github.javaparser.ast.expr.NormalAnnotationExpr;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.expr.SingleMemberAnnotationExpr;
import com.github.javaparser.ast.expr.StringLiteralExpr;
import com.github.javaparser.ast.expr.TypeExpr;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.Type;
import com.google.common.collect.Lists;
import com.onezhier.rock.exception.SysException;
import com.onezhier.rock.protocol.annotation.AssociationRelation;
import com.onezhier.rock.protocol.annotation.AssociationRelation.Amount;
import com.onezhier.rock.protocol.annotation.AssociationRelation.Association;
import com.onezhier.rock.protocol.annotation.Attribute;
import com.onezhier.rock.protocol.annotation.CreateType;
import com.onezhier.rock.protocol.annotation.EnumTag;
import com.onezhier.rock.protocol.meta.Enum;
import com.onezhier.rock.protocol.meta.EnumItem;
import com.onezhier.rock.protocol.meta.ModelMeta;
import com.onezhier.rock.protocol.meta.AttributeMeta;
import com.onezhier.rock.protocol.meta.MethodMeta;
import com.onezhier.rock.protocol.meta.ParameterMeta;
import com.onezhier.rock.protocol.meta.ModelRelationMeta;
import com.onezhier.rock.protocol.meta.UniqueConstraintMeta;

import groovy.lang.Tuple3;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ProtocolHandler {
	
	
	public void generateJavaSource(ModelMeta model,List<AttributeMeta> attrList , List<MethodMeta> methodList,String destFilePath) throws IOException {
		
		CompilationUnit cu = new CompilationUnit();
		cu.setPackageDeclaration(model.getPackageName());
		
		//导入类
		cu.addImport(Column.class);
		cu.addImport(Entity.class);
		cu.addImport(Table.class);
		cu.addImport(Data.class);
		cu.addImport(NoArgsConstructor.class);
		cu.addImport(com.onezhier.rock.protocol.annotation.Model.class);
		cu.addImport(Attribute.class);
		cu.addImport(com.onezhier.rock.protocol.annotation.UniqueConstraint.class);
		cu.addImport(CreateType.class);
		cu.addImport(String.class);
		cu.addImport(Integer.class);
		cu.addImport(Boolean.class);
		cu.addImport(Long.class);
		cu.addImport(Double.class);
		cu.addImport(Date.class);
				
		ClassOrInterfaceDeclaration modelClass = cu.addClass(model.getName());
		modelClass.setJavadocComment("@Author ${author}\r\n@Date ${dateTime}");
		//modelClass.addOrphanComment(null);
		//设置继承
		modelClass.addExtendedType("com.onezhier.rock.framework.dal.db.DO");
		
		
		//设置注解
		NodeList<MemberValuePair>  modelNodeList = new NodeList<>();
		modelNodeList.add(new MemberValuePair("alias", new StringLiteralExpr(model.getAlias())));
		modelNodeList.add(new MemberValuePair("description",  new StringLiteralExpr(model.getDescription())));
		modelNodeList.add(new MemberValuePair("storeClassName", new StringLiteralExpr(model.getPackageName()+"."+model.getName())));
		modelNodeList.add(new MemberValuePair("group", new StringLiteralExpr(model.getGroupName())));

		modelNodeList.add(new MemberValuePair("autoAudit", new BooleanLiteralExpr(model.getAutoAudit())));

		modelNodeList.add(new MemberValuePair("logic", new BooleanLiteralExpr(model.getLogic())));
		
		//设置uniqueConstraints 注解
		NodeList<Expression> uniqueConstraintList = new NodeList<>();
		for(UniqueConstraintMeta uc :model.getUniqueConstraints()) {
			NodeList<MemberValuePair> ucAttrNodeList = new NodeList<>();
			ucAttrNodeList.add(new MemberValuePair("prompt", new StringLiteralExpr(uc.getPrompt()) ));
			
			ucAttrNodeList.add(new MemberValuePair("attributes", new ArrayInitializerExpr( new NodeList<>(uc.getAttributes().stream().map((t)->{ return new StringLiteralExpr(t);}).collect(Collectors.toList()))) ) );
			uniqueConstraintList.add(new NormalAnnotationExpr(new Name("UniqueConstraint"), ucAttrNodeList));
		}
		modelNodeList.add(new MemberValuePair("uniqueConstraints", new ArrayInitializerExpr(uniqueConstraintList)));
		
		
		modelClass.addAnnotation(new NormalAnnotationExpr(new Name("Model"), modelNodeList));
		modelClass.addAnnotation(new MarkerAnnotationExpr(new Name("Entity")));
		modelClass.addAnnotation(new MarkerAnnotationExpr(new Name("Data")));
		modelClass.addAnnotation(new MarkerAnnotationExpr(new Name("NoArgsConstructor")));
		NodeList<MemberValuePair>  tableNodeList = new NodeList<>();
		tableNodeList.add(new MemberValuePair("name", new StringLiteralExpr(model.getName())));
		modelClass.addAnnotation(new NormalAnnotationExpr(new Name("Table"), tableNodeList));
		
		attrList = attrList.stream().filter((t)->{ return t.getClassify().equals(CreateType.CUSTOMIZED.name());}).collect(Collectors.toList());
		for(AttributeMeta attr : attrList) {
			
			FieldDeclaration  fieldDeclaration =  modelClass.addField(attr.getType(), attr.getName(), Modifier.Keyword.valueOf(attr.getAccessControl().toUpperCase()));
			
			//添加注解
			NodeList<MemberValuePair>  attrNodeList = new NodeList<>();
			if(StringUtils.isNotBlank(attr.getDisplayName())) {
				attrNodeList.add(new MemberValuePair("alias", new StringLiteralExpr(attr.getDisplayName())));
			}
			if(StringUtils.isNotBlank(attr.getDescription())) {
				attrNodeList.add(new MemberValuePair("comment",  new StringLiteralExpr(attr.getDescription())));
			}
			
			attrNodeList.add(new MemberValuePair("unique", new BooleanLiteralExpr(attr.getUnique())));

			attrNodeList.add(new MemberValuePair("notNull", new BooleanLiteralExpr(attr.getNotNull())));

			attrNodeList.add(new MemberValuePair("classify", new FieldAccessExpr(new TypeExpr(new ClassOrInterfaceType(CreateType.class.getSimpleName())), attr.getClassify())));
			
			fieldDeclaration.addAnnotation(new NormalAnnotationExpr(new Name("Attribute"), attrNodeList));
			
			NodeList<MemberValuePair>  columnNodeList = new NodeList<>();
			columnNodeList.add(new MemberValuePair("name", new StringLiteralExpr(attr.getName())));
			fieldDeclaration.addAnnotation(new NormalAnnotationExpr(new Name("Column"), columnNodeList));
			/** Session id that applied to the request, if any. */
			fieldDeclaration.setBlockComment(" "+attr.getName()+"即"+attr.getDisplayName()+"表"+attr.getDescription()+", 此属性"+(attr.getNotNull()?"是":"不是")+"必须的！对于系统来说"+(attr.getUnique()?"是":"不是")+"唯一的");
		}
		FileUtils.writeStringToFile(new File(destFilePath), cu.toString(), "UTF-8");
		//System.err.println(cu.toString());
//		for(ModelMethod  method : methodList) {
//			new BlockStmt().addStatement(new ReturnStmt(new NameExpr("title")));
//			//modelClass.addMethod(method.getName(), Modifier.Keyword.valueOf(method.getAccessControl())).setBody(method.getBody());
//		}
		
		
	}
	
	public static void main(String[] args) {
		
		
//		ModelMeta model = new ModelMeta("order", "OrderInfo", "订单信息",  "订单信息描述的信息", "com.temp.order.OrderInfo");
//		model.setAutoAudit(true);
//		model.setLogic(true);
//		model.setPackageName("com.temp.order");
//		
//		List<UniqueConstraintMeta> ucList = new LinkedList<>();
//		UniqueConstraintMeta uc = new UniqueConstraintMeta();
//		uc.setPrompt("prompt");
//		uc.setAttributes(Lists.newArrayList("name","code"));
//		ucList.add(uc);
//		model.setUniqueConstraints(ucList);
//		
//		List<AttributeMeta> attrList = new LinkedList<>();
//		attrList.add(new AttributeMeta(0, "name", "private".toUpperCase(), "java.lang.String", "名字", "订单的名字", true, true, CreateType.CUSTOMIZED.name()));
//		attrList.add(new AttributeMeta(1, "code", "private".toUpperCase(), "java.lang.String", "编号", "订单的编号", true, false, CreateType.CUSTOMIZED.name()));
//		attrList.add(new AttributeMeta(2, "number", "private".toUpperCase(), "java.lang.Integer", "数量", "订单的数量", false, true, CreateType.CUSTOMIZED.name()));
//		
//		try {
//			new ProtocolHandler().generateJavaSource(model, attrList, null,null);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
//		List<ModelRelation> relationList = new LinkedList<>();
//		ModelRelation relation = new ModelRelation("com.temp.ceshinew.dao.entity.Adress", "com.temp.ceshinew.dao.entity.Orgin", false, "Adress", "Orgin", AssociationRelation.Association.AGGREGATION);
//		relation.setTargetAttrName("adressId");
//		relationList.add(relation);
//		new ProtocolHandler().modifyJavaSource("Adress", new File("C:\\opt\\lck\\18\\workspace\\PZ23\\PZ23-server\\src\\main\\java\\com\\temp\\ceshinew\\dao\\entity\\Adress.java"), relationList);
	}
	public void modifyJavaSource(String modelName, File sourceFile,List<ModelRelationMeta> relationList) {
		  
		try {
			CompilationUnit cu = StaticJavaParser.parse(sourceFile);
			ClassOrInterfaceDeclaration modelClass = cu.getClassByName(modelName).get();
			modelClass.addImplementedType(AssociationRelation.class);
			modelClass.addImplementedType(Amount.class);
			modelClass.addImplementedType(AssociationRelation.Association.class);
			
			AnnotationExpr expr = modelClass.getAnnotationByClass(com.onezhier.rock.protocol.annotation.Model.class).get();
			if(expr.isNormalAnnotationExpr()) {
				//删除原来的关系
				NormalAnnotationExpr naExpr = (NormalAnnotationExpr)expr;
				MemberValuePair exit = null;
				for (MemberValuePair pair :naExpr.getPairs()) {
					if(pair.getNameAsString().equals("associationRelationes")) {
						exit = pair;
					}
				}
				if(exit!=null) {
					exit.remove();
				}
				
				
				NodeList  relationNodeList_ = new NodeList<>();
				
				for(ModelRelationMeta relation : relationList) {
					
					NodeList  attrNodeList = new NodeList<>();
					attrNodeList.add(new MemberValuePair("otherClass", new ClassExpr(new ClassOrInterfaceType(relation.getTargetClassName()))));
					attrNodeList.add(new MemberValuePair("otherAttrName",  new StringLiteralExpr(relation.getTargetAttrName())));
					attrNodeList.add(new MemberValuePair("otherAmount", new FieldAccessExpr(new TypeExpr(new ClassOrInterfaceType(Amount.class.getSimpleName())), relation.getMapTooMany()?"MANY":"SIGNAL")));

					attrNodeList.add(new MemberValuePair("associationType", new FieldAccessExpr(new TypeExpr(new ClassOrInterfaceType(Association.class.getSimpleName())), relation.getAssociationType().name())));
					
					
					relationNodeList_.add(new NormalAnnotationExpr(new Name("AssociationRelation"), attrNodeList));
				}
				
				
				naExpr.addPair("associationRelationes", new ArrayInitializerExpr(relationNodeList_) );
			}
			FileUtils.write(new File(sourceFile.getAbsolutePath()), cu.toString(), "UTF-8", false);
			
		} catch (IOException e) {
			throw new SysException(e.getMessage(),e);
		}
	}
	
	
//	public Tuple3<Model, List<ModelRelationShip>, List<Enum>> parseJavaSourceFile(File sourceFile) {
//		
//
//        List<ModelRelationShip> modelRelationShips = new ArrayList<>(100);
//        List<Model> models = new ArrayList<>(100);
//        List<Enum> enumInfos = new ArrayList<>(20);
//        //key:类全路径或者枚举全路径，value:如果是枚举，则为true,否则false
//        Map<String, Boolean> classNameOrEnumsMap = new HashMap<>();
//            CompilationUnit cu;
//			try {
//				cu = StaticJavaParser.parse(sourceFile);
//			} catch (FileNotFoundException e) {
//				throw new SysException(e.getMessage(),e);
//			}
//            List<ClassOrInterfaceDeclaration> classOrInterfaceDeclarations = cu.findAll(ClassOrInterfaceDeclaration.class);
//          
//            
//            if (classOrInterfaceDeclarations.isEmpty()) {
//                List<EnumDeclaration> enumDeclarations = cu.findAll(EnumDeclaration.class);
//                if (enumDeclarations.isEmpty()) {
//                   	return null;
//                }
//                for (EnumDeclaration enumDeclaration : enumDeclarations) {
//                    Enum enumInfo = parseForEnum(enumDeclaration, cu);
//                    if (enumInfo == null) {
//                       return null;
//                    }
//                    classNameOrEnumsMap.put(enumInfo.getClassPath(), true);
//                    enumInfos.add(enumInfo);
//                }
//            } else {
//                ClassOrInterfaceDeclaration classOrInterfaceDeclaration = classOrInterfaceDeclarations.get(0);
//                Optional<AnnotationExpr> entityAnnotation = classOrInterfaceDeclaration.getAnnotationByClass(com.onezhier.rock.protocol.annotation.Model.class);
//                if (!entityAnnotation.isPresent()) {
//                  return null;
//                }
//                Model model = parseModel(classOrInterfaceDeclaration, cu, entityAnnotation.get(), modelRelationShips, classNameOrEnumsMap);
//                classNameOrEnumsMap.put(model.getName(), false);
//                models.add(model);
//            }
//        
//        return new  Tuple3(models,modelRelationShips,enumInfos);
//	}
	
	public Tuple3<List<ModelMeta>, List<ModelRelationMeta>, List<Enum>> parseJavaSourceFile(Set<File> sourceFiles) {
		

        List<ModelRelationMeta> modelRelationShips = new ArrayList<>(100);
        List<ModelMeta> models = new ArrayList<>(100);
        List<Enum> enumInfos = new ArrayList<>(20);
        //key:类全路径或者枚举全路径，value:如果是枚举，则为true,否则false
        Map<String, Boolean> classNameOrEnumsMap = new HashMap<>();
        for (File sourceFile : sourceFiles) {
            CompilationUnit cu;
			try {
				cu = StaticJavaParser.parse(sourceFile);
			} catch (FileNotFoundException e) {
				throw new SysException(e.getMessage(),e);
			}
            List<ClassOrInterfaceDeclaration> classOrInterfaceDeclarations = cu.findAll(ClassOrInterfaceDeclaration.class);
            if (classOrInterfaceDeclarations.isEmpty()) {
                List<EnumDeclaration> enumDeclarations = cu.findAll(EnumDeclaration.class);
                if (enumDeclarations.isEmpty()) {
                    continue;
                }
                for (EnumDeclaration enumDeclaration : enumDeclarations) {
                    Enum enumInfo = parseForEnum(enumDeclaration, cu);
                    if (enumInfo == null) {
                        continue;
                    }
                    classNameOrEnumsMap.put(enumInfo.getClassPath(), true);
                    enumInfos.add(enumInfo);
                }
            } else {
                ClassOrInterfaceDeclaration classOrInterfaceDeclaration = classOrInterfaceDeclarations.get(0);
                Optional<AnnotationExpr> entityAnnotation = classOrInterfaceDeclaration.getAnnotationByClass(com.onezhier.rock.protocol.annotation.Model.class);
                if (!entityAnnotation.isPresent()) {
                    continue;
                }
                ModelMeta model = parseModel(classOrInterfaceDeclaration, cu, entityAnnotation.get(), modelRelationShips, classNameOrEnumsMap);
                classNameOrEnumsMap.put(model.getName(), false);
                models.add(model);
            }
        }
        
        return new  Tuple3(models,modelRelationShips,enumInfos);
	}
	
	
	private List<AttributeMeta> parseFieldes( List<FieldDeclaration> fieldDeclarations,Map<String, String> importMap,String name,String packageName ,List<ModelRelationMeta> modelRelationShips){
	        List<AttributeMeta> modelAttributes = new ArrayList<>(20);
	        List<MethodMeta> modelMethods = new ArrayList<>(20);
	        int index = 0;
	        for (FieldDeclaration fieldDeclaration : fieldDeclarations) {
	            Optional<Modifier> first = fieldDeclaration.getModifiers().getFirst();
	            VariableDeclarator variable = fieldDeclaration.getVariable(0);
	            Optional<AnnotationExpr> annotationByClass = fieldDeclaration.getAnnotationByClass(Attribute.class);
	            String displayName = variable.getName().toString();
	            String comment = "";
	            boolean unique = false;
	            boolean notNull = true;
	            String classify = "";
	            if (annotationByClass.isPresent()) {
	                Map<String, Object> properties = extraPropertiesFromNormalAnnotationExpr((NormalAnnotationExpr) annotationByClass.get());
	                displayName = (String) properties.getOrDefault("alias", displayName);
	                comment = (String) properties.getOrDefault("comment", comment);
	                unique = (boolean) properties.getOrDefault("unique", false);
	                notNull = (boolean) properties.getOrDefault("notNull", false);
	                classify = (String) properties.getOrDefault("classify", CreateType.CUSTOMIZED.name());
	            }
	            TypeInfo typeFullName = getType(variable.getType(), importMap, packageName);

	            AttributeMeta modelAttribute = new AttributeMeta(index++, variable.getName().toString(), first.get().getKeyword().asString(), typeFullName.getName(), displayName, comment,unique,notNull,classify);
	            modelAttributes.add(modelAttribute);
	            if (!BASIC_DATA_TYPES.contains(variable.getType().toString()) && variable.getType().isReferenceType()) {
	                TypeInfo typeInfo = getType(variable.getType(), importMap, packageName);
	                ModelRelationMeta modelRelationShip = new ModelRelationMeta(packageName+"."+name, typeInfo.getName(), typeInfo.isList,name,typeInfo.getSimpleName(),Association.AGGREGATION);
	                modelRelationShips.add(modelRelationShip);
	            }

	        }
	        return modelAttributes;
	}
	
	
	public List<AttributeMeta> parseFieldes(String modelClassPath,ClassLoader classLoader){
		List<AttributeMeta> result = new LinkedList<>();
		try {
			Class clazz = Class.forName(modelClassPath, true, classLoader);
			int[] idx = {0};
			ReflectionUtils.doWithFields(clazz, new FieldCallback() {
				
				@Override
				public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
					Attribute attr = field.getAnnotation(Attribute.class);
					AttributeMeta  modelAttribute = new AttributeMeta(idx[0], field.getName(), java.lang.reflect.Modifier.toString(field.getModifiers()) , field.getType().getName(), attr.alias(), attr.comment(), attr.unique(), attr.notNull(),attr.classify().name());
					result.add(modelAttribute);
				}
			}, new FieldFilter() {
				
				@Override
				public boolean matches(Field field) {
					return field.getAnnotation(Attribute.class)!=null;
				}
			});
		} catch (ClassNotFoundException e) {
			throw new SysException(e.getMessage(),e);
		}
		return result;
	}
	
	private MethodMeta convert(com.onezhier.rock.protocol.annotation.Method f,Method method,Integer idx) {
		List<ParameterMeta> paramList = new LinkedList<>();
	    int index = 0;		
	   
		for(Parameter param : method.getParameters()) {
			ParameterMeta p = new ParameterMeta(param.getName(), param.getType().getName(), true, index);
			paramList.add(p);
			index ++;
		}
		paramList.add(new ParameterMeta("returnVal",  method.getReturnType().getName(), false, 0));
		return  new MethodMeta(idx, method.getName(), java.lang.reflect.Modifier.toString(method.getModifiers()) , f.comment(), null, f.exposed(), f.alias(), paramList,f.classify().name());
	}
	
	public List<MethodMeta> parseMethodes(String serviceClassPath,ClassLoader classLoader){
		List<MethodMeta> result = new LinkedList<MethodMeta>();
		try {
			Class clazz = Class.forName(serviceClassPath, true, classLoader);
			int[] idx = {0};
			ReflectionUtils.doWithMethods(clazz, new MethodCallback() {
				
				public void doWith(Method method) throws IllegalArgumentException, IllegalAccessException {
					
					com.onezhier.rock.protocol.annotation.Method f = method.getAnnotation(com.onezhier.rock.protocol.annotation.Method.class);
					result.add(ProtocolHandler.this.convert(f, method, idx[0]));
					idx[0] = idx[0]+1;
				}
			}, new org.springframework.util.ReflectionUtils.MethodFilter() {
				
				@Override
				public boolean matches(Method method) {
					return method.getAnnotation(com.onezhier.rock.protocol.annotation.Method.class)!=null;
				}
			});
		} catch (ClassNotFoundException e) {
			throw new SysException(e.getMessage(),e);
		}
		
		return result;
	}
	
	public List<MethodMeta> parseMethodes(File javaSourceFile){
		
        
        CompilationUnit cu;
		try {
			cu = StaticJavaParser.parse(javaSourceFile);
		} catch (FileNotFoundException e) {
			throw new SysException(e.getMessage(),e);
		}
		
		
		  String packageName = cu.findAll(PackageDeclaration.class).get(0).getName().toString();
	        List<ImportDeclaration> importDeclarations = cu.findAll(ImportDeclaration.class);
	        
	        Map<String, String> importMap = new HashMap<>();
	        for (ImportDeclaration importDeclaration : importDeclarations) {
	            String importName = importDeclaration.getName().toString();
	            String key = importName.substring(importName.lastIndexOf(".") + 1);
	            importMap.put(key, importName);
	        }
	        
	   return   this.parseMethodes(cu.findAll(MethodDeclaration.class), importMap, packageName);
      
    
	}
	
	private List<MethodMeta> parseMethodes(List<MethodDeclaration> allMethod,Map<String, String> importMap,String packageName){
		List<MethodMeta> modelMethods = new ArrayList<>(20);
	       int index = 0;
	        for (MethodDeclaration methodDeclaration : allMethod) {
	            Optional<AnnotationExpr> annotationByClass = methodDeclaration.getAnnotationByClass(com.onezhier.rock.protocol.annotation.Method.class);
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
	            NodeList<com.github.javaparser.ast.body.Parameter> parameters = methodDeclaration.getParameters();
	            int pIndex = 0;
	            for (com.github.javaparser.ast.body.Parameter parameter : parameters) {
	                TypeInfo typeInfo = getType(parameter.getType(), importMap, packageName);
	                ParameterMeta modelMethodParameter = new ParameterMeta(parameter.getName().asString().replaceAll("\"", ""), typeInfo.getName(), true, pIndex++);
	                modelMethodParameters.add(modelMethodParameter);
	            }
	            String returnType = methodDeclaration.getType().toString();
	            if (!"void".equals(returnType)) {
	                TypeInfo typeInfo = getType(methodDeclaration.getType(), importMap, packageName);
	                ParameterMeta modelMethodParameter = new ParameterMeta("returnVal", typeInfo.getName(), false, 0);
	                modelMethodParameters.add(modelMethodParameter);
	            }

	            MethodMeta modelMethod = new MethodMeta(index++, methodDeclaration.getNameAsString(), methodDeclaration.getModifiers().getFirst().get().getKeyword().asString(), comment, methodDeclaration.toString(), exposed, alias, modelMethodParameters,classify);
	            modelMethods.add(modelMethod);
	        }
	        
	        return modelMethods;
	}
	
	
	
	 private ModelMeta parseModel(ClassOrInterfaceDeclaration classOrInterfaceDeclaration, CompilationUnit cu, AnnotationExpr annotationExpr, List<ModelRelationMeta> modelRelationShips, Map<String, Boolean> classNameOrEnumsMap) {
	        String packageName = cu.findAll(PackageDeclaration.class).get(0).getName().toString();
	        List<ImportDeclaration> importDeclarations = cu.findAll(ImportDeclaration.class);

//	        List<String> importList = importDeclarations.stream().map((importDeclaration) -> {
//	            return importDeclaration.getName().asString();
//	        }).collect(Collectors.toList());


	        Map<String, String> importMap = new HashMap<>();
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
	        if (annotationExpr instanceof NormalAnnotationExpr) {
	            Map<String, Object> properties = extraPropertiesFromNormalAnnotationExpr((NormalAnnotationExpr) annotationExpr);
	            storeClassName = (String) properties.getOrDefault("storeClassName", storeClassName);
	            modelAlias = (String) properties.getOrDefault("alias", modelAlias);
	            modelDescription = (String) properties.getOrDefault("description", modelDescription);
	            group = (String) properties.getOrDefault("group", group);
	        }
	        
	        List<AttributeMeta> modelAttributes = this.parseFieldes(classOrInterfaceDeclaration.getFields(), importMap, classOrInterfaceDeclaration.getName().asString(), packageName, modelRelationShips);
	        
	        List<MethodMeta> modelMethods =  this.parseMethodes(cu.findAll(MethodDeclaration.class), importMap, packageName);
	        

	        return new ModelMeta(group, name, modelAlias, modelDescription, storeClassName, modelAttributes, modelMethods);
	    }
	 
	 
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

	    private Enum parseForEnum(EnumDeclaration declaration, CompilationUnit cu) {
	        Optional<AnnotationExpr> annotationByClass = declaration.getAnnotationByClass(EnumTag.class);
	        if (annotationByClass.isPresent()) {
	            String packageName = cu.findAll(PackageDeclaration.class).get(0).getName().toString();
	            AnnotationExpr annotationExpr = annotationByClass.get();
	            String classPath = packageName + "." + declaration.getNameAsString().replaceAll("\"", "");
	            String name = declaration.getNameAsString().replaceAll("\"", "");
	            String alias = name;
	            if (annotationExpr instanceof SingleMemberAnnotationExpr) {
	                alias = ((SingleMemberAnnotationExpr) annotationExpr).getMemberValue().toString().replaceAll("\"", "");
	            }
	            List<EnumItem> enumItems = new ArrayList<>(10);
	            for (EnumConstantDeclaration entry : declaration.getEntries()) {
	                String itemName = entry.getName().asString();
	                Expression aliasExp = entry.getArgument(0);
	                String itemAlias = aliasExp.toString().replaceAll("\"", "");
	                String valueType = "";
	                Object value = null;
	                Expression valueExp = entry.getArgument(1);
	                if (valueExp.isBooleanLiteralExpr()) {
	                    valueType = "Boolean";
	                    value = Boolean.valueOf(valueExp.toString().replaceAll("\"", ""));
	                }
	                if (valueExp.isLongLiteralExpr() || valueExp.isIntegerLiteralExpr()) {
	                    valueType = "Number";
	                    value = Long.valueOf(valueExp.toString().replaceAll("\"", ""));
	                }
	                if (valueExp.isCharLiteralExpr()) {
	                    valueType = "String";
	                    value = valueExp.toString().replaceAll("\"", "");
	                }
	                if (value == null) {
	                    throw new RuntimeException(classPath + "枚举类型不合法");
	                }
	                Expression description = entry.getArgument(2);

	                EnumItem enumItem = new EnumItem(itemName, itemAlias, valueType, description.toString().replaceAll("\"", ""), value);
	                enumItems.add(enumItem);
	            }
	            return new Enum(classPath, name, alias, enumItems);
	        }
	        return null;
	    }
	    
	    static final List<String> BASIC_DATA_TYPES = Arrays.asList("int","float","double","long","boolean");

	    static final List<String> WRAPPER_DATA_TYPES = Arrays.asList("String","Integer","Float","Double","Long","Boolean");
	    
	    public static TypeInfo getType(Type type, Map<String, String> importMap, String packageName) {
	        String typeName = type.toString();
	        if (BASIC_DATA_TYPES.contains(typeName)) {
	            return new TypeInfo(typeName, false, false,typeName);
	        }
	        if(WRAPPER_DATA_TYPES.contains(typeName)){
	            return new TypeInfo(String.format("%s%s","java.lang.",typeName),false,false,typeName);
	        }
	        if("Date".equals(typeName)){
	            return new TypeInfo("java.util.Date",false,false,"Date");
	        }

	        if (importMap.containsKey(typeName)) {
	            return new TypeInfo(importMap.get(typeName), false, false,typeName);
	        }
	        if (type instanceof ClassOrInterfaceType) {
	            List<Node> childNodes = type.getChildNodes();
	            Node firstNode = childNodes.get(0);
	            if (firstNode instanceof SimpleName && "List".equals(((SimpleName) firstNode).getIdentifier())) {
	                StringBuilder sb = new StringBuilder();
	                sb.append("List<");
	                for (int i = 1; i < childNodes.size(); i++) {
	                    Node node = childNodes.get(i);
	                    if (node instanceof SimpleName) {
	                        break;
	                    } else {
	                        ClassOrInterfaceType nodeType = (ClassOrInterfaceType) node;
	                        String s = nodeType.getElementType().toString();
	                        sb.append(importMap.getOrDefault(s, packageName + "." + s));
	                    }
	                }
	                sb.append(">");
	                return new TypeInfo(sb.toString(), true, false,typeName);
	            }
	            if (firstNode instanceof SimpleName && "Map".equals(((SimpleName) firstNode).getIdentifier())) {
	                return new TypeInfo(typeName, false, true,typeName);
	            }
	        }
	        return new TypeInfo(packageName + "." + typeName, false, false,typeName);
	    }

	    @Getter
	    public static class TypeInfo {
	        private String name;
	        
	        private Boolean isList;
	        
	        private Boolean isMap;
	        
	        private String simpleName;
	        
	        public TypeInfo(String name, Boolean isList, Boolean isMap,String simpleName) {
	            this.name = name;
	            this.isList = isList;
	            this.isMap = isMap;
	            this.simpleName = simpleName;
	        }

	       
	    }


}
