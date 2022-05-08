package com.nhnacademy.springmvc.controller.restController;



import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.nhnacademy.springmvc.domain.Student;
import com.nhnacademy.springmvc.exception.ValidationFailedException;
import com.nhnacademy.springmvc.repository.StudentRepository;
import com.nhnacademy.springmvc.repository.StudentRepositoryImpl;
import com.nhnacademy.springmvc.validator.StudentRegisterRequestValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.NestedServletException;

public class StudentRestControllerTest {
    private MockMvc mockMvc;
    private StudentRepository studentRepository;
    private StudentRegisterRequestValidator validator;

    @BeforeEach
    void setUp() {
        studentRepository = mock(StudentRepository.class);
//        studentRepository = new StudentRepositoryImpl();
        validator = new StudentRegisterRequestValidator();

        mockMvc = MockMvcBuilders
            .standaloneSetup(new StudentRestController(studentRepository, validator))
            .build();
    }

    @DisplayName("join학생이 등록이 정상적으로 됐다는 가정하에 json 데이터가 리스폰스바디에 잘 들어간다. 상태값은 201이며 contentType은 json이다.")
    @Test
    void registerStudentTest_Basic() throws Exception {
        Student student = new Student("12345", "join", "join@naver.com",
            100, "hi");
        student.setId(1L);

        when(studentRepository.register(anyString(), anyString(), anyString(), anyInt(), anyString()))
            .thenReturn(student);

        mockMvc.perform(post("/studentsRest")
                .contentType("application/json;charset=UTF-8")
                .accept(MediaType.APPLICATION_JSON)
                .content("{\n" +
                    "  \"name\": \"jun\",\n" +
                    "  \"email\": \"gbeovhsqhtka@naer.com\",\n" +
                    "  \"score\": 100,\n" +
                    "  \"comment\": \"hello\",\n" +
                    "  \"password\": \"12345\"\n" +
                    "}"))
            .andExpect(status().isCreated())
            .andExpect(content().contentType("application/json"))
            .andExpect(content().string("{\"id\":1,\"name\":\"join\",\"email\":\"join@naver.com\",\"score\":100,\"comment\":\"hi\",\"password\":\"12345\"}"))
            .andDo(print());
    }



    @DisplayName("json 데이터를 넘겨서 실제 학생을 등록하고 정보를 json 데이터로 넘긴다(정상적으로 레포지토리에 등록된게 반환되어서 json값이 리스폰스바디에 들어가는지를 확인한다(repository test를 따로하지 않았음으로 이장에서 함께 진행한다)")
    @Test
    void registerStudentTest_DetailRepository() throws Exception {
        studentRepository = new StudentRepositoryImpl();
        mockMvc = MockMvcBuilders
            .standaloneSetup(new StudentRestController(studentRepository, validator))
            .build();

        MvcResult mvcResult = mockMvc.perform(post("/studentsRest")
                .contentType("application/json;charset=UTF-8")
                .accept(MediaType.APPLICATION_JSON)
                .content("{\n" +
                    "  \"name\": \"jun\",\n" +
                    "  \"email\": \"gbeovhsqhtka@naer.com\",\n" +
                    "  \"score\": 100,\n" +
                    "  \"comment\": \"hello\",\n" +
                    "  \"password\": \"12345\"\n" +
                    "}"))
            .andExpect(status().isCreated())
            .andExpect(content().contentType("application/json"))
            .andExpect(content().string("{\"id\":1,\"name\":\"jun\",\"email\":\"gbeovhsqhtka@naer.com\",\"score\":100,\"comment\":\"hello\",\"password\":\"12345\"}"))
            .andDo(print())
            .andReturn();

        // 위의 content().string과 같은 코드이다.
//        assertThat(mvcResult.getResponse().getContentAsString())
//            .isEqualTo("{\"id\":1,\"name\":\"jun\",\"email\":\"gbeovhsqhtka@naer.com\",\"score\":100,\"comment\":\"hello\",\"password\":\"12345\"}");
    }



    @DisplayName("json데이터로 이름 입력값을 len(0)으로 주었을때 유효성검증이 잘 일어나는지 확인한다.")
    @Test
    void registerStudentTest_validator() throws Exception {
        Throwable th = catchThrowable(() -> mockMvc.perform(post("/studentsRest")
                .contentType("application/json;charset=UTF-8")
                .accept(MediaType.APPLICATION_JSON)
                .content("{\n" +
                    "  \"name\": \"\",\n" +
                    "  \"email\": \"gbeovhsqhtka@naer.com\",\n" +
                    "  \"score\": 100,\n" +
                    "  \"comment\": \"hello\",\n" +
                    "  \"password\": \"12345\"\n" +
                    "}"))
            .andDo(print()));

        assertThat(th).isInstanceOf(NestedServletException.class)
            .hasCauseInstanceOf(ValidationFailedException.class);
    }

    @DisplayName("join 학생을 조회한다. 상태값 200 및 반환데이터가 json이 맞는지 검증한다.")
    @Test
    void viewStudentTest() throws Exception {
        Student student = new Student("12345", "join", "join@naver.com",
            100, "hi");
        student.setId(1L);

        when(studentRepository.getStudent(anyLong()))
            .thenReturn(student);

        MvcResult mvcResult = mockMvc.perform(get("/studentsRest/1")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andDo(print())
            .andReturn();

        assertThat(mvcResult.getResponse().getContentAsString())
            .isEqualTo("{\"id\":1,\"name\":\"join\",\"email\":\"join@naver.com\",\"score\":100,\"comment\":\"hi\",\"password\":\"12345\"}");
    }

    @DisplayName("repository modify기능이 잘된다는 가정하에 put방식 매핑이 잘되는지, join 값이 잘 반환되는지, 상태값은 200이 맞는지 확인한다.")
    @Test
    void modifyStudentTest_Basic() throws Exception {
        Student student = new Student("12345", "join", "join@naver.com",
            100, "hi");
        student.setId(1L);

        when(studentRepository.modify(anyLong(), any()))
            .thenReturn(student);

        mockMvc.perform(put("/studentsRest/1")
            .accept(MediaType.APPLICATION_JSON)
            .contentType("application/json;charset=UTF-8")
            .content("{\n" +
                "  \"name\": \"hirack\",\n" +
                "  \"email\": \"gbeovhsqhtka@naer.com\",\n" +
                "  \"score\": 100,\n" +
                "  \"comment\": \"hello\",\n" +
                "  \"password\": \"12345\"\n" +
                "}"))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andExpect(content().string("{\"id\":1,\"name\":\"join\",\"email\":\"join@naver.com\",\"score\":100,\"comment\":\"hi\",\"password\":\"12345\"}"));
    }


    @DisplayName("put방식시에 유효성검사")
    @Test
    void modifyStudentTest_Validator() {
        Throwable th = catchThrowable(() -> mockMvc.perform(put("/studentsRest/1")
                .contentType("application/json;charset=UTF-8")
                .accept(MediaType.APPLICATION_JSON)
                .content("{\n" +
                    "  \"name\": \"\",\n" +
                    "  \"email\": \"gbeovhsqhtka@naer.com\",\n" +
                    "  \"score\": 100,\n" +
                    "  \"comment\": \"hello\",\n" +
                    "  \"password\": \"12345\"\n" +
                    "}"))
            .andDo(print()));

        assertThat(th).isInstanceOf(NestedServletException.class)
            .hasCauseInstanceOf(ValidationFailedException.class);
    }

}