package com.jguru.assignmentui.model;

public class HplFilter {
	
	private String operator;
	private String value;
	private String property;
	
	public HplFilter(String operator, String value, String property) {
		super();
		this.operator = operator;
		this.value = value;
		this.property = property;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	

}
