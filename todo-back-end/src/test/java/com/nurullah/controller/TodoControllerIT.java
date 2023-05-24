package com.nurullah.controller;

import com.nurullah.dto.AddTodoRequest;
import com.nurullah.model.Customer;
import com.nurullah.model.Todo;
import com.nurullah.repository.CustomerRepository;
import com.nurullah.repository.TodoRepository;
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

import java.util.Date;
import java.util.List;

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
        var addTodoRequest = new AddTodoRequest("todo", new Date(), false);

        mockMvc.perform(post("/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addTodoRequest))
                        .principal(() -> "nurullaher"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.description", Matchers.is("todo")))
                .andExpect(jsonPath("$.completed", Matchers.is(false)));
    }

    @Test
    public void should_return_all_todos() throws Exception {
        var username = "nurullaher";
        var customer = new Customer(1, "Nurullah Er", username, "12345", List.of());
        customerRepository.save(customer);
        todoService.saveTodo(new AddTodoRequest("todo1", new Date(), false), username);
        todoService.saveTodo(new AddTodoRequest("todo2", new Date(), true), username);

        mockMvc.perform(get("/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .principal(() -> username))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.size()", Matchers.is(2)))
                .andExpect(jsonPath("$[0].description", Matchers.is("todo1")))
                .andExpect(jsonPath("$[1].completed", Matchers.is(true)));
    }

    @Test
    public void should_toggle_completion() throws Exception {
        long id = 1;
        var customer = new Customer(1, "Nurullah Er", "nurullaher", "12345", List.of());
        customerRepository.save(customer);
        todoRepository.save(new Todo(1, "todo", new Date(), false, customer));

        mockMvc.perform(put("/todos/{id}/toggle-completion", id))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.id", Matchers.is(1)))
                .andExpect(jsonPath("$.description", Matchers.is("todo")))
                .andExpect(jsonPath("$.completed", Matchers.is(true)));
    }

    @Test
    public void should_delete_todo() throws Exception {
        var username = "nurullaher";
        long id = 1;
        var customer = new Customer(1, "Nurullah Er", username, "12345", List.of());
        customerRepository.save(customer);
        var todo = todoService.saveTodo(
                new AddTodoRequest("todo", new Date(), false),
                username
        );

        then(todoRepository.findAll()).hasSize(1);
        mockMvc.perform(delete("/todos")
                        .param("id", String.valueOf(todo.getId()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));
        then(todoRepository.findAll()).hasSize(0);
    }
}
