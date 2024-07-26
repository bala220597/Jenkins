package springboot.springboot_testing.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import springboot.springboot_testing.controller.EmployeeController;
import springboot.springboot_testing.entity.Employee;
import springboot.springboot_testing.service.Impl.EmployeeServiceImpl;

import java.util.List;

@WebMvcTest//Unit test for controller it isolates controller and load required bean
public class EmployeeControllerTestNew {

    @MockBean
    private EmployeeServiceImpl employeeService;

    @Autowired
    private MockMvc mockMvc;//for web api call
    @Autowired
    private ObjectMapper objectMapper;//for serialize and deserialize like @RequestBody annotation

    @Autowired
    private EmployeeController employeeController;

    @Test
    @DisplayName("Junit for controller layer")
    public void givenEmployeeObj_whenCreateMethodCall_thenReturnEmployee() throws Exception {
        //given
        Employee employee=Employee.builder().firstName("bala").
                lastName("k").email("bala@gmail.com").build();

                   //given-stub service layer
        BDDMockito.given(employeeService.saveEmployee(ArgumentMatchers.any(Employee.class)))
                  .willAnswer((invocation)->invocation.getArgument(0));//calling 1 arg and return what they returned

        //when-->controller logic calling here
        ResultActions postManResult=mockMvc
                .perform(MockMvcRequestBuilders
                .post("/api/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee))
        );
        //verify-then
        postManResult.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", CoreMatchers.is(employee.getFirstName())));


    }

    @Test()
    @DisplayName("Junit for fetch all")
    @SneakyThrows
    public void givenListOfEmp_whenGetAll_then_returnListOfEmpObj(){
        //given
        Employee employee1=Employee.builder().firstName("bala").lastName("k").email("bala@gmail.com").build();
        Employee employee2=Employee.builder().firstName("pavi").lastName("j").email("pavi@gmail.com").build();
        List<Employee> employeelist= List.of(employee1,employee2);

        BDDMockito.given(employeeService.findAllEmployee()).willReturn(employeelist);
        //when-execute controller logic
       ResultActions postManRes= mockMvc.perform(MockMvcRequestBuilders
                .get("/api/all"));
       //then
        postManRes.andDo(MockMvcResultHandlers.print())
                  .andExpect(MockMvcResultMatchers.jsonPath("$.size()",CoreMatchers.is(2)));

    }
    @Test
    @SneakyThrows
    public void givenEmployeeID_when_thenReturnId(){
        //given
        Employee employee2=Employee.builder().id(1L).firstName("pavi").lastName("j").email("pavi@gmail.com").build();
        BDDMockito.given(employeeService.findByEmployeeId(1L)).willReturn(employee2);
        //when
       ResultActions postManRes= mockMvc.perform(MockMvcRequestBuilders.get("/api/{id}",1L));

       //then
        postManRes.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName",CoreMatchers.is("pavi")));
               // .andExpect(MockMvcResultMatchers.jsonPath("$.size()",CoreMatchers.is(1)));

    }





}
