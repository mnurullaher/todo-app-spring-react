package com.nurullah.service;

import com.nurullah.dto.AddTodoRequest;
import com.nurullah.model.Customer;
import com.nurullah.model.Todo;
import com.nurullah.repository.CustomerRepository;
import com.nurullah.repository.TodoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;

@Testcontainers
@SpringBootTest
public class TodoServiceIT {

    @Container
    static MySQLContainer mySQLContainer = new MySQLContainer<>(DockerImageName.parse("mysql:8.0.33"));

    @DynamicPropertySource
    private static void setupProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mySQLContainer::getUsername);
        registry.add("spring.datasource.password", mySQLContainer::getPassword);
    }

    @Autowired
    private TodoRepository todoRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private TodoService todoService;

    @BeforeEach
    public void setup() {
        customerRepository.deleteAll();
        todoRepository.deleteAll();
    }

    @Test
    public void test_saveTodo() {
        var username = "nurullaher";
        customerRepository.save(
                new Customer(1, "Nurullah Er", username, "12345", List.of())
        );
        AddTodoRequest addTodoRequest = AddTodoRequest.builder()
                .description("new todo")
                .deadline(new Date())
                .completed(false)
                .build();

        Todo result = todoService.saveTodo(addTodoRequest, username);

        then(result).isNotNull();
        then(result.getDescription()).isEqualTo("new todo");
        then(result.getCustomer().getFullName()).isEqualTo("Nurullah Er");
        then(result.getId()).isNotEqualTo(0);
    }

    @Test
    public void test_getAllTodos() {
        var username = "username";
        customerRepository.save(
                new Customer(1, "Nurullah Er", username, "12345", List.of())
        );
        AddTodoRequest addTodoRequest = AddTodoRequest.builder()
                .description("new todo")
                .deadline(new Date())
                .completed(false)
                .build();

        todoService.saveTodo(addTodoRequest, username);

        var result = todoService.getAllTodos(username);

        then(result).isNotNull();
        then(result).hasSize(1);
        then(result.get(0).getCustomer().getFullName()).isEqualTo("Nurullah Er");
    }

    @Test
    public void test_toggleCompletion() {
        var todo = todoService.saveTodo(
                new AddTodoRequest("new todo", new Date(), false),
                "nurullaher"
        );

        var result = todoService.toggleCompletion(todo.getId());

        then(result).isNotNull();
        then(result.isCompleted()).isEqualTo(true);
        then(result.getDescription()).isEqualTo("new todo");
    }

    @Test
    public void test_removeTodo() {
        customerRepository.save(
                new Customer(1, "Nurullah Er", "nurullaher", "12345", List.of())
        );
        var todo = todoService.saveTodo(
                new AddTodoRequest("new todo", new Date(), false),
                "nurullaher"
        );

        then(todoService.getAllTodos("nurullaher")).hasSize(1);
        todoService.removeTodo(todo.getId());
        then(todoService.getAllTodos("nurullaher")).hasSize(0);
    }

}
