package com.jguru.assignmentui.model;

public class HplSort {
	
	private String property;
	private String dir;
	
	public HplSort(String property, String dir) {
		super();
		this.property = property;
		this.dir = dir;
	}
	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	public String getDir() {
		return dir;
	}
	public void setDir(String dir) {
		this.dir = dir;
	}

}
