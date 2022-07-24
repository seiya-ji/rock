package com.onezhier.rock.framework.dal.db;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import lombok.Data;

@MappedSuperclass
@Data
public class TreeDO<T extends TreeDO> extends DO{
	
	@Column(name = "pid")
	private Long pid;
	
	private transient List<T>  childrens;

}
