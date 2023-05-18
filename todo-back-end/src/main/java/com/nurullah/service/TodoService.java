package com.nurullah.service;

import com.nurullah.dto.AddTodoRequest;
import com.nurullah.model.Todo;
import com.nurullah.repository.TodoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TodoService {

    private final TodoRepository todoRepository;

    private final CustomerService customerService;

    public Todo saveTodo(AddTodoRequest request, String username) {

        Todo newTodo = Todo.builder()
                .description(request.getDescription())
                .deadline(request.getDeadline())
                .isCompleted(request.isCompleted())
                .build();

        newTodo.setCustomer(customerService.findByUserName(username));

        return todoRepository.save(newTodo);
    }

    public List<Todo> getAllTodos(String username) {
        var customer = customerService.findByUserName(username);
        return customer.getTodos();
    }

    public Todo toggleCompletion(long id) {
        var todo = todoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Object not found"));
        todo.setCompleted(!todo.isCompleted());
        return todoRepository.save(todo);
    }

    public Todo removeTodo(long id) {
        var todo =  todoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Object not found"));
        todoRepository.delete(todo);
        return todo;
    }
}
