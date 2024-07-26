package springboot.springboot_testing.repository;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import springboot.springboot_testing.entity.Employee;

import java.util.List;

@DataJpaTest
public class EmployeeRepositoryTest {
    @Autowired
    private EmployeeRepository employeeRepository;


    @Test
    @DisplayName("Junit test -save employee Obj")
    public void givenEmpObj_whenSave_thenReturnEmpObj(){
        //given
        Employee emp=Employee.builder().firstName("bala")
                .lastName("k")
                .email("bala@gmail.com")
                .build();
        //when
        Employee SavedEmp=employeeRepository.save(emp);
        //then-verify output
        Assertions.assertThat(SavedEmp).isNotIn();
        Assertions.assertThat(SavedEmp.getId()).isGreaterThan(0);

    }

    @Test
    @DisplayName("Junit for list of employee")
    public void givenListOfEmpObj_whenFindAll_thenReturnListOfEmpObj(){
        //given
        Employee emp1=Employee.builder().firstName("bala").lastName("k").email("bala@gmail.com").build();
        Employee emp2=Employee.builder().firstName("pavi").lastName("j").email("pavi@gmail.com").build();
        //when
        List<Employee>EmployeeList=employeeRepository.saveAll(List.of(emp1,emp1));
        //then verify output
        Assertions.assertThat(EmployeeList).isNotEmpty();
        Assertions.assertThat(EmployeeList.size()).isGreaterThan(1);


    }

    @Test
    @DisplayName("Junit for employee find by email")
    public void givenEmployeeObj_whenSave_thenReturnEmployeeByEmail(){
        //given
        Employee emp2=Employee.builder().firstName("pavi").lastName("j").email("pavi@gmail.com").build();
        //when
        Employee saveEmp=employeeRepository.save(emp2);
        //then
        Employee fetchEmp=employeeRepository.findByEmail(saveEmp.getEmail()).get();
        Assertions.assertThat(fetchEmp.getEmail()).isEqualTo(saveEmp.getEmail());

    }

    @Test
    @DisplayName("Junit for updated employee")
    public void givenEmployeeObj_whenUpdateEmployeeeObj_thenReturnUpdateEmp(){
        //given
        Employee emp2=Employee.builder().firstName("pavi").lastName("j").email("pavi@gmail.com").build();
        Employee saveEmp=employeeRepository.save(emp2);
        //when
        saveEmp.setEmail("pavivj06@gmail.com");
        employeeRepository.save(saveEmp);
        //then
        Assertions.assertThat(saveEmp.getEmail()).isEqualTo("pavivj06@gmail.com");

    }
    @Test
    @DisplayName("Junit for delete employee")
    public void givenEmployeeObj_whenDeleteEmpObJ_thenreturnEmpty(){
        //given
        Employee emp2=Employee.builder().firstName("pavi").lastName("j").email("pavi@gmail.com").build();
        Employee saveEmp=employeeRepository.save(emp2);
        //when
       employeeRepository.delete(saveEmp);
       //then
        Assertions.assertThat(employeeRepository.findByEmail("pavi@gmail.com")).isEmpty();

    }


}
