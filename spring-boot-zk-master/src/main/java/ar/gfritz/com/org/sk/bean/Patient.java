package ar.gfritz.com.org.sk.bean;



import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;



@Entity
public class Patient {
	long patientId;
	long employeeId;
	Employee employee;
	String patientName;
	String relation;
	Date dob;

	public Patient() {
		
	}
	
	public Patient( long empoyeeId, String patientName, String relation, Date dob) {
		super();
		this.employeeId = empoyeeId;
		this.patientName = patientName;
		this.relation = relation;
		this.dob = dob;
	}
	
	
	
	
	public Patient(String patientName, String relation) {
		super();
		this.patientName = patientName;
		this.relation = relation;
		
	}

	@Id
	@Column(name="patientId")
	public long getPatientId() {
		return patientId;
	}
	public void setPatientId(long patientId) {
		this.patientId = patientId;
	}
	@Column(name="employeeId")
	public long getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(long empoyeeId) {
		this.employeeId = empoyeeId;
	}
	@Column(name="patientname")
	public String getPatientName() {
		return patientName;
	}
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
	@Column(name="relation")
	public String getRelation() {
		return relation;
	}
	public void setRelation(String relation) {
		this.relation = relation;
	}
	@Column(name="dob")
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	
	@ManyToOne
	@JoinColumn(name="employeeId", insertable=false , updatable=false)
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	

	@Override
	public String toString() {
		return "Patient [patientId=" + patientId + ", empoyeeId=" + employeeId + ", employee=" + employee
				+ ", patientName=" + patientName + ", relation=" + relation + ", dob=" + dob + "]";
	}
	
}
