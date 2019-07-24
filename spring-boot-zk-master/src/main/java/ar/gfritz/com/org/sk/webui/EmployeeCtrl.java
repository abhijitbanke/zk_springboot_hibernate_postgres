package ar.gfritz.com.org.sk.webui;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import ar.gfritz.com.org.sk.bean.Employee;
import ar.gfritz.com.org.sk.bean.Patient;
import ar.gfritz.com.org.sk.webui.dao.EmployeeRepo;
import ar.gfritz.com.org.sk.webui.dao.PatientRepo;

@Component("employeeCtrl")
@Scope("prototype")
public class EmployeeCtrl extends GenericForwardComposer<Window> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Textbox textbox_EmployeeName, textbox_EmployeeAddress;
	Doublebox doublebox_EmployeeTockenNumber;
	@Autowired
	EmployeeRepo employeeRepo;
	
	@Autowired
	PatientRepo patientRepo;
	
	public void onCreate$employeeWindow(Event event) {
		System.out.println("OnCreate window");
	}
	
	
	public void onClick$button_SaveEmployee(Event event) throws Exception {
		System.out.println("onClick$button_SaveEmployee");
		doSave();
	}
	
	public void onClickButton_SaveEmployee(ForwardEvent event) throws Exception {
		System.out.println("onClick$button_SaveEmployee");
		doSave();
	}
	
	public void doSave() throws Exception {
		
		if(!isValidate()) {
			return;
		}
		
		Employee emp = new Employee(textbox_EmployeeName.getValue(), textbox_EmployeeAddress.getValue(), doublebox_EmployeeTockenNumber.getText());
		Long maxId = employeeRepo.findMaxEmployeeId();
		emp.setEmployeeId((maxId != null ? maxId : 0)+1);
		emp = employeeRepo.save(emp);
		Messagebox.show("Employee have been saved!!");
		
		Patient pat = new Patient( emp.getEmployeeId(), textbox_EmployeeName.getValue(), "Self", new Date());
		
		maxId = patientRepo.findMaxPatientId();
		pat.setPatientId((maxId != null ? maxId : 0)+1);
		patientRepo.save(pat);
		
		clearForm();
		/*int val =    employeeDao.doSave(emp);
		if(val > -1) {
			Messagebox.show("Employee have been saved!!");
			clearForm();
		}*/
		
	}

	private boolean isValidate() throws Exception {
		if(textbox_EmployeeName.getValue() == null) {
			Messagebox.show("Please enter Name");
			return false;
		}
		if(textbox_EmployeeAddress.getValue() == null) {
			Messagebox.show("Please enter Address");
			return false;
		}
		if(doublebox_EmployeeTockenNumber.getText() == null || doublebox_EmployeeTockenNumber.getText().trim().isEmpty()) {
			Messagebox.show("Please enter Tocken Number");
			return false;
		}else {
			Employee emp = new Employee();
			emp.setEmployeeTockenNumber(doublebox_EmployeeTockenNumber.getText());
			if(employeeRepo.existByEmployeeTocken(doublebox_EmployeeTockenNumber.getText().trim())){
				Messagebox.show("Employee already registered with given Tocken number");
			}
			/*List<Employee> list = employeeDao.getEmployeesByExample(emp);
			if(list != null && !list.isEmpty()) {
				Messagebox.show("Employee already registered with mobile number ("+list.get(0).getEmployeeName()+")");
			}*/
		}
		return true;
	}
	
	private void clearForm() {
		textbox_EmployeeName.setValue(null);
		textbox_EmployeeAddress.setValue(null);
		doublebox_EmployeeTockenNumber.setValue(null);
	} 
	
}
