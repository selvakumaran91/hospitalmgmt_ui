package com.jguru.assignmentui.ui;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.jguru.assignmentui.clientcontroller.HplPatientClientTest;

@SpringBootTest
public class GridViewTest {

	@InjectMocks
	private GridView service;

	@Mock
	HplPatientClientTest hplPatientClient = new HplPatientClientTest();

	@Mock
	PatientView patientView = new PatientView();


	public void patientGridViewTest() {


	}
}
