package com.nurullah.service;

import com.nurullah.model.Todo;
import com.nurullah.repository.TodoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.nurullah.utils.CustomerTestUtils.USERNAME;
import static com.nurullah.utils.CustomerTestUtils.getMockCustomer;
import static com.nurullah.utils.TodoTestUtils.*;
import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
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
    public void should_save_and_return_todo() {
        var customer = getMockCustomer();

        doAnswer(x -> {
            var todo = (Todo) x.getArgument(0);
            todo.setId(1);
            return todo;
        }).when(todoRepository).save(any(Todo.class));
        given(customerService.findByUserName(USERNAME)).willReturn(customer);

        Todo result = todoService.saveTodo(getMockAddTodoRequest(), USERNAME);

        then(result).isNotNull();
        then(result.getCustomer()).isEqualTo(customer);
        then(result.getDescription()).isEqualTo(DESCRIPTION);
        then(result.getId()).isEqualTo(1);
    }

    @Test
    public void should_return_all_todos() {
        var customer = getMockCustomer();
        customer.setTodos(List.of(getMockTodo()));

        given(customerService.findByUserName(USERNAME)).willReturn(customer);

        var result = todoService.getAllTodos(USERNAME);

        then(result).isNotNull();
        then(result).hasSize(1);
        then(result).isEqualTo(customer.getTodos());
    }

    @Test
    public void should_toggle_todo_completion() {
        var todo = getMockTodo();

        given(todoRepository.findById(ID)).willReturn(Optional.of(todo));
        given(todoRepository.save(todo)).willReturn(todo);

        var result = todoService.toggleCompletion(ID);

        then(result).isNotNull();
        then(result.isCompleted()).isNotEqualTo(IS_COMPLETED);
        then(result.getDescription()).isEqualTo(DESCRIPTION);
    }

    @Test
    public void should_throw_exception_if_todo_is_not_found() {
        given(todoRepository.findById(ID)).willThrow(EntityNotFoundException.class);

        assertThrows(EntityNotFoundException.class, () -> todoService.toggleCompletion(ID));
    }

    @Test
    public void test_removeTodo() {
        Todo todo = getMockTodo();
        given(todoRepository.findById(ID)).willReturn(Optional.of(todo));

        todoService.removeTodo(ID);
        Mockito.verify(todoRepository).delete(todo);
    }
}