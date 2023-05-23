package com.nurullah.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nurullah.dto.AddTodoRequest;
import com.nurullah.model.Customer;
import com.nurullah.model.Todo;
import com.nurullah.service.TodoService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = TodoController.class)
@AutoConfigureMockMvc(addFilters = false)
class TodoControllerTest {
    @MockBean
    private TodoService todoService;
    @Autowired
    private MockMvc mockMvc;
    ObjectMapper objectMapper = new ObjectMapper();
    @Test
    public void shouldCreateTodo() throws Exception {
        AddTodoRequest addTodoRequest = AddTodoRequest.builder()
                .description("new todo")
                .deadline(new Date())
                .completed(false)
                .build();

        given(todoService.saveTodo(addTodoRequest, "nurullaher")).willReturn(
                new Todo(1, "todo", new Date(), false, new Customer())
        );

        mockMvc.perform(post("/todos")
                        .content(objectMapper.writeValueAsString(addTodoRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .principal(() -> "nurullaher"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.id", Matchers.is(1)))
                .andExpect(jsonPath("$.description", Matchers.is("todo")));
    }
}