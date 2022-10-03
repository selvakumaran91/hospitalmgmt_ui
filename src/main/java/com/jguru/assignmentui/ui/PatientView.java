package com.jguru.assignmentui.ui;

import static org.eclipse.swt.events.SelectionListener.widgetSelectedAdapter;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import com.jguru.assignmentui.clientcontroller.HplPatientClient;
import com.jguru.assignmentui.model.HplPatientMaster;
import com.jguru.assignmentui.util.ClientUtil;

public class PatientView {
	HplPatientClient patientClient = new HplPatientClient();

	Text patientIdText, patientNameText, dobText, genderText, addressText, telePhoneNumberText;
	Combo genderCombo;
	org.eclipse.swt.widgets.List genderList;

	int statusCode;
	
	PatientAssign rowAssign = new PatientAssign();


	/**
	 * Method defined to fill the UI with patient data.
	 * 
	 * @param table      {@link Table}
	 * @param display    {@link Display}
	 * @param viewName   {@link String}
	 * @param buttonType {@link String}
	 * @param PatientId  {@link Long}
	 * @throws {@link IOException}
	 * @throws {@link InterruptedException}
	 */
	public void patientViewFill(Table table, Display display, String viewName, String buttonType, HplPatientMaster patientMaster)
			throws IOException, InterruptedException {
		patientView(table, display, buttonType, viewName);

		if (buttonType.equalsIgnoreCase(ClientUtil.PM_UPDATE_BUTTON) || buttonType.equalsIgnoreCase(ClientUtil.PM_VIEW_BUTTON) || buttonType.equalsIgnoreCase(ClientUtil.PM_DELETE_BUTTON)) {
			if (ClientUtil.PM_UPDATE_BUTTON.equalsIgnoreCase(buttonType)) {
				patientIdText.setText(String.valueOf(patientMaster.getPatientId()));
			}
//			String[] string_array = new String[1];
//			string_array[0] =patientMaster.getGender(); 
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String dob = dateFormat.format(patientMaster.getDob());
			patientNameText.setText(patientMaster.getPatientName());
			genderCombo.setText(patientMaster.getGender());
//			genderList.add("M");
//			genderList.add("F");
//			genderList.add("T");
//			genderList.setSelection(string_array);
			dobText.setText(dob);
			addressText.setText(patientMaster.getAddress());
			telePhoneNumberText.setText(patientMaster.getTelephoneNo());
		}
	}

	/**
	 * Method defined to show the patient data in UI.
	 * 
	 * @param table      {@link Table}
	 * @param display    {@link Display}
	 * @param buttonType {@link String}
	 * @param viewName   {@link String}
	 */
	private void patientView(Table table, Display display, String buttonType, String viewName) {
		Display patientDisplay = display;
		Color gray = display.getSystemColor(SWT.COLOR_GRAY);
		Shell patientShell = new Shell(patientDisplay, SWT.CLOSE | SWT.TITLE | SWT.MIN | SWT.MAX | SWT.ON_TOP);
		patientShell.setSize(200, 300);
		patientShell.setBackground(gray);
		patientShell.setText(viewName);
		patientShell.setLayout(new GridLayout(2, true));

		if (ClientUtil.PM_UPDATE_BUTTON.equalsIgnoreCase(buttonType)) {
			Label patientIdLabel = new Label(patientShell, SWT.NONE);
			patientIdLabel.setText(ClientUtil.PATIENT_ID);
			patientIdText = new Text(patientShell, SWT.BORDER);
			patientIdText.setEditable(false);
			
		}
		Label patientName = new Label(patientShell, SWT.NONE);
		patientName.setText(ClientUtil.PATIENT_NAME);
		patientNameText = new Text(patientShell, SWT.BORDER);
		patientNameText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		Label genderLabel = new Label(patientShell, SWT.NONE);
		genderLabel.setText(ClientUtil.GENDER);
		genderCombo = new Combo(patientShell, SWT.FILL);
		genderCombo.setItems("M","F","T");
//		genderList = new org.eclipse.swt.widgets.List(patientShell, SWT.FILL);
//		genderList.setItems("M","F","T");

		Label dobLabel = new Label(patientShell, SWT.NONE);
		dobLabel.setText(ClientUtil.DATE_OF_BIRTH);
		dobText = new Text(patientShell, SWT.BORDER);
		dobText.setText("yyyy-MM-dd");
		dobText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		Label addressLabel = new Label(patientShell, SWT.NONE);
		addressLabel.setText(ClientUtil.ADDRESS);
		addressText = new Text(patientShell, SWT.BORDER);
		addressText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		Label telePhoneNumberLabel = new Label(patientShell, SWT.NONE);
		telePhoneNumberLabel.setText(ClientUtil.TELEPHONE_NUMBER);
		telePhoneNumberText = new Text(patientShell, SWT.BORDER);
		telePhoneNumberText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		if(ClientUtil.PM_VIEW_BUTTON.equalsIgnoreCase(buttonType)) {
			patientNameText.setEditable(false);
			genderCombo.setEnabled(false);
			dobText.setEditable(false);
			addressText.setEditable(false);
			telePhoneNumberText.setEditable(false);
		}
		
		if (ClientUtil.PM_UPDATE_BUTTON.equalsIgnoreCase(buttonType)
				|| ClientUtil.CREATE.equalsIgnoreCase(buttonType)) {
			Button ok = new Button(patientShell, SWT.PUSH);
			ok.setText(ClientUtil.SUBMIT);
			ok.addSelectionListener(widgetSelectedAdapter(a -> {
				try {
					if (ClientUtil.PM_UPDATE_BUTTON.equalsIgnoreCase(buttonType)) {
						HplPatientMaster patientResponse = patientClient.updatePatient(getPatient(ClientUtil.PM_UPDATE_BUTTON).getPatientId(),
								getPatient(buttonType));
						if (patientResponse != null) {
							rowAssign.rowItems(table.getSelection()[0], getPatient(ClientUtil.PM_UPDATE_BUTTON));
							patientShell.close();
						}
					} else {
						HplPatientMaster patientResponse = patientClient.addPatient(getPatient(ClientUtil.PM_CREATE_BUTTON));
						if (patientResponse != null) {
							TableItem item = new TableItem(table, SWT.NONE, patientClient.getPatients().size() - 1);
							rowAssign.rowItems(item, patientResponse);
							patientShell.close();
						}
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}));
		}
		patientShell.pack();
		patientShell.open();
	}

	/**
	 * Method defined to get patient data from UI.
	 * 
	 * @param buttonType {@link String}
	 * @return {@link Patient}
	 */
	private HplPatientMaster getPatient(String buttonType) {
		HplPatientMaster patient = new HplPatientMaster();
		if (ClientUtil.PM_UPDATE_BUTTON.equalsIgnoreCase(buttonType)) {
			patient.setPatientId(Integer.parseInt(patientIdText.getText()));
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date dob = null;
		try {
			dob = format.parse(dobText.getText());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		patient.setPatientName(patientNameText.getText());
		patient.setGender(genderCombo.getText());
		patient.setDob(dob);
		patient.setAddress(addressText.getText());
		patient.setTelephoneNo(telePhoneNumberText.getText());
		patient.setIsActive("Y");
		return patient;
	}
	
	
}


