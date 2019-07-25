package ar.gfritz.com.org.sk.webui;

import java.io.File;
import java.io.Serializable;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.fill.JRFileVirtualizer;

import org.hibernate.validator.internal.util.privilegedactions.GetAnnotationParameter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.zkoss.zk.ui.event.CreateEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkex.zul.Jasperreport;
import org.zkoss.zul.Button;
import org.zkoss.zul.Window;


@Component("reportCtrl")
@Scope("prototype")
public class ReportCtrl extends GenericForwardComposer<Window> implements Serializable{
	
	private static final long serialVersionUID = -5938302965892767436L;
	protected Jasperreport report;
	protected Window windowReport;
	String reportPath;
	List<Object> beanList;
	List<Object> beanListExcel;
	Map parametesMap;
	Connection conn = null;
	Button button_excel;
	private String localPath = "C:/Temp";
	private String serverPath = "/opt/phisesb/rpttempdir/";
	 
	public void onCreate$windowReport(Event event){
		parametesMap = ((CreateEvent)((ForwardEvent)event).getOrigin()).getArg();
		init();
		showReport("pdf");
		System.err.println("====================== Report => " + reportPath);
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void showReport(String type) {
		if(parametesMap.get("HIDE_EXPORT_EXCEL_BUTTON") != null && (Boolean) parametesMap.get("HIDE_EXPORT_EXCEL_BUTTON") == true)
			button_excel.setVisible(false);
		if(!type.equals("pdf")&& null != reportPath ){
			   reportPath = reportPath.replace(".jasper", "Excel.jasper");
		}
		File file = null;
		String savingPath = null;		
		String osName = System.getProperty("os.name");
		if (osName.contains("Win")) {
			file = new File(localPath);
			if (!file.exists()) 
				file.mkdirs();
			savingPath = localPath;
		} else {
			file = new File(serverPath);
			if (!file.exists()) 
				file.mkdirs();
			savingPath = serverPath;
		}
		JRFileVirtualizer virtualizer = new JRFileVirtualizer(2, savingPath);
		parametesMap.put(JRParameter.REPORT_VIRTUALIZER, virtualizer);
	    report.setSrc(reportPath);
	    report.setType(type);
	    report.setParameters(parametesMap);
	   
	    if(beanList != null && beanList.size() > 0){
	    	if(parametesMap.containsKey("KEWPS10")) {
		    	parametesMap.put("KEWPS", new JRBeanCollectionDataSource(beanList));
		    	parametesMap.put("KEWPSSTOR", new JRBeanCollectionDataSource(beanList));
		    }
	    	
			if (reportPath != null) {
				if(!type.equals("pdf")&& beanListExcel != null && beanListExcel.size() > 0){
					report.setDatasource(new JRBeanCollectionDataSource(
							beanListExcel));
				}else{
					report.setDatasource(new JRBeanCollectionDataSource(
							beanList));
				}
	    	}	    	
	    }	  
	  
	}

	
	
	public void onClick$button_excel() {
		init();
		parametesMap.put("IS_IGNORE_PAGINATION", true);
		// added following 2 lines to create multiple sheet in excel
		parametesMap.put("is_break_after_row", true);
		parametesMap.put("CREATESHEET", true);
		if(parametesMap.get("EXCEL_REPORT_PATH") != null)
			reportPath = (String) parametesMap.get("EXCEL_REPORT_PATH");
		showReport("xls");
	}
	
	private void init() {
		if(parametesMap.get("REPORT_PATH")!=null){
			reportPath=(String) parametesMap.get("REPORT_PATH");
		}
		if(parametesMap.get("BEANLIST")!=null){
			beanList=(List<Object> ) parametesMap.get("BEANLIST");
		}
		if(parametesMap.get("BEANLISTEXCEL")!=null){
			beanListExcel=(List<Object> ) parametesMap.get("BEANLISTEXCEL");
		}
		if(parametesMap.get("DATASET")!=null){
			if (parametesMap.get("DATASET") instanceof Class) {
				
			}beanList=(List<Object> ) parametesMap.get("DATASET");			
		}
		if(parametesMap.get("TITLE")!=null){
			String title=(String) parametesMap.get("TITLE");
			windowReport.setTitle(title);
		}
		if(parametesMap.get("WINDOW_WIDTH")!=null){
			String width=(String) parametesMap.get("WINDOW_WIDTH");
			windowReport.setWidth(width);
		}
		if(parametesMap.get("WINDOW_HEIGHT")!=null){
			String height=(String) parametesMap.get("WINDOW_HEIGHT");
			windowReport.setHeight(height);
		}
	}
	@Override
	protected void finalize() throws Throwable {
		super.finalize();if(conn != null){
			try{
				conn.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
	}
}
