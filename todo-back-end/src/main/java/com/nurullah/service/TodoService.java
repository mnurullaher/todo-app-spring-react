package com.nurullah.service;

import com.nurullah.model.Todo;
import com.nurullah.repository.TodoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;

    public void saveTodo(String description, Date deadline, boolean isCompleted) {

        Todo newTodo = Todo.builder()
                .description(description)
                .deadline(deadline)
                .isCompleted(isCompleted)
                .build();

        todoRepository.save(newTodo);
    }

    public List<Todo> getAllTodos() {
        List<Todo> todoList = new ArrayList<>();
        todoRepository.findAll().forEach(todoList::add);
        return todoList;
    }

    public Todo updateTodo(long id) {
        var todo = todoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Object not found"));
        todo.setCompleted(true);
        return todoRepository.save(todo);
    }

    public Todo removeTodo(long id) {
        var todo =  todoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Object not found"));
        todoRepository.delete(todo);
        return todo;
    }
}
