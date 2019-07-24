package ar.gfritz.com.org.sk.webui.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.zkoss.zsoup.helper.StringUtil;

import ar.gfritz.com.org.sk.bean.Employee;

public class EmployeeSpecifications implements Specification<Employee>{

	private Employee example;
	
	public EmployeeSpecifications(Employee example) {
		super();
		this.example = example;
	}

	@Override
	public Predicate toPredicate(Root<Employee> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		List<Predicate> predicates = new ArrayList<Predicate>();
		if(!StringUtil.isBlank(example.getEmployeeName())){
			predicates.add(cb.like(cb.lower(root.get("employeeName")), example.getEmployeeName().toLowerCase()+"%"));
		}
		if(!StringUtil.isBlank(example.getEmployeeTockenNumber())){
			predicates.add(cb.like(cb.lower(root.get("employeeTockenNumber")), example.getEmployeeTockenNumber().toLowerCase()+"%"));
		}
		return andTogether(predicates,cb);
	}

	private Predicate andTogether(List<Predicate> predicates, CriteriaBuilder cb) {
		return cb.and(predicates.toArray(new Predicate[0]));
	}

}
