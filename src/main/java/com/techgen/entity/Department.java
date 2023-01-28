package com.techgen.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "department")
public class Department {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "dept_name")
	private String name;

	private String code;

	@Column(name = "active")
	private Boolean isActive;

	@OneToMany(mappedBy = "department", cascade = { CascadeType.PERSIST }, orphanRemoval = true)
	private List<Employee> employees;

	public Department(String name, String code, Boolean isActive) {
		super();
		this.name = name;
		this.code = code;
		this.isActive = isActive;
	}

	public Department addDepartmentToEmployee(Employee employee) {
		if (employees == null) {
			employees = new ArrayList<>();
		}
		employees.add(employee);
	 	employee.setDepartment(this);
		return this;
	}

}
