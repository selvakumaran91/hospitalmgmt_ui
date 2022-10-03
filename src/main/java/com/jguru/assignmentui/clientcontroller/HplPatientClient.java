package com.jguru.assignmentui.clientcontroller;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jguru.assignmentui.model.HplFilterSort;
import com.jguru.assignmentui.model.HplPatientMaster;
import com.jguru.assignmentui.model.HplPatientMasterResponse;
import com.jguru.assignmentui.util.ClientUtil;

/**
 * Patient client controller 
 * @author Selvakumaran Subramanian
 */
public class HplPatientClient {

	HttpRequest request = null;

	URI uri = null;

	HttpResponse<String> response = null;


	/**
	 * To get all patient details with filters, sort and pagination
	 * @author Selvakumaran Subramanian
	 * @return list of patients
	 * @throws Exception
	 */
	public HplPatientMasterResponse getPatientDetails(HplFilterSort filterSort, int page, int limit)  {
		HplPatientMasterResponse result = new HplPatientMasterResponse();
		try {
			request = HttpRequest.newBuilder().uri(URI.create(ClientUtil.URI + "/patient/search?page="+page+"&size="+limit)).POST(HttpRequest.BodyPublishers.ofString(new ObjectMapper().writeValueAsString(filterSort)))
					.setHeader("Content-Type", "application/json").build();
			System.out.println(new ObjectMapper().writeValueAsString(filterSort));
			response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
			if (!response.body().isEmpty()) {
				result = new ObjectMapper().readValue(response.body(), new TypeReference<HplPatientMasterResponse>() {
				});
			} else {
				return result;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}


	/**
	 * To get all patient details
	 * @author Selvakumaran Subramanian
	 * @return list of patients
	 * @throws Exception
	 */
	public List<HplPatientMaster> getPatients()  {
		List<HplPatientMaster> result = new ArrayList<>();
		try {
			request = HttpRequest.newBuilder().uri(URI.create(ClientUtil.URI + "/patient")).GET()
					.setHeader("Content-Type", "application/json").build();
			response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
			if (!response.body().isEmpty()) {
				result = new ObjectMapper().readValue(response.body(), new TypeReference<List<HplPatientMaster>>() {
				});
			} else {
				return result;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}

	/**
	 * To add patient details
	 * @author Selvakumaran Subramanian
	 * @return list of patients
	 * @throws Exception
	 */
	public HplPatientMaster addPatient(HplPatientMaster patient) throws Exception {
		request = HttpRequest.newBuilder().uri(URI.create(ClientUtil.URI + "/patient"))
				.POST(HttpRequest.BodyPublishers.ofString(new ObjectMapper().writeValueAsString(patient)))
				.setHeader("Content-Type", "application/json").build();

		response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
		return new ObjectMapper().readValue(response.body(), HplPatientMaster.class);
	}

	/**
	 * To update a patient
	 * @author Selvakumaran Subramanian
	 * @return Updated patient details
	 * @throws Exception
	 */
	public HplPatientMaster updatePatient(Integer integer, HplPatientMaster patient) throws Exception {
		request = HttpRequest.newBuilder().uri(URI.create(ClientUtil.URI + "/patient/" + integer))
				.PUT(HttpRequest.BodyPublishers.ofString(new ObjectMapper().writeValueAsString(patient)))
				.setHeader("Content-Type", "application/json").build();

		response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
		return new ObjectMapper().readValue(response.body(), HplPatientMaster.class);
	}

	/**
	 * To delete a patient
	 * @author Selvakumaran Subramanian
	 * @throws Exception
	 */
	public int deletePatient(Integer patientId) throws IOException, InterruptedException {
		request = HttpRequest.newBuilder().uri(URI.create(ClientUtil.URI + "/patient/" + patientId)).DELETE().build();
		System.out.println(request);
		response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
		return response.statusCode();
	}
}


