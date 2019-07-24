package ar.gfritz.com.org.sk.webui.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.zkoss.zsoup.helper.StringUtil;

import ar.gfritz.com.org.sk.bean.Employee;
import ar.gfritz.com.org.sk.bean.Patient;

public class PatientSpecifications implements Specification<Patient>{

	private Patient example;
	
	public PatientSpecifications(Patient example) {
		super();
		this.example = example;
	}

	@Override
	public Predicate toPredicate(Root<Patient> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		query.distinct(true);
		List<Predicate> predicates = new ArrayList<Predicate>();
		if(!StringUtil.isBlank(example.getPatientName())){
			predicates.add(cb.like(cb.lower(root.get("patientName")), example.getPatientName().toLowerCase()+"%"));
		}
		if(example.getEmployeeId()>0){
			predicates.add(cb.equal(root.get("employeeId"), example.getEmployeeId()));
		}
		if(!StringUtil.isBlank(example.getRelation())){
			predicates.add(cb.equal(cb.lower(root.get("relation")), example.getRelation().toLowerCase()));
		}
		if(example.getEmployee() != null){
			 Join<Patient,Employee> pfJoin = query.from(Patient.class).join("employee");
			 if(!StringUtil.isBlank(example.getEmployee().getEmployeeTockenNumber())){
				 predicates.add(cb.equal(pfJoin.get("employeeTockenNumber"),example.getEmployee().getEmployeeTockenNumber()));
			 }
			 if(!StringUtil.isBlank(example.getEmployee().getEmployeeName())){
				 predicates.add(cb.equal(pfJoin.get("employeeName"),example.getEmployee().getEmployeeName()));
			 }
	         
		}
		return andTogether(predicates,cb);
	}

	private Predicate andTogether(List<Predicate> predicates, CriteriaBuilder cb) {
		return cb.and(predicates.toArray(new Predicate[0]));
	}

}
