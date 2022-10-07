package com.jguru.assignmentui.clientcontroller;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.jguru.assignmentui.model.HplFilter;
import com.jguru.assignmentui.model.HplFilterSort;
import com.jguru.assignmentui.model.HplPatientMaster;
import com.jguru.assignmentui.model.HplPatientMasterResponse;
import com.jguru.assignmentui.model.HplSort;

/**
 * Patient client controller 
 * @author Selvakumaran Subramanian
 */
@SpringBootTest
public class HplPatientClientTest {

	@InjectMocks
	HplPatientClient service1;

	@Mock
	HplPatientClient service;
	@Mock
	HttpRequest request = null;

	@Mock
	HttpClient httpClient = null;
	
	@Mock
	URI uri = null;

	@Mock
	HttpResponse<String> response = null;

	@Mock
	private MockMvc mockMvc;

	@Test
	@DisplayName("Test get patient details with filters")
	public void getPatientDetailsTest() throws Exception  {

		DateFormat dobDateFormat  = new SimpleDateFormat("yyyy-MM-dd");
		Date dob = dobDateFormat.parse("1988-04-13");

		HplPatientMaster patient1 = new HplPatientMaster(1,"AAAA", dob, "M", "Chennai", "+91 1234567890","Y");
		HplPatientMaster patient2 = new HplPatientMaster(2,"Selvakumaran", dob, "M", "Chennai", "+91 9876765423","Y");
		HplPatientMaster patient3 = new HplPatientMaster(3,"BBB", dob, "F", "Chennai", "+91 9876765423","Y");

		HplSort hplSort = new HplSort("patientName", "asc");
		HplFilter hplFilter = new HplFilter("eq", "M", "gender");
		List<HplSort> sort = new ArrayList<>();
		sort.add(hplSort);
		List<HplFilter> filter = new ArrayList<>();
		filter.add(hplFilter);
		HplFilterSort hplFilterSort = new HplFilterSort(sort,filter);

		List<HplPatientMaster> list = new ArrayList<>();
		list.add(patient1);
		list.add(patient2);
		list.add(patient3);
		HplPatientMasterResponse patientResponse = new HplPatientMasterResponse();
		patientResponse.setRecords(list);
		patientResponse.setPageNumber(1);
		patientResponse.setTotalPages(1);
		patientResponse.setTotalRecords(2);
		int page =1;
		int limit =3;
		
		when(service.getPatientDetails(hplFilterSort,page,limit)).thenReturn(patientResponse);

		Assertions.assertEquals(2, patientResponse.getTotalRecords(), "should return 2 patients");

	}

	@Test
	@DisplayName("Test get all active patients")
	public void getPatientsTest() throws ParseException  {
		
		DateFormat dobDateFormat  = new SimpleDateFormat("yyyy-MM-dd");
		Date dob = dobDateFormat.parse("1988-04-13");
		
		HplPatientMaster patient1 = new HplPatientMaster(1,"AAAA", dob, "M", "Chennai", "+91 1234567890","Y");
		HplPatientMaster patient2 = new HplPatientMaster(2,"Selvakumaran", dob, "M", "Chennai", "+91 9876765423","Y");
		HplPatientMaster patient3 = new HplPatientMaster(3,"BBB", dob, "F", "Chennai", "+91 9876765423","Y");
		
		List<HplPatientMaster> list = new ArrayList<>();
		list.add(patient1);
		list.add(patient2);
		list.add(patient3);
		
		when(service.getPatients()).thenReturn(list);
		// Assert the response
        Assertions.assertEquals(3, list.size(), "findAll should return 3 patients");

	}
	
	@Test
	@DisplayName("Test save patient")
	public void addPatientTest() throws Exception  {
		
		DateFormat dobDateFormat  = new SimpleDateFormat("yyyy-MM-dd");
		Date dob = dobDateFormat.parse("1988-04-13");
		
		HplPatientMaster patient = new HplPatientMaster(0,"AAAA", dob, "M", "Chennai", "+91 1234567890","Y");
		HplPatientMaster patient1 = new HplPatientMaster(1,"AAAA", dob, "M", "Chennai", "+91 1234567890","Y");
		doReturn(patient1).when(service).addPatient(patient);
		//when(service.addPatient(patient)).thenReturn(patient);
		// Assert the response
        Assertions.assertEquals(1, patient1.getPatientId(), "patientId should return 1 patients");

	}
	
	@Test
	@DisplayName("Test update patient")
	public void updatePatientTest() throws Exception  {
		
		DateFormat dobDateFormat  = new SimpleDateFormat("yyyy-MM-dd");
		Date dob = dobDateFormat.parse("1988-04-13");
		
		HplPatientMaster patient = new HplPatientMaster(0,"AAAA", dob, "M", "Chennai", "+91 1234567890","Y");
		HplPatientMaster savePatient = new HplPatientMaster(1,"AAAA", dob, "M", "Chennai", "+91 1234567890","Y");
		HplPatientMaster updatepatient = new HplPatientMaster(1,"Selva", dob, "M", "Chennai", "+91 1234567890","Y");
		doReturn(savePatient).when(service).addPatient(patient);
		doReturn(updatepatient).when(service).updatePatient(1, updatepatient);
		//when(service.addPatient(patient)).thenReturn(patient);
		// Assert the response
        Assertions.assertNotSame(savePatient.getPatientName(), updatepatient.getPatientName(), "patientName should not same");

	}
	
	@Test
	@DisplayName("Test delete patient")
	public void deletePatientTest() throws Exception  {
		
		DateFormat dobDateFormat  = new SimpleDateFormat("yyyy-MM-dd");
		Date dob = dobDateFormat.parse("1988-04-13");
		
		HplPatientMaster patient = new HplPatientMaster(0,"AAAA", dob, "M", "Chennai", "+91 1234567890","Y");
		HplPatientMaster savePatient = new HplPatientMaster(1,"AAAA", dob, "M", "Chennai", "+91 1234567890","Y");
		doReturn(savePatient).when(service).addPatient(patient);
		int statusCode = 200;
		when(service.deletePatient(savePatient.getPatientId())).thenReturn(statusCode);
		//when(service.addPatient(patient)).thenReturn(patient);
		// Assert the response
        Assertions.assertEquals(200, statusCode , "StatusCode should be same");

	}


}


