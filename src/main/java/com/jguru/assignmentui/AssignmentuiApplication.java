package com.jguru.assignmentui;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.jguru.assignmentui.ui.GridView;

@SpringBootApplication
public class AssignmentuiApplication {

	public static void main(String[] args) {
		GridView gridView = new GridView();
		try {
			gridView.patientGridView();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
