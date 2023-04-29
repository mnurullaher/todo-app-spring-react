package com.nurullah.controller;

import com.nurullah.model.Todo;
import com.nurullah.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/todos")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @CrossOrigin(origins = "*")
    @PostMapping
    public Todo addTodo(
            @RequestParam String description,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date deadline,
            @RequestParam boolean isCompleted
    ) {
        return todoService.saveTodo(description, deadline, isCompleted);
    }

    @GetMapping
    public List<Todo> getAllTodos(){
        return todoService.getAllTodos();
    }

    @PutMapping
    public Todo updateTodo(@RequestParam long id) {
        return todoService.updateTodo(id);
    }

    @DeleteMapping
    public Todo removeTodo(@RequestParam long id) {
        return todoService.removeTodo(id);
    }

}
