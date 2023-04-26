package com.nurullah.controller;

import com.nurullah.model.Todo;
import com.nurullah.service.TodoService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
public class TodoController {

    @Autowired
    private TodoService todoService;

    @PostMapping("/todos")
    public Todo addTodo(
            @RequestParam String description,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date deadline,
            @RequestParam boolean isCompleted,
            HttpServletResponse response
    ) {
        response.addHeader("Access-Control-Allow-Origin", "*");
        return todoService.saveTodo(description, deadline, isCompleted);
    }

    @GetMapping("/todos")
    public List<Todo> getAllTodos(HttpServletResponse response){
        response.addHeader("Access-Control-Allow-Origin", "*");
        return todoService.getAllTodos();
    }

    @PostMapping("/todos/update")
    public Todo updateTodo(@RequestParam long id, HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "*");
        return todoService.updateTodo(id);
    }

    @PostMapping("todos/remove")
    public Todo removeTodo(@RequestParam long id, HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "*");
        return todoService.removeTodo(id);
    }

}
