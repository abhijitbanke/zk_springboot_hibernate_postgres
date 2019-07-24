package ar.gfritz.com.org.sk.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
public class Treatment {
	long treatmentId;
	long patientId;
	Patient patient; 
	String tockenNo;
	String discription;
	Date treatmentDate;
	Date treatmentDateFrom;
	Date treatmentDateTo;
	
	public Treatment() {
		super();
	}
	
	public Treatment( long patientId, String tockenNo, String discription, Date treatmentDate) {
		super();
		this.patientId = patientId;
		this.tockenNo = tockenNo;
		this.discription = discription;
		this.treatmentDate = treatmentDate;
	}
	@Id
	@Column(name="TREATMENTID")
	public long getTreatmentId() {
		return treatmentId;
	}
	public void setTreatmentId(long treatmentId) {
		this.treatmentId = treatmentId;
	}
	
	@Column(name="patientId")
	public long getPatientId() {
		return patientId;
	}
	public void setPatientId(long patientId) {
		this.patientId = patientId;
	}
	
	@Column(name="TOCKENNO")
	public String getTockenNo() {
		return tockenNo;
	}
	public void setTockenNo(String tockenNo) {
		this.tockenNo = tockenNo;
	}
	
	@Column(name="DESCRIPTION")
	public String getDiscription() {
		return discription;
	}
	public void setDiscription(String discription) {
		this.discription = discription;
	}
	
	@Column(name="TREATMENTDATE")
	public Date getTreatmentDate() {
		return treatmentDate;
	}
	public void setTreatmentDate(Date treatmentDate) {
		this.treatmentDate = treatmentDate;
	}
	
	
	  @ManyToOne
	  @JoinColumn(name="patientId", insertable=false, updatable=false) 
	  public Patient getPatient() { return patient; }
	  public void setPatient(Patient patient) { this.patient = patient; }
	 
	
	
	
	@Transient
	public Date getTreatmentDateFrom() {
		return treatmentDateFrom;
	}
	public void setTreatmentDateFrom(Date treatmentDateFrom) {
		this.treatmentDateFrom = treatmentDateFrom;
	}
	@Transient
	public Date getTreatmentDateTo() {
		return treatmentDateTo;
	}
	public void setTreatmentDateTo(Date treatmentDateTo) {
		this.treatmentDateTo = treatmentDateTo;
	}
	@Override
	public String toString() {
		return "Treatment [treatmentId=" + treatmentId + ", patientId=" + patientId /* + ", patient=" + patient */
				+ ", tockenNo=" + tockenNo + ", discription=" + discription + ", treatmentDate=" + treatmentDate + "]";
	}
}
