package com.techgen.client;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.techgen.entity.Department;
import com.techgen.entity.Employee;
import com.techgen.util.HibernateUtil;

public class OneToManyClient {

	public static void main(String[] args) {
		SessionFactory sessionFactory = null;
		Session session = null;
		try {
			sessionFactory = HibernateUtil.getSessionFactory();

			session = sessionFactory.openSession();

			Transaction transaction = session.getTransaction();

			// persistDepartmentAndEmployee(session, transaction);
			// getDepartment(session, 1l);
			// getEmployee(session, 1l);
			// persistEmployee(session, transaction);
			// persistDepartment(session, transaction);
			// updateEmployeeDepartment(session, transaction);
			// updateDepartmentForEmployee(session, transaction);
			// deleteEmployee(session, transaction);
			// deleteDepartment(session, transaction);

		} catch (HibernateException e) {
			System.out.println(e.getMessage());
		} finally {
			if (session != null) {
				session.close();
			}
			if (sessionFactory != null) {
				sessionFactory.close();
			}
		}
	}

	private static Department getDepartment(Session session, Long id) {
		Department department = session.get(Department.class, id);
		return department;
	}

	private static Employee getEmployee(Session session, Long id) {
		Employee employee = session.get(Employee.class, id);
		return employee;
	}

	private static void persistDepartmentAndEmployee(Session session, Transaction transaction) {
		transaction.begin();

		Department department1 = new Department("CS", "CS-220", true);
		Department department2 = new Department("IT", "IT-875", false);
		Department department3 = new Department("EC", "EC-320", true);
		Department department4 = new Department("EL", "EL-175", true);

		Employee employee1 = new Employee("John", "Indore", 4500, department1);
		Employee employee2 = new Employee("Tom", "Bhopal", 4500, department1);
		Employee employee3 = new Employee("Amit", "Ujjain", 4500, department2);
		Employee employee4 = new Employee("Sumit", "Bhopal", 4500, department3);
		Employee employee5 = new Employee("Dilip", "Ujjain", 4500, department3);
		Employee employee6 = new Employee("Tauhid", "Khandwa", 4500, department4);

		List<Department> departments = new ArrayList<>();
		departments.add(department1.addDepartmentToEmployee(employee1));
		departments.add(department2.addDepartmentToEmployee(employee3));
		departments.add(department1.addDepartmentToEmployee(employee2));
		departments.add(department3.addDepartmentToEmployee(employee4));
		departments.add(department3.addDepartmentToEmployee(employee5));
		departments.add(department4.addDepartmentToEmployee(employee6));

		for (Department department : departments) {
			session.persist(department);
		}

		transaction.commit();
	}

	private static void persistEmployee(Session session, Transaction transaction) {
		transaction.begin();

		Employee employee1 = new Employee("Vikas", "Dewas", 4500, null);
		Employee employee2 = new Employee("Salim", "Neemuch", 4500, null);
		Employee employee3 = new Employee("Rohit", "Khandwa", 4500, null);

		List<Employee> employees = new ArrayList<>();
		employees.add(employee1);
		employees.add(employee2);
		employees.add(employee3);

		for (Employee employee : employees) {
			session.persist(employee);
		}

		transaction.commit();
	}

	private static void persistDepartment(Session session, Transaction transaction) {
		transaction.begin();

		Department department1 = new Department("EC", "EC-320", true);
		Department department2 = new Department("EL", "EL-877", true);

		List<Department> departments = new ArrayList<>();
		departments.add(department1);
		departments.add(department2);

		for (Department department : departments) {
			session.persist(department);
		}

		transaction.commit();
	}

	private static void updateEmployeeDepartment(Session session, Transaction transaction) {
		transaction.begin();

		Employee employee1 = getEmployee(session, 9l);

		Department department = getDepartment(session, 7l);

		List<Employee> employees = new ArrayList<>();
		employees.add(employee1);

		for (Employee employee : employees) {
			employee.setSalary(employee.getSalary() + 100);
			employee.setDepartment(department);
		}
		transaction.commit();
	}

	private static void updateDepartmentForEmployee(Session session, Transaction transaction) {
		transaction.begin();

		Employee employee1 = getEmployee(session, 3l);

		Department department1 = getDepartment(session, 1l);

		List<Department> departments = new ArrayList<>();
		departments.add(department1);

		for (Department department : departments) {
			department.setIsActive(false);
			department.addDepartmentToEmployee(employee1);
		}
		transaction.commit();
	}

	private static void deleteEmployee(Session session, Transaction transaction) {
		transaction.begin();

		Employee employee = getEmployee(session, 5l);
		// employee.setDepartment(null); // if cascade is removal in manyToOne side

		session.remove(employee);

		transaction.commit();
	}

	private static void deleteDepartment(Session session, Transaction transaction) {
		transaction.begin();

		Department department = getDepartment(session, 3l);

		session.remove(department);

		transaction.commit();
	}

}
