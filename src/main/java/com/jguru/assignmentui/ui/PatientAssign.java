package com.jguru.assignmentui.ui;

import java.text.SimpleDateFormat;

import org.eclipse.swt.widgets.TableItem;

import com.jguru.assignmentui.model.HplPatientMaster;

public class PatientAssign {


	public void rowItems(TableItem item, HplPatientMaster patient) {
		item.setText(0, String.valueOf(patient.getPatientId()));
		item.setText(1, patient.getPatientName());
		item.setText(2, patient.getGender());
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String dob = dateFormat.format(patient.getDob());
		item.setText(3, dob);
		item.setText(4, patient.getAddress());
		item.setText(5, patient.getTelephoneNo());


	}
}
