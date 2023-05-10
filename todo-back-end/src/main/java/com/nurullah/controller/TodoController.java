package com.nurullah.controller;

import com.nurullah.dto.AddTodoRequest;
import com.nurullah.model.Todo;
import com.nurullah.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/todos")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @PostMapping
    public Todo addTodo(@RequestBody AddTodoRequest request, Principal principal) {
        return todoService.saveTodo(request, principal.getName());
    }

    @GetMapping
    public List<Todo> getAllTodos(Principal principal){
        return todoService.getAllTodos(principal.getName());
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
