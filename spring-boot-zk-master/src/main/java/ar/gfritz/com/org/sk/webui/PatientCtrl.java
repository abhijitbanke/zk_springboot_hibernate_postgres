package ar.gfritz.com.org.sk.webui;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.sys.ComponentsCtrl;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.googlecode.genericdao.search.Filter;

import ar.gfritz.com.org.sk.HibernateSearchObject;
import ar.gfritz.com.org.sk.PagedListWrapper;
import ar.gfritz.com.org.sk.bean.Employee;
import ar.gfritz.com.org.sk.bean.Patient;
import ar.gfritz.com.org.sk.webui.dao.EmployeeRepo;
import ar.gfritz.com.org.sk.webui.dao.EmployeeSpecifications;
import ar.gfritz.com.org.sk.webui.dao.PatientRepo;

@Component("patientCtrl")
@Scope("prototype")
public class PatientCtrl extends GenericForwardComposer<Window> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Textbox textbox_SearchEmployeeName, textbox_SearchEmployeTockenNumber;
	Button button_Search;
	Listbox listbox_EmployeeRef;
	Bandbox bandbox_EmployeeRef;
	
	Textbox textbox_PatientName  ;
	Combobox combobox_Relation;
	Datebox datebox_DOB;
	@Autowired
	PatientRepo patientRepo;
	@Autowired
	EmployeeRepo employeeRepo;
//	PatientDao patientDao = new PatientDao();
	Paging paging_EmployeeSearch;
	
	@Autowired
	PagedListWrapper<Employee> pagedListWrapper;
	
	public void onCreate$patientWindow(Event event) {
		System.out.println("OnCreate window");
		
		initEmployeeListbox();
	}
	
	
	private void initEmployeeListbox() {
		paging_EmployeeSearch.setPageSize(5);
		listbox_EmployeeRef.setItemRenderer(new ListitemRenderer<Employee>() {

			@Override
			public void render(Listitem listitem, Employee employee, int index) throws Exception {
				Listcell listcell_EmployeeName = new Listcell(employee.getEmployeeName());
				Listcell listcell_EmployeeTocken = new Listcell(employee.getEmployeeTockenNumber());
				
				listitem.appendChild(listcell_EmployeeName);
				listitem.appendChild(listcell_EmployeeTocken);
				listitem.setValue(employee);
				ComponentsCtrl.applyForward(listitem, "onDoubleClick=onDoubleClickEmployee");
//				listitem.addForward(Events.ON_DOUBLE_CLICK, listitem.getListbox(), null);
			}
		});
		
	}

	
	public void onDoubleClickEmployee(ForwardEvent event) {
		System.out.println("onDoubleClickEmployee");
		Listitem listitem = (Listitem) event.getOrigin().getTarget();
		Employee emp = listitem.getValue();
		
		bandbox_EmployeeRef.setValue(emp.getEmployeeName());
		bandbox_EmployeeRef.setAttribute("OBJECT", emp);
		
		bandbox_EmployeeRef.close();
		
	}

	public void onClick$button_Search(Event event) {
		System.out.println("onClick$button_Search");
		
		HibernateSearchObject<Employee> hso = new HibernateSearchObject<Employee>(Employee.class);
		if(!StringUtils.isEmpty(textbox_SearchEmployeTockenNumber.getValue())){
			hso.addFilter(Filter.ilike("employeeTockenNumber", "%"+textbox_SearchEmployeTockenNumber.getValue()+"%"));
		}
		if(!StringUtils.isEmpty(textbox_SearchEmployeeName.getValue())){
			hso.addFilter(Filter.ilike("employeeName", "%"+textbox_SearchEmployeeName.getValue()+"%"));
		}
		hso.setDistinct(true);
		pagedListWrapper.init(hso, listbox_EmployeeRef, paging_EmployeeSearch);
		/*Employee exampleEmployee = new Employee();
		exampleEmployee.setEmployeeName(textbox_SearchEmployeeName.getValue());
		exampleEmployee.setEmployeeTockenNumber(textbox_SearchEmployeTockenNumber.getValue());
		 
		EmployeeSpecifications employeeSpecifications = new EmployeeSpecifications(exampleEmployee);
		List<Employee> list = employeeRepo.findAll(employeeSpecifications); //  getEmployeesByExample(exampleEmployee);
		listbox_EmployeeRef.setModel(new ListModelList<Employee>(list));*/
		
	}
	
	public void onClick$button_SavePatient(Event event) throws Exception {
		System.out.println("onClick$button_SavePatient");
		doSave();
	}
	
	/*
	 * public void onClickButton_SaveEmployee(ForwardEvent event) throws Exception {
	 * System.out.println("onClick$button_SaveEmployee"); doSave(); }
	 */
	
	public void doSave() throws Exception {
		if(!isValidate()) {
			return;
		}
		Employee employee = ((Employee)bandbox_EmployeeRef.getAttribute("OBJECT"));
		Patient pat = new Patient( employee.getEmployeeId(), textbox_PatientName.getValue(), combobox_Relation.getValue(), datebox_DOB.getValue());
		
		Long maxId = patientRepo.findMaxPatientId();
		pat.setPatientId((maxId != null ? maxId : 0)+1);
		patientRepo.save(pat);
		Messagebox.show("Patient have been saved!!");
		clearForm();
		
		
		/*int val = patientDao.doSave(pat);
		if(val > -1) {
			Messagebox.show("Patient have been saved!!");
			clearForm();
		}*/
		
	}

	private boolean isValidate() throws Exception {
		if(textbox_PatientName.getValue() == null) {
			Messagebox.show("Please enter Name");
			return false;
		}
		if(combobox_Relation.getSelectedIndex() == -1) {
			Messagebox.show("Please select Relation");
			return false;
		}
		if(bandbox_EmployeeRef.getText() == null || bandbox_EmployeeRef.getText().trim().isEmpty()) {
			Messagebox.show("Please select employee");
			return false;
		}
		
		if(datebox_DOB.getValue() == null) {
			Messagebox.show("Please select date of birth");
			return false;
		}
		return true;
	}
	
	private void clearForm() {
		textbox_PatientName.setValue(null);
		combobox_Relation.setSelectedIndex(-1);
		bandbox_EmployeeRef.setValue(null);
		datebox_DOB.setValue(null);
	} 
	
	
	public void onOpen$bandbox_EmployeeRef(Event event) {
		System.out.println("onOpen$bandbox_EmployeeRef");
//		listbox_EmployeeRef.getI
		textbox_SearchEmployeeName.setValue(null);
		textbox_SearchEmployeTockenNumber.setValue(null);
		listbox_EmployeeRef.setModel(new ListModelList<Employee>());
	}
}
