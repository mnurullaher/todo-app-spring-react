package com.nurullah.controller;

import com.nurullah.repository.CustomerRepository;
import com.nurullah.repository.TodoRepository;
import com.nurullah.service.CustomerService;
import com.nurullah.service.TodoService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import org.testcontainers.utility.DockerImageName;

import static com.nurullah.utils.CustomerTestUtils.USERNAME;
import static com.nurullah.utils.CustomerTestUtils.getMockSignupRequest;
import static com.nurullah.utils.TodoTestUtils.*;
import static org.assertj.core.api.BDDAssertions.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class TodoControllerIT {

    @Autowired
    private TodoRepository todoRepository;
    @Autowired
    private TodoService todoService;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private MockMvc mockMvc;
    ObjectMapper objectMapper = new ObjectMapper();

    @Container
    static MySQLContainer mySQLContainer = new MySQLContainer<>(DockerImageName.parse("mysql:8.0.33"));

    @DynamicPropertySource
    private static void setupProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mySQLContainer::getUsername);
        registry.add("spring.datasource.password", mySQLContainer::getPassword);
    }

    @BeforeEach
    public void setup() {
        todoRepository.deleteAll();
        customerRepository.deleteAll();
    }

    @Test
    public void should_create_todo() throws Exception {
        mockMvc.perform(post("/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getMockAddTodoRequest()))
                        .principal(() -> USERNAME))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.description", Matchers.is(DESCRIPTION)))
                .andExpect(jsonPath("$.completed", Matchers.is(IS_COMPLETED)));
    }

    @Test
    public void should_return_all_todos() throws Exception {
        customerService.saveCustomer(getMockSignupRequest());
        todoService.saveTodo(getMockAddTodoRequest(), USERNAME);

        mockMvc.perform(get("/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .principal(() -> USERNAME))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.size()", Matchers.is(1)))
                .andExpect(jsonPath("$[0].description", Matchers.is(DESCRIPTION)))
                .andExpect(jsonPath("$[0].completed", Matchers.is(IS_COMPLETED)));
    }

    @Test
    public void should_toggle_completion() throws Exception {
        customerService.saveCustomer(getMockSignupRequest());
        todoService.saveTodo(getMockAddTodoRequest(), USERNAME);

        mockMvc.perform(put("/todos/{id}/toggle-completion", ID))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.id", Matchers.not(0)))
                .andExpect(jsonPath("$.description", Matchers.is(DESCRIPTION)))
                .andExpect(jsonPath("$.completed", Matchers.not(IS_COMPLETED)));
    }

    @Test
    public void should_delete_todo() throws Exception {
        customerService.saveCustomer(getMockSignupRequest());
        var todo = todoService.saveTodo(getMockAddTodoRequest(), USERNAME);

        then(todoRepository.findAll()).hasSize(1);

        mockMvc.perform(delete("/todos")
                        .param("id", String.valueOf(todo.getId()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));

        then(todoRepository.findAll()).hasSize(0);
    }
}
