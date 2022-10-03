package com.jguru.assignmentui.model;

import java.util.List;

public class HplFilterSort {
	
	private List<HplSort> sorts;
	private List<HplFilter> filters;
	
	public HplFilterSort(List<HplSort> sorts, List<HplFilter> filters) {
		super();
		this.sorts = sorts;
		this.filters = filters;
	}
	
	public List<HplSort> getSorts() {
		return sorts;
	}
	
	public void setSorts(List<HplSort> sorts) {
		this.sorts = sorts;
	}
	public List<HplFilter> getFilters() {
		return filters;
	}
	public void setFilters(List<HplFilter> filters) {
		this.filters = filters;
	}
}
