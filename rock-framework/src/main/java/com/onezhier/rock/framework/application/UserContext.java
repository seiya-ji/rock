/**
 * Copyright (c) 2010-2022 PuZhong.Co.Ltd. All Rights Reserved..
 */
package com.onezhier.rock.framework.application;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.math.NumberUtils;

import com.onezhier.rock.exception.SysException;

import lombok.Data;

/**
 * <p>Title: UserContext </p>
 * <p>Description: TODO </p>
 *
 * @author stephen.ji
 * @version v0.1
 * @date 2021/8/15 3:51 下午
 */
@Data
public class UserContext {

	private UserInfo userInfo;

	@Data
	public static class UserInfo {

		private String id;

		private String name;
		
		private Map<String, Object> extData  = new HashMap<>();

		public Long getLongId() {
			if (NumberUtils.isNumber(this.id)) {
				return Long.valueOf(this.id);
			}
			return null;
		}
		
		public Long getLongVal4Data(String key) {
			Object val = this.extData.get(key);
			if (val instanceof Long) {
				return Long.valueOf(val.toString());
			}
			return null;
		}
		
		public String getStringVal4Data(String key) {
			Object val = this.extData.get(key);
			if (val instanceof String) {
				return val.toString();
			}
			return null;
		}
		
		public void put2Data(String key,Object val) {
			Object preValue =  this.extData.put(key, val);
			if(preValue!=null) {
				throw new SysException("存在对应key的值");
			}
		}
		
		

	}

}
