package ar.gfritz.com.org.sk.webui;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Include;
import org.zkoss.zul.Menu;
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.Menupopup;
import org.zkoss.zul.Window;


@Component("indexCtrl")
@Scope("prototype")
public class IndexCtrl extends GenericForwardComposer<Window> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Menu menu_Masters;
	Menupopup menupopup;
	Menuitem menuItem_Employee, menuItem_Patient, menuItem_Treatment, menuItem_EmployeeReport, menuItem_PatientReport, menuItem_TreatmentReport;
	Include include;
	public void onCreate$window(Event event) {
		System.out.println("OnCreate window");
	}
	
	public void onSelectMenuitem(ForwardEvent event) {
		System.out.println("Event called ::: "+event.getOrigin().getTarget().getId());
		if(event.getOrigin().getTarget() == menuItem_Employee) {
			include.setSrc("/WEB-INF/pages/master/emplyee.zul"); 
		}else if(event.getOrigin().getTarget() == menuItem_Patient) {
			include.setSrc("/WEB-INF/pages/master/Patient.zul");
		}else if(event.getOrigin().getTarget() == menuItem_Treatment) {
			include.setSrc("/WEB-INF/pages/master/Treatment.zul");
		}else if(event.getOrigin().getTarget() == menuItem_EmployeeReport) {
			include.setSrc("/WEB-INF/pages/report/emplyeeReport.zul"); 
		}else if(event.getOrigin().getTarget() == menuItem_PatientReport) {
			include.setSrc("/WEB-INF/pages/report/PatientReport.zul");
		}else if(event.getOrigin().getTarget() == menuItem_TreatmentReport) {
			include.setSrc("/WEB-INF/pages/report/TreatmentReport.zul");
		}
	}
	
}
