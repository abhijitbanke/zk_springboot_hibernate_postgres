package ar.gfritz.com.org.sk.webui;

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

import ar.gfritz.com.org.sk.HibernateSearchObject;
import ar.gfritz.com.org.sk.PagedListWrapper;
import ar.gfritz.com.org.sk.bean.Patient;
import ar.gfritz.com.org.sk.bean.Treatment;
import ar.gfritz.com.org.sk.webui.dao.PatientRepo;
import ar.gfritz.com.org.sk.webui.dao.TreatmentRepo;

import com.googlecode.genericdao.search.Filter;

@Component("treatmentCtrl")
@Scope("prototype")
public class TreatmentCtrl extends GenericForwardComposer<Window> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Textbox textbox_SearchPatientName, textbox_SearchEmployeTockenNumber;
	Button button_Search;
	Listbox listbox_PatientSearch;
	Bandbox bandbox_Patient;
	
	Textbox  textbox_TockenNumber, textbox_TreatmentDescription ;
	
	Datebox datebox_TratementDate;
	@Autowired
	TreatmentRepo treatmentRepo;
	@Autowired
	PatientRepo patientRepo;
//	TreatmentDao treatmentDao = new TreatmentDao();
	
	Paging paging_PatientSearch;
	
	@Autowired
	PagedListWrapper<Patient> pagedListWrapper;
	
	public void onCreate$TreatmentWindow(Event event) {
		System.out.println("OnCreate window");
		
		initPatientListbox();
	}
	
	
	private void initPatientListbox() {
		paging_PatientSearch.setPageSize(5);
		listbox_PatientSearch.setItemRenderer(new ListitemRenderer<Patient>() {

			@Override
			public void render(Listitem listitem, Patient patient, int index) throws Exception {
				Listcell listcell_PatientName = new Listcell(patient.getPatientName());
				Listcell listcell_PatientRelation = new Listcell(patient.getRelation());
				Listcell listcell_EmployeeContact = new Listcell(patient.getEmployee().getEmployeeTockenNumber());
				
				listitem.appendChild(listcell_PatientName);
				listitem.appendChild(listcell_PatientRelation);
				listitem.appendChild(listcell_EmployeeContact);
				listitem.setValue(patient);
				ComponentsCtrl.applyForward(listitem, "onDoubleClick=onDoubleClickPatient");
//				listitem.addForward(Events.ON_DOUBLE_CLICK, listitem.getListbox(), null);
			}
		});
		
	}

	
	public void onDoubleClickPatient(ForwardEvent event) {
		System.out.println("onDoubleClickPatient");
		Listitem listitem = (Listitem) event.getOrigin().getTarget();
		Patient pat= listitem.getValue();
		
		bandbox_Patient.setValue(pat.getPatientName());
		bandbox_Patient.setAttribute("OBJECT", pat);
		textbox_TockenNumber.setValue(pat.getEmployee().getEmployeeTockenNumber());
		bandbox_Patient.close();
		
	}

	public void onClick$button_Search(Event event) {
		System.out.println("onClick$button_Search");
		HibernateSearchObject<Patient> hso = new HibernateSearchObject<Patient>(Patient.class);
		hso.setDistinct(true);
		hso.addFetch("employee");
		if(!StringUtils.isEmpty(textbox_SearchPatientName.getValue())){
			hso.addFilter(Filter.ilike("patientName", "%"+textbox_SearchPatientName.getValue()+"%"));
		}
		if(!StringUtils.isEmpty(textbox_SearchEmployeTockenNumber.getValue())){
			hso.addFilter(Filter.ilike("employee.employeeTockenNumber", "%"+textbox_SearchEmployeTockenNumber.getValue()+"%"));
		}
		pagedListWrapper.init(hso, listbox_PatientSearch, paging_PatientSearch);
		
		/*Patient patient = new Patient();
		patient.setPatientName(textbox_SearchPatientName.getValue());
		Employee exmapleEmployee = new Employee();
		exmapleEmployee.setEmployeeTockenNumber(textbox_SearchEmployeTockenNumber.getValue());
		patient.setEmployee(exmapleEmployee);*/
		/*
		 * Employee exampleEmployee = new Employee();
		 * exampleEmployee.setEmployeeName(textbox_SearchEmployeeName.getValue());
		 * exampleEmployee.setEmployeeContact(textbox_SearchEmployeTockenNumber.getValue());
		 */
		
		/*List<Patient> list = patientRepo.findAll(new PatientSpecifications(patient)) ; ///patientDao.getPatientsByExample(patient, exmapleEmployee);
		listbox_PatientSearch.setModel(new ListModelList<Patient>(list));*/
		
	}
	
	public void onClick$button_SaveTreatment(Event event) throws Exception {
		System.out.println("onClick$button_SaveTreatment");
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
		Patient patient = ((Patient)bandbox_Patient.getAttribute("OBJECT"));
		Treatment treatment = new Treatment(patient.getPatientId(), textbox_TockenNumber.getValue(), textbox_TreatmentDescription.getValue(), datebox_TratementDate.getValue());
		//		Patient pat = new Patient( employee.getEmployeeId(), textbox_PatientName.getValue(), combobox_Relation.getValue(), datebox_TratementDate.getValue());
		
		Long maxId = treatmentRepo.findMaxTreatmentId();
		treatment.setTreatmentId((maxId != null ? maxId : 0)+1);
		treatmentRepo.save(treatment);
		Messagebox.show("Treatment record have been saved!!");
		clearForm();
		/*int val = treatmentDao.doSave(treatment);
		if(val > -1) {
			Messagebox.show("Treatment record have been saved!!");
			clearForm();
		}*/
		
	}

	private boolean isValidate() throws Exception {
		
		if(textbox_TockenNumber.getValue() == null || textbox_TockenNumber.getValue().trim().isEmpty()) {
			Messagebox.show("Please enter Tocken Number");
			return false;
		}
		if(bandbox_Patient.getText() == null || bandbox_Patient.getText().trim().isEmpty()) {
			Messagebox.show("Please select Patient");
			return false;
		}
		
		if(datebox_TratementDate.getValue() == null) {
			Messagebox.show("Please select visit dateh");
			return false;
		}
		return true;
	}
	
	private void clearForm() {
		
		textbox_TreatmentDescription.setValue(null);
		textbox_TockenNumber.setValue(null);
		bandbox_Patient.setValue(null);
		datebox_TratementDate.setValue(null);
	} 
	
	
	public void onOpen$bandbox_Patient(Event event) {
		System.out.println("onOpen$bandbox_Patient");
//		listbox_PatientSearch.getI
		textbox_SearchPatientName.setValue(null);
		textbox_SearchEmployeTockenNumber.setValue(null);
		listbox_PatientSearch.setModel(new ListModelList<Patient>());
	}
}
