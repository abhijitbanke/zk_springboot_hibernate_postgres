package ar.gfritz.com.org.sk.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Employee {
	long employeeId;
	String employeeName;
	String employeeAddress;
	String employeeTockenNumber;
	public Employee() {
		super();
	}

	public Employee( String employeeName, String employeeAddress, String employeeTockenNumber) {
		super();
		this.employeeName = employeeName;
		this.employeeAddress = employeeAddress;
		this.employeeTockenNumber = employeeTockenNumber;
	}
	
	@Id
	@Column(name="employeeId")
	public long getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(long employeeId) {
		this.employeeId = employeeId;
	}
	
	@Column(name="employeeName")
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	
	@Column(name="employeeAddress")
	public String getEmployeeAddress() {
		return employeeAddress;
	}
	public void setEmployeeAddress(String employeeAddress) {
		this.employeeAddress = employeeAddress;
	}
	
	@Column(name="employeeTockenNumber")
	public String getEmployeeTockenNumber() {
		return employeeTockenNumber;
	}
	public void setEmployeeTockenNumber(String employeeTockenNumber) {
		this.employeeTockenNumber = employeeTockenNumber;
	}

	@Override
	public String toString() {
		return "Employee [employeeId=" + employeeId + ", employeeName=" + employeeName + ", employeeAddress="
				+ employeeAddress + ", employeeTockenNumber=" + employeeTockenNumber + "]";
	}
}
