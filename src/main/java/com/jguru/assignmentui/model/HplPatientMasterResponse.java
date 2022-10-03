package com.jguru.assignmentui.model;

import java.util.List;

public class HplPatientMasterResponse extends PaginationResponse {
	
	private List<HplPatientMaster> records;

	public List<HplPatientMaster> getRecords() {
		return records;
	}

	public void setRecords(List<HplPatientMaster> records) {
		this.records = records;
	}
	

}
