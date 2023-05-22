package com.nurullah.controller;

import com.nurullah.dto.AddTodoRequest;
import com.nurullah.model.Todo;
import com.nurullah.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

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

    @PutMapping("/{id}/toggle-completion")
    public Todo updateTodo(@PathVariable("id") long id) {
        return todoService.toggleCompletion(id);
    }

    @DeleteMapping
    public void removeTodo(@RequestParam long id) {
        todoService.removeTodo(id);
    }

}
