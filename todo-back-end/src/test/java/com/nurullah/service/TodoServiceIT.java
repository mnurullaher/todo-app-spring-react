package com.nurullah.service;

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

import static com.nurullah.utils.CustomerTestUtils.*;
import static com.nurullah.utils.TodoTestUtils.*;
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
    public void should_save_and_return_todo() {
        customerRepository.save(getMockCustomer());

        Todo result = todoService.saveTodo(getMockAddTodoRequest(), USERNAME);

        then(result).isNotNull();
        then(result.getDescription()).isEqualTo(DESCRIPTION);
        then(result.getCustomer().getFullName()).isEqualTo(FULLNAME);
        then(result.getId()).isNotEqualTo(0);
    }

    @Test
    public void should_return_all_todos() {
        customerRepository.save(getMockCustomer());
        todoService.saveTodo(getMockAddTodoRequest(), USERNAME);

        var result = todoService.getAllTodos(USERNAME);

        then(result).isNotNull();
        then(result).hasSize(1);
        then(result.get(0).getCustomer().getFullName()).isEqualTo(FULLNAME);
    }

    @Test
    public void test_toggleCompletion() {
        var todo = todoService.saveTodo(getMockAddTodoRequest(), USERNAME);

        var result = todoService.toggleCompletion(todo.getId());

        then(result).isNotNull();
        then(result.isCompleted()).isNotEqualTo(IS_COMPLETED);
        then(result.getDescription()).isEqualTo(DESCRIPTION);
    }

    @Test
    public void should_remove_todo() {
        customerRepository.save(getMockCustomer());
        var todo = todoService.saveTodo(getMockAddTodoRequest(), USERNAME);

        then(todoService.getAllTodos(USERNAME)).hasSize(1);
        todoService.removeTodo(todo.getId());
        then(todoService.getAllTodos(USERNAME)).hasSize(0);
    }

}
