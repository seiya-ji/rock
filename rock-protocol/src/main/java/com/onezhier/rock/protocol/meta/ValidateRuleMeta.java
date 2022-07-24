package com.onezhier.rock.protocol.meta;

import java.io.Serializable;

import lombok.Data;

@Data
public class ValidateRuleMeta implements Serializable{
	
	// see ValidatorType
	private String type;
	
	private String prompt;
	
	@Data
	public static class  DecimalRule extends ValidateRuleMeta {
		private Integer precision;
	}
	
	@Data
	public static class  NumberRule extends ValidateRuleMeta {
		
		private NumberClassify classify;
		
		
		
		public static enum NumberClassify{
			NEGATIVE,NEGATIVEORZERO,POSITIVEORZERO,POSITIVE;
		}
		
	} 
	@Data
	public static class  LimitRule extends ValidateRuleMeta {
		
		private Double max;
		
		private Double min;
	} 

}
