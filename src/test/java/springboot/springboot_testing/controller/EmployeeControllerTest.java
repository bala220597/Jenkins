package springboot.springboot_testing.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import springboot.springboot_testing.entity.Employee;
import springboot.springboot_testing.service.Impl.EmployeeServiceImpl;

@WebMvcTest//for controller test load only controller needs other than wont care
//@ExtendWith(MockitoExtension.class)
public class EmployeeControllerTest {

    @MockBean
    private EmployeeServiceImpl employeeService;
  //  @InjectMocks
   // private EmployeeController employeeController;

    @Autowired
    private MockMvc mockMvc;//for api calling
    @Autowired
    private ObjectMapper objectMapper;// for serialize and deseralize java object like @ResponseBody annotation

    @Test
    @DisplayName("JUnit for controller call")
    public void givenEmployee_whenCreateMethodCall_thenReturnEmployee() throws Exception {
        //given
        Employee employee=Employee.builder()
                //.id(1L)
                .firstName("bala").lastName("k").email("bala@gmail.com").build();
        //given -stub method
       // BDDMockito.given(employeeService.saveEmployee(employee)).willReturn(employee);
        BDDMockito.given(employeeService.saveEmployee(ArgumentMatchers.any(Employee.class)))
                .willAnswer((invocation)->invocation.getArgument(0));

        //when
        ResultActions response=mockMvc.perform(MockMvcRequestBuilders
                .post("/api/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee))

        );
        //then-verify
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.firstName",
                                CoreMatchers.is(employee.getFirstName())));

    }

}
