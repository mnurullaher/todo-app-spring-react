package com.nurullah.controller;

import com.nurullah.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class TodoController {

    @Autowired
    private TodoService todoService;

    @PostMapping("/add-todo")
    public String addTodo(
            @RequestParam String description,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date deadline,
            @RequestParam boolean isCompleted
    ) {
        todoService.saveTodo(description, deadline, isCompleted);
        return "Todo created";
    }

}
