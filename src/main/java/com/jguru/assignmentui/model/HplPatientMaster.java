package com.jguru.assignmentui.model;

import java.util.Date;


public class HplPatientMaster {
	
	private Integer patientId;
	private String patientName;
	private Date dob;
	private String gender;
	private String address;
	private String telephoneNo;
	private String isActive;

	
	/** Default Constructor */
	public HplPatientMaster() {
		
	}
	
	/** Full Constructor */
	public HplPatientMaster(Integer patientId, String patientName, Date dob, String gender, String address,
			String telephoneNo, String isActive) {
		super();
		this.patientId = patientId;
		this.patientName = patientName;
		this.dob = dob;
		this.gender = gender;
		this.address = address;
		this.telephoneNo = telephoneNo;
		this.isActive = isActive;

	}

	public Integer getPatientId() {
		return patientId;
	}
	public void setPatientId(Integer patientId) {
		this.patientId = patientId;
	}

	public String getPatientName() {
		return patientName;
	}
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
	
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getTelephoneNo() {
		return telephoneNo;
	}
	public void setTelephoneNo(String telephoneNo) {
		this.telephoneNo = telephoneNo;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	
}
