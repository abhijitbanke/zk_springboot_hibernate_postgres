package ar.gfritz.com.org.sk.webui.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ar.gfritz.com.org.sk.bean.Employee;


public interface EmployeeRepo extends CrudRepository<Employee, Long>, JpaSpecificationExecutor<Employee> {
	@Query("select max(employeeId) from Employee")
	public Long findMaxEmployeeId();
	
	@Query("select count(e) > 0 from Employee e where e.employeeTockenNumber=:empTockenNumber")
	boolean existByEmployeeTocken(@Param("empTockenNumber") String employeeTockenNumber);
	
	@Query("from Employee where employeeTockenNumber=:employeeTockenNumber")
	public Employee findByEmployeeTockenNumber(@Param("employeeTockenNumber") String employeeTockenNumber);
	
}
