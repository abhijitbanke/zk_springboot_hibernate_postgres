package ar.gfritz.com.org.sk.webui;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
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
import ar.gfritz.com.org.sk.webui.dao.EmployeeRepo;
import ar.gfritz.com.org.sk.webui.dao.PatientRepo;
import ar.gfritz.com.org.sk.webui.dao.TreatmentRepo;

import com.googlecode.genericdao.search.Filter;


@Controller("treatmentRecordsCtrl")
@Scope("prototype")
public class TreatmentRecordsCtrl extends GenericForwardComposer<Window> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Textbox textbox_SearchPatientName, textbox_SearchEmployeTockenNumber;
	Button button_Search;
//	Button button_Print;
	Listbox listbox_PatientSearch;
	Bandbox bandbox_Patient;
	
	Textbox  textbox_TockenNumber ;
	
	Datebox datebox_TratementDateFrom, datebox_TratementDateTo;
	
	Listbox listbox_TreatmentRecords;
	Paging paging_TreatmentRecords; 
	@Autowired
	TreatmentRepo treatmentRepo;
	@Autowired
	PatientRepo patientRepo;
	@Autowired
	EmployeeRepo employeeRepo;
	@Autowired
	PagedListWrapper<Treatment> pagedListWrapper;
	
	@Autowired
	PagedListWrapper<Patient> pagedListWrapperForPatient;
	
	Paging paging_PatientSearch;
	
	public void onCreate$TreatmentWindow(Event event) {
		System.out.println("OnCreate window");
		
		initPatientListbox();
		initTreatementRecordsListbox();
	}
	
	
	private void initTreatementRecordsListbox() {
		paging_TreatmentRecords.setPageSize(5);
		listbox_TreatmentRecords.setItemRenderer(new ListitemRenderer<Treatment>() {
			final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			@Override
			public void render(Listitem listitem, Treatment treatment, int index) throws Exception {
				listitem.appendChild(new Listcell(sdf.format(treatment.getTreatmentDate())));
				listitem.appendChild(new Listcell(treatment.getTockenNo()));
				listitem.appendChild(new Listcell(treatment.getPatient().getEmployee().getEmployeeName()));
				listitem.appendChild(new Listcell(treatment.getPatient().getPatientName()));
				listitem.appendChild(new Listcell(treatment.getPatient().getRelation()));
				listitem.appendChild(getTratementComponent(treatment));
			}
			private Component getTratementComponent(Treatment treatment) {
				Listcell listcell = new Listcell();
				Textbox textbox = new Textbox();
				textbox.setWidth("99%");
				textbox.setValue(treatment.getDiscription());
				textbox.setReadonly(true);
				textbox.setRows(3);
				listcell.appendChild(textbox);
				return listcell;
			}
		});
		
	}


	private void initPatientListbox() {
		listbox_PatientSearch.setItemRenderer(new ListitemRenderer<Patient>() {

			@Override
			public void render(Listitem listitem, Patient patient, int index) throws Exception {
				Listcell listcell_PatientName = new Listcell(patient.getPatientName());
				Listcell listcell_PatientRelation = new Listcell(patient.getRelation());
				Listcell listcell_EmployeeTockenNumber = new Listcell(patient.getEmployee().getEmployeeTockenNumber());
				
				listitem.appendChild(listcell_PatientName);
				listitem.appendChild(listcell_PatientRelation);
				listitem.appendChild(listcell_EmployeeTockenNumber);
				listitem.setValue(patient);
				ComponentsCtrl.applyForward(listitem, "onDoubleClick=onDoubleClickPatient");
			}
		});
		
	}

	
	public void onDoubleClickPatient(ForwardEvent event) {
		System.out.println("onDoubleClickPatient");
		Listitem listitem = (Listitem) event.getOrigin().getTarget();
		Patient pat= listitem.getValue();
		
		bandbox_Patient.setValue(pat.getPatientName());
		bandbox_Patient.setAttribute("OBJECT", pat);
		
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
		pagedListWrapperForPatient.init(hso, listbox_PatientSearch, paging_PatientSearch);
		
	}
	
	
	public void onClick$button_Print(Event event) throws Exception {
		System.out.println("onClick$button_Print");
		doPrint();
	}
	
	
	private void doPrint() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		HibernateSearchObject<Treatment> hos = getHibernateSearchObject();
		List<Treatment> list = pagedListWrapper.getPagedListService().getBySearchObject(hos);
		
		String reportPath = "/WEB-INF/report/treatmentReport.jasper";
		Map<String, Object> repParams = new HashMap<String, Object>();
		repParams.put("visitDateFrom",datebox_TratementDateFrom.getValue() != null ? sdf.format( datebox_TratementDateFrom.getValue()) : "");
		repParams.put("visitDateTo",datebox_TratementDateFrom.getValue() != null ? sdf.format( datebox_TratementDateFrom.getValue()) : "");
		repParams.put("HIDE_EXPORT_EXCEL_BUTTON", false);
		repParams.put("PARAMETERS_MAP", repParams);
		repParams.put("BEANLIST", list);
		repParams.put("REPORT_PATH", reportPath);
		repParams.put("TITLE", "Treatment Report");
		repParams.put("WINDOW_WIDTH", "100%");
		repParams.put("WINDOW_HEIGHT", "100%");
		repParams.put("printedBy", "Dr. S. K. ");
		Component comp = null;
		try {
			comp = Executions.createComponents("/WEB-INF/pages/reportZul.zul", null, repParams);
			if (comp instanceof Window) {
				((Window) comp).doModal();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public void onClick$button_SearchRecords(Event event) throws Exception {
		System.out.println("onClick$button_SearchRecords");
		doSearch();
	}
	
	/*
	 * public void onClickButton_SaveEmployee(ForwardEvent event) throws Exception {
	 * System.out.println("onClick$button_SaveEmployee"); doSave(); }
	 */
	
	public void doSearch() throws Exception {
		/*
		 * if(!isValidate()) { return; }
		 */
		
		HibernateSearchObject<Treatment> hos = getHibernateSearchObject();
		pagedListWrapper.init(hos, listbox_TreatmentRecords, paging_TreatmentRecords);
	}
	
	private HibernateSearchObject<Treatment> getHibernateSearchObject(){
		HibernateSearchObject<Treatment> hos = new HibernateSearchObject<Treatment>(Treatment.class);
		hos.addFetch("patient.employee");
		
		Patient patient;
		if(bandbox_Patient.getValue() != null && !bandbox_Patient.getValue().trim().isEmpty()) {
			patient = ((Patient)bandbox_Patient.getAttribute("OBJECT"));
			hos.addFilter(Filter.equal("patient.patientId", patient.getPatientId()));
		}
		if(textbox_TockenNumber.getValue() != null && !textbox_TockenNumber.getValue().trim().isEmpty() ) {
			hos.addFilter(Filter.like("tockenNo", "%"+textbox_TockenNumber.getValue().trim()+"%"));
		}
		
		if(datebox_TratementDateFrom.getValue() != null) {
			hos.addFilter(Filter.greaterOrEqual("treatmentDate",datebox_TratementDateFrom.getValue()));
		}
		if(datebox_TratementDateTo.getValue() != null) {
			hos.addFilter(Filter.lessOrEqual("treatmentDate",datebox_TratementDateTo.getValue()));
		}
		hos.setDistinct(true);
		return hos;
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
		
		if(datebox_TratementDateFrom.getValue() == null) {
			Messagebox.show("Please select visit dateh");
			return false;
		}
		return true;
	}
	
	private void clearForm() {
		
		textbox_TockenNumber.setValue(null);
		bandbox_Patient.setValue(null);
		datebox_TratementDateFrom.setValue(null);
	} 
	
	
	public void onOpen$bandbox_Patient(Event event) {
		System.out.println("onOpen$bandbox_Patient");
//		listbox_PatientSearch.getI
		textbox_SearchPatientName.setValue(null);
		textbox_SearchEmployeTockenNumber.setValue(null);
		listbox_PatientSearch.setModel(new ListModelList<Patient>());
	}
}
