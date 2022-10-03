package com.jguru.assignmentui.ui;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import com.jguru.assignmentui.clientcontroller.HplPatientClient;
import com.jguru.assignmentui.model.HplFilter;
import com.jguru.assignmentui.model.HplFilterSort;
import com.jguru.assignmentui.model.HplPatientMaster;
import com.jguru.assignmentui.model.HplPatientMasterResponse;
import com.jguru.assignmentui.model.HplSort;
/**
 * Class declared to create table in SWT UI.
 */
import com.jguru.assignmentui.util.ClientUtil;
public class GridView {
	Table table = null;
	Button create = null, update = null, delete = null, view = null;

	HplPatientClient hplPatientClient = new HplPatientClient();
	//ClientUtil util = new ClientUtil();
	PatientView patientView = new PatientView();
//	TableRowUI tableRowUI = new TableRowUI();

	/**
	 * Method defined to create table view.
	 */
	public void patientGridView() {
		Display display = new Display();
		Color gray = display.getSystemColor(SWT.COLOR_DARK_GRAY);
		Color listBackground = display.getSystemColor(SWT.COLOR_LIST_BACKGROUND);
		Shell shell = new Shell(display);
		shell.setText("Patient Management");
		shell.setBackground(gray);
		shell.setLayout(new GridLayout(5, false));
				
		Text searchText;
		searchText = new Text(shell, SWT.SEARCH | SWT.ICON_CANCEL | SWT.ICON_SEARCH);
		searchText.setMessage("Search");
		searchText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		create = new Button(shell, SWT.PUSH);
		create.setText("Create");
		create.setBackground(listBackground);

		update = new Button(shell, SWT.LEFT);
		update.setText("Update");
		update.setBackground(listBackground);
		update.setEnabled(false);

		delete = new Button(shell, SWT.LEFT);
		delete.setText("Delete");
		delete.setBackground(listBackground);
		delete.setEnabled(false);

		view = new Button(shell, SWT.LEFT);
		view.setText("View");
		view.setBackground(listBackground);
		view.setEnabled(false);


		table = new Table(shell, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		data.heightHint = 200;
		table.setLayoutData(data);
		String[] titles = { "Patient id", "Patient Name","Gender", "Date Of Birth" ,"Address", "Phone Number"};
		for (String title : titles) {
			TableColumn column = new TableColumn(table, SWT.NONE);
			column.setText(title);
		}

		for (int i = 0; i < titles.length; i++) {
			table.getColumn(i).pack();
		}
		try {
			fillTable(hplPatientClient.getPatients());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		table.addListener(SWT.Selection, event -> {
			update.setEnabled(true);
			view.setEnabled(true);
			delete.setEnabled(true);
		});

		searchText.addListener(SWT.DefaultSelection, getSearchListener());
		create.addListener(SWT.Selection, getButtonsListener(display));
		view.addListener(SWT.Selection, getButtonsListener(display));
		update.addListener(SWT.Selection, getButtonsListener(display));
		delete.addListener(SWT.Selection, getButtonsListener(display));
		

		shell.pack();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
	
	/**
	 * To search the filter keyword results.
	 * @return {@link Listener}
	 */
	private Listener getSearchListener() {
		Listener listener = event -> {
			//create.setEnabled(false);
			Text searchKeyword = (Text) event.widget;
			if(!searchKeyword.getText().equals("")) {
				create.setEnabled(false);
			} else {
				create.setEnabled(true);
			}
			String value = searchKeyword.getText();

			HplSort sort = new HplSort("patientId", "asc");
			List<HplSort> sorts = new ArrayList<>();
			sorts.add(sort);
			List<HplFilter> filters = new ArrayList<>();
			if(!value.isEmpty() || !value.equals("")) {
				HplFilter filter = new HplFilter("like", searchKeyword.getText(), "patientName");
				filters.add(filter);
			}
			HplFilterSort filterSort = new HplFilterSort(sorts, filters);
			 int page=1;
			 int limit=250;
			HplPatientMasterResponse response = new HplPatientMasterResponse();
			try {
				response = hplPatientClient.getPatientDetails(filterSort, page, limit);
				fillTable(response.getRecords());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		};
		return listener;
	}
	
	
	/**
	 * To search the filter keyword results.Method defined to create listener for create, update, view and delete buttons.
	 * @param display {@link Display}
	 */
	private Listener getButtonsListener(Display display) {
		Listener listener = event -> {
			Button button = (Button) event.widget;
			HplPatientMaster patientMaster = new HplPatientMaster();			
			System.out.println("Update1 --" +button.getText());
			
			for (TableItem item : table.getSelection()) {
				
				patientMaster.setPatientId(Integer.parseInt(item.getText(0)));
				patientMaster.setPatientName(item.getText(1));
				patientMaster.setGender(item.getText(2));
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				Date dob = null;
				try {
					dob = format.parse(item.getText(3));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				patientMaster.setDob(dob);
				patientMaster.setAddress(item.getText(4));
				patientMaster.setTelephoneNo(item.getText(5));

				
			}
			if(ClientUtil.PM_VIEW_BUTTON.equalsIgnoreCase(button.getText())) {
				try {
					patientView.patientViewFill(table, display, ClientUtil.PM_VIEW_BUTTON, view.getText(), patientMaster);
					disableButtons();
				} catch (IOException | InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} 
			else if(ClientUtil.PM_DELETE_BUTTON.equalsIgnoreCase(button.getText())) {
				try {
					int statusCode = hplPatientClient.deletePatient(patientMaster.getPatientId());
					if (statusCode == 200) {
						table.remove(table.getSelectionIndices());
					}
					disableButtons();
				} catch (NumberFormatException | IOException | InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if(ClientUtil.PM_UPDATE_BUTTON.equalsIgnoreCase(button.getText())) {
				try {
					System.out.println("Update");
					patientView.patientViewFill(table, display, ClientUtil.PM_UPDATE_BUTTON, update.getText(),
							patientMaster);
				} catch (NumberFormatException | IOException | InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				disableButtons();
			} else if(ClientUtil.PM_CREATE_BUTTON.equalsIgnoreCase(button.getText())) {
				try {
					patientView.patientViewFill(table, display, ClientUtil.PM_CREATE_BUTTON, create.getText(), null);
				} catch (IOException | InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				disableButtons();
			}
		};
		return listener;
	}

	/**
	 * Method defined to make update, delete and view buttons disable.
	 */
	private void disableButtons() {
		view.setEnabled(false);
		delete.setEnabled(false);
		update.setEnabled(false);
	}
	
	/**
	 * Fill the table with the patients list.
	 * 
	 * @param patients {@link List} of {@link Patient}
	 */
	private void fillTable(List<HplPatientMaster> patients) {
		table.removeAll();
		if (!patients.isEmpty()) {
			final TableItem[] items = new TableItem[patients.size()];
			int i = 0;
			for (HplPatientMaster patient : patients) {
				new TableColumn(table, SWT.NONE).setWidth(patients.size());
				String dob = "";

				DateFormat dobDateFormat  = new SimpleDateFormat("yyyy-MM-dd");
				dob = dobDateFormat.format(patient.getDob());

				items[i] = new TableItem(table, SWT.NONE);
				//tableRowUI.tableItems(items[i], patients.get(i));
				items[i].setText(0, String.valueOf(patient.getPatientId()));
				items[i].setText(1, patient.getPatientName());
				items[i].setText(2, patient.getGender());
				items[i].setText(3, dob);
				items[i].setText(4, patient.getAddress());
				items[i].setText(5, patient.getTelephoneNo());
				i++;
			}
		}
	}
}
