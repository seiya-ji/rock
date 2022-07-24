package com.onezhier.rock.protocol.meta;

import java.util.List;

import lombok.Data;

@Data
public class UniqueConstraintMeta {
	
	private List<String> attributes;
	
	/**
	 * 错误提示语
	 * @return
	 */
	private String prompt;

}
