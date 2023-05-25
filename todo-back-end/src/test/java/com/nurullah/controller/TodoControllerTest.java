package com.nurullah.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nurullah.service.TodoService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.nurullah.utils.CustomerTestUtils.USERNAME;
import static com.nurullah.utils.TodoTestUtils.*;
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
    @Autowired
    private MockMvc mockMvc;
    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void should_create_todo() throws Exception {
        var addTodoRequest = getMockAddTodoRequest();

        given(todoService.saveTodo(addTodoRequest, USERNAME)).willReturn(getMockTodo());

        mockMvc.perform(post("/todos")
                        .content(objectMapper.writeValueAsString(addTodoRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .principal(() -> USERNAME))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.id", Matchers.is((int) ID)))
                .andExpect(jsonPath("$.description", Matchers.is(DESCRIPTION)));
    }

    @Test
    public void should_get_all_todos() throws Exception {
        given(todoService.getAllTodos(USERNAME)).willReturn(List.of(getMockTodo()));

        mockMvc.perform(get("/todos")
                        .principal(() -> USERNAME))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.size()", Matchers.is(1)))
                .andExpect(jsonPath("$[0].description", Matchers.is(DESCRIPTION)));
    }

    @Test
    public void should_toggle_completion() throws Exception {
        mockMvc.perform(put("/todos/{id}/toggle-completion", ID))
                .andExpect(status().is(200));

        verify(todoService).toggleCompletion(ID);
    }

    @Test
    public void should_delete_todo() throws Exception {
        mockMvc.perform(delete("/todos")
                        .param("id", String.valueOf(ID))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));

        verify(todoService).removeTodo(ID);
    }
}