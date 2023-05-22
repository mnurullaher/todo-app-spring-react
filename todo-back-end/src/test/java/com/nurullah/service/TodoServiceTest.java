package com.nurullah.service;

import com.nurullah.dto.AddTodoRequest;
import com.nurullah.model.Customer;
import com.nurullah.model.Todo;
import com.nurullah.repository.TodoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.BDDAssertions.then;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.doAnswer;

@ExtendWith(MockitoExtension.class)
class TodoServiceTest {

    @Mock
    private TodoRepository todoRepository;
    @Mock
    private CustomerService customerService;
    @InjectMocks
    private TodoService todoService;

    @Test
    public void test_saveTodo() {

        String username = "nurullaher";
        AddTodoRequest addTodoRequest = AddTodoRequest.builder()
                .description("new todo")
                .deadline(new Date())
                .completed(false)
                .build();

        var customer = Customer.builder()
                .id(1)
                .fullName("Nurullah ER")
                .username(username)
                .password("12345")
                .todos(List.of())
                .build();

        doAnswer(x -> {
            var todo =  (Todo) x.getArgument(0);
            todo.setId(1);
            return todo;
        }).when(todoRepository).save(any(Todo.class));

        given(customerService.findByUserName(username)).willReturn(customer);

        Todo result = todoService.saveTodo(addTodoRequest, username);

        then(result).isNotNull();
        then(result.isCompleted()).isEqualTo(false);
        then(result.getCustomer()).isEqualTo(customer);
        then(result.getDescription()).isEqualTo("new todo");
        then(result.getId()).isEqualTo(1);
    }

    @Test
    public void test_getAllTodos() {

        var username = "nurullaher";
        var customer = Customer.builder()
                .id(1)
                .fullName("Nurullah ER")
                .username(username)
                .password("12345")
                .build();
        customer.setTodos(
                List.of(
                        new Todo(1, "todo1", new Date(), false, customer),
                        new Todo(2, "todo2", new Date(), true, customer),
                        new Todo(3, "todo3", new Date(), true, customer)
                )
        );
        given(customerService.findByUserName(username)).willReturn(customer);

        var result = todoService.getAllTodos(username);

        then(result).isNotNull();
        then(result).hasSize(3);
        then(result).isEqualTo(customer.getTodos());

    }

    @Test
    public void test_toggleCompletion() {
        long id = 1;
        var customer = Customer.builder()
                .id(1)
                .fullName("Nurullah ER")
                .username("nurullaher")
                .password("12345")
                .todos(List.of())
                .build();
        Todo todo = new Todo(
                id, "new todo", new Date(), false, customer
        );

        given(todoRepository.findById(id)).willReturn(Optional.of(todo));
        given(todoRepository.save(todo)).willReturn(todo);

        var result = todoService.toggleCompletion(id);

        then(result).isNotNull();
        then(result.isCompleted()).isEqualTo(true);
        then(result.getDescription()).isEqualTo("new todo");
    }

    @Test
    public void test_removeTodo() {
        long id = 1;
        Todo todo = Todo.builder()
                .id(id)
                .description("new todo")
                .isCompleted(false)
                .deadline(new Date())
                .customer(new Customer())
                .build();
        given(todoRepository.findById(id)).willReturn(Optional.of(todo));

        todoService.removeTodo(id);
        Mockito.verify(todoRepository).delete(todo);
    }

}