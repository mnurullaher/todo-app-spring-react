package com.nurullah.service;

import com.nurullah.model.Todo;
import com.nurullah.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

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
}
