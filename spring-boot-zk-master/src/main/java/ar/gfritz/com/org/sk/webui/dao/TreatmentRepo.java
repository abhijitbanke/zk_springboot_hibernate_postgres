package ar.gfritz.com.org.sk.webui.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ar.gfritz.com.org.sk.bean.Employee;
import ar.gfritz.com.org.sk.bean.Treatment;

@Repository("treatmentRepo")
public interface TreatmentRepo extends CrudRepository<Treatment, Long> {
	@Query("select max(treatmentId) from Treatment")
	Long findMaxTreatmentId();

	/*@Query("select e from Treatment t inner join Patient p on p.patientId = t.patientId inner join Employee e on e.employeeId = p.employeeId where t.patientId =:patientId ")
	Employee getEmployeeByPatientId(@Param("patientId") Long patientId);*/
	
	
}
