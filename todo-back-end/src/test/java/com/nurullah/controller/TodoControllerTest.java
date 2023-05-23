package com.nurullah.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nurullah.dto.AddTodoRequest;
import com.nurullah.model.Customer;
import com.nurullah.model.Todo;
import com.nurullah.repository.TodoRepository;
import com.nurullah.service.TodoService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = TodoController.class)
@AutoConfigureMockMvc(addFilters = false)
class TodoControllerTest {
    @MockBean
    private TodoService todoService;
    @Mock
    private TodoRepository todoRepository;
    @Autowired
    private MockMvc mockMvc;
    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void should_create_todo() throws Exception {
        var username = "nurullaher";
        AddTodoRequest addTodoRequest = AddTodoRequest.builder()
                .description("new todo")
                .deadline(new Date())
                .completed(false)
                .build();

        given(todoService.saveTodo(addTodoRequest, username)).willReturn(
                new Todo(1, "todo", new Date(), false, new Customer())
        );

        mockMvc.perform(post("/todos")
                        .content(objectMapper.writeValueAsString(addTodoRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .principal(() -> username))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.id", Matchers.is(1)))
                .andExpect(jsonPath("$.description", Matchers.is("todo")));
    }

    @Test
    public void should_get_all_todos() throws Exception {
        var username = "nurullaher";

        given(todoService.getAllTodos(username)).willReturn(List.of(
                new Todo(1, "todo1", new Date(), false, new Customer()),
                new Todo(2, "todo2", new Date(), true, new Customer())
        ));

        mockMvc.perform(get("/todos")
                        .principal(() -> username))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.size()", Matchers.is(2)))
                .andExpect(jsonPath("$[0].description", Matchers.is("todo1")))
                .andExpect(jsonPath("$[1].description", Matchers.is("todo2")));
    }

    @Test
    public void should_toggle_completion() throws Exception {
        var id = 1;
        given(todoService.toggleCompletion(id)).willReturn(
                new Todo(id, "todo", new Date(), true, new Customer())
        );

        mockMvc.perform(put("/todos/{id}/toggle-completion", id))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.id", Matchers.is(1)))
                .andExpect(jsonPath("$.description", Matchers.is("todo")))
                .andExpect(jsonPath("$.completed", Matchers.is(true)));
    }

    @Test
    public void should_delete_todo() throws Exception {
        long id = 1;
        mockMvc.perform(delete("/todos")
                        .param("id", String.valueOf(id))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));

        verify(todoService).removeTodo(id);
    }
}