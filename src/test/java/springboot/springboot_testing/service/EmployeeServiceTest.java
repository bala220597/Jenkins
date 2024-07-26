package springboot.springboot_testing.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import springboot.springboot_testing.entity.Employee;
import springboot.springboot_testing.exceptions.UserAlreadyFoundException;
import springboot.springboot_testing.repository.EmployeeRepository;
import springboot.springboot_testing.service.Impl.EmployeeServiceImpl;

import java.lang.module.ResolutionException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;
    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Test
    @DisplayName("Junit service layer for save Employee Obj")
    public void  givenEmployeeObj_whenSaveEmployee_thenReturnEmployeeObj(){
        //given
        Employee employee=Employee.builder().id(1L).firstName("bala")
                .lastName("k").email("bala@gmail.com")
                .build();
        //given-stud methods
        BDDMockito.given(employeeRepository.findByEmail("bala@gmail.com")).willReturn(Optional.empty());
        BDDMockito.given(employeeRepository.save(employee)).willReturn(employee);

        //when-call service method
        Employee savedEmployee=employeeService.saveEmployee(employee);
        //then-verify
        Assertions.assertThat(savedEmployee).isNotNull();
        Assertions.assertThat(savedEmployee.getEmail()).isEqualTo("bala@gmail.com");

    }
    @Test
    @DisplayName("Junit for service layer throw exception cases")
    public void givenEmployeeObj_whenSaveEmployee_thenThrowException(){
        //given
        Employee employee=Employee.builder().id(1L).firstName("bala")
                .lastName("k").email("bala@gmail.com")
                .build();
        //given-stub methods
        BDDMockito.given(employeeRepository.findByEmail("bala@gmail.com")).willReturn(Optional.of(employee));
        //when-call save employee
        org.junit.jupiter.api.Assertions.assertThrows(UserAlreadyFoundException.class,()->{
            employeeService.saveEmployee(employee);
        });
        //here not possible to go this stub line because of err
        // BDDMockito.given(employeeRepository.save(employee)).willReturn(employee);
        //so verify that
        Mockito.verify(employeeRepository,Mockito.never()).save(employee);
    }

    @Test
    @DisplayName("Junit for findAllEmployee")
    public void givenListOfEmployee_whenFindEmployee_thenReturnListOfEmployee(){
       //given
        Employee employee1=Employee.builder().id(1L).firstName("bala")
                .lastName("k").email("bala@gmail.com")
                .build();
        Employee employee2=Employee.builder().id(2L).firstName("pavi")
                .lastName("j").email("pavivj@gmail.com")
                .build();

        BDDMockito.given(employeeRepository.findAll()).willReturn(List.of(employee1,employee2));
        //when-call service method findAll
        List<Employee>empList=employeeService.findAllEmployee();
        //then-verify
        Assertions.assertThat(empList.size()).isGreaterThan(0);

    }

    @Test
    @DisplayName("Junit for findAll return emptu")
    public void givenListOfEmployee_whenFindAll_thenReturnNothing(){
        //given
        Employee employee1=Employee.builder().id(1L).firstName("bala")
                .lastName("k").email("bala@gmail.com")
                .build();
        Employee employee2=Employee.builder().id(2L).firstName("pavi")
                .lastName("j").email("pavivj@gmail.com")
                .build();

        BDDMockito.given(employeeRepository.findAll()).willReturn(Collections.emptyList());
        //when-call service method findAll
        List<Employee>empList=employeeService.findAllEmployee();
        //then-verify
        Assertions.assertThat(empList.size()).isEqualTo(0);
    }
    @Test
    @DisplayName("Junit for Updated Test")
    public void givenEmpObj_whenUpdate_thenReturnEmp(){
        //given
        Employee employee1=Employee.builder().id(1L).firstName("bala")
                .lastName("k").email("bala@gmail.com")
                .build();
        //given-stub
        BDDMockito.given(employeeRepository.findById(1L)).willReturn(Optional.ofNullable(employee1));
        employee1.setEmail("bala123@gmail.com");
        BDDMockito.given(employeeRepository.save(employee1)).willReturn(employee1);
        //when-call upadateEmployee
        Employee UpdatedEmp=employeeService.updateEmployee(employee1);
        //then-verify
        Assertions.assertThat(UpdatedEmp.getEmail()).isEqualTo("bala123@gmail.com");
        Assertions.assertThat(UpdatedEmp).isNotNull();

    }
    @Test
    @DisplayName("Junit for DeletebyId")
    public void givenEmployeeId_whenDelete_thenReturnNothing(){
        //given
       BDDMockito.willDoNothing().given(employeeRepository).deleteById(1L);
      //when-call deleteById method
        employeeService.deleteEmployee(1L);
        //Verify-then
        Mockito.verify(employeeRepository,Mockito.times(1)).deleteById(1L);

    }
}
