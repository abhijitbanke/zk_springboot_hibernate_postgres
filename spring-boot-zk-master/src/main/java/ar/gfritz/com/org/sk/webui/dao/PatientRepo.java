package ar.gfritz.com.org.sk.webui.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ar.gfritz.com.org.sk.bean.Patient;

@Repository("patientRepo")
public interface PatientRepo extends CrudRepository<Patient, Long>,  JpaRepository<Patient, Long> , JpaSpecificationExecutor<Patient>{
	@Query("select max(patientId) from Patient")
	Long findMaxPatientId();

	@Query("select count(p) > 0 from Patient p join p.employee e  where e.employeeTockenNumber=:employeeTockenNumber and p.patientName =:patientName")
	boolean existsByNameAndTocken(@Param("patientName")String patientName,@Param("employeeTockenNumber")String employeeTockenNumber);
	
	@Query("select count(p) > 0 from Patient p join p.employee e  where e.employeeId=:employeeId and p.patientName =:patientName")
	boolean existsByNameAndEmployeeId(@Param("patientName")String patientName, @Param("employeeId")Long employeeId);
}
