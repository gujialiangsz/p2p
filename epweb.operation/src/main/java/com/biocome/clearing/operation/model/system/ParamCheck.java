package com.biocome.clearing.operation.model.system;


/**
 * 参数校验 参数格式, [+]name=pattern
 * 
 */
public class ParamCheck {

	private boolean must = false;

	private String pname;

	private String pattern;

	public boolean isMust() {
		return must;
	}

	public void setMust(boolean must) {
		this.must = must;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

}
