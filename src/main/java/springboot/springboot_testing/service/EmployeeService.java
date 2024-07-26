package springboot.springboot_testing.service;

import springboot.springboot_testing.entity.Employee;

import java.util.List;

public interface EmployeeService {
    Employee saveEmployee(Employee employee);
    List<Employee> findAllEmployee();
    Employee updateEmployee(Employee employee);
    void deleteEmployee(Long id);
    Employee findByEmployeeId(Long id);
}
