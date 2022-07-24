/**
 * Copyright (c) 2010-2022 PuZhong.Co.Ltd. All Rights Reserved..
 */
package com.onezhier.rock.framework.util;

import java.util.List;

import lombok.Data;

@Data
public class CustomizeTypeProtocol  {
	
	/**
	 * 类型的名字，类的全限定名
	 */
	private String name;
	
	/**
	 * 类的类型，如 interface , abstract class , class
	 */
	private String type;

	
	/**
	 * 该类的超类型,类的全限定名，按顺序
	 */
	private List<String>  superTypeList;
	
	/**
	 * 该类的所有的方法，按顺序
	 */
	private List<MethodProtocol> methodList;
	
	/**
	 * 该类的所有的属性，按顺序
	 * 
	 */
	private List<AttributeProtocol> attributeList;
	
	
	@Data
	public static class MethodProtocol{
		
		/**
		 * 方法名
		 */
		private String name;
		
		/**
		 * 保护类型，private,  protect ,public 
		 */
		private String scope;
		
		/**
		 * 该方法的参数，按顺序
		 */
		private List<Entry>  inList;
		
		/**
		 * 该方法的返回，为null的话表示无返回
		 */
		private Entry out;
	}
	
	@Data
	public static class AttributeProtocol extends Entry{
		
	}
	
	@Data
	public static  class Entry {
		
		private String name;
		
		private String type;
	}

}
