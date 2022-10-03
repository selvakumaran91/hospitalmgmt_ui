package com.jguru.assignmentui.ui;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.swt.widgets.TableItem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.jguru.assignmentui.model.HplPatientMaster;

@SpringBootTest
public class PatientAssignTest {
	
	@Mock
	PatientAssign patientAssign;
	
	@Mock
	TableItem item;
	
	@Test
	public void rowItemsTest() throws ParseException {
		DateFormat dobDateFormat  = new SimpleDateFormat("yyyy-MM-dd");
		Date dob = dobDateFormat.parse("1988-04-13");
		HplPatientMaster patient = new HplPatientMaster(1,"AAAA", dob, "M", "Chennai", "+91 1234567890","Y");
		doNothing().when(patientAssign).rowItems(item, patient);
		// Assert the response
		//Assertions.assertNotNull(item, "should not null");
	}

}
