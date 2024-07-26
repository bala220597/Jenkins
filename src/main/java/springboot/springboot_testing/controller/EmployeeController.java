package springboot.springboot_testing.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springboot.springboot_testing.entity.Employee;
import springboot.springboot_testing.service.EmployeeService;

import java.util.List;

@RestController()
@RequestMapping("/api")
@AllArgsConstructor
public class EmployeeController {

   // @Autowired
    private EmployeeService employeeService;

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public Employee createEmployee(@RequestBody Employee employee){
        return  employeeService.saveEmployee(employee);
    }

    @GetMapping("/all")
    public List<Employee> getAllEmployee(){
        return employeeService.findAllEmployee();
    }
    @GetMapping("{id}")
    public Employee findByEmployee(@PathVariable("id") Long employeeId){
       return employeeService.findByEmployeeId(employeeId);
    }
}
