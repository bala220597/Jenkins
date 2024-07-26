package springboot.springboot_testing.service.Impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import springboot.springboot_testing.entity.Employee;
import springboot.springboot_testing.exceptions.UserAlreadyFoundException;
import springboot.springboot_testing.repository.EmployeeRepository;
import springboot.springboot_testing.service.EmployeeService;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;
    @Override
    public Employee saveEmployee(Employee employee) {
       Optional<Employee> employeeResp= employeeRepository.findByEmail(employee.getEmail());
       if(employeeResp.isPresent()){
           throw new UserAlreadyFoundException("The given user already found");
       }
        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> findAllEmployee() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee updateEmployee(Employee employee) {
       Employee emp= employeeRepository.findById(employee.getId()).get();
       emp.setEmail(employee.getEmail());
       Employee saveEmp=  employeeRepository.save(emp);
        return saveEmp;
    }

    @Override
    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    @Override
    public Employee findByEmployeeId(Long id) {
        return employeeRepository.findById(id).get();
    }
}
