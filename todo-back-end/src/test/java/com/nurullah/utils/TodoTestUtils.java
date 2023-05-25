package com.nurullah.utils;

import com.nurullah.dto.AddTodoRequest;
import com.nurullah.model.Customer;
import com.nurullah.model.Todo;

import java.util.Date;

public class TodoTestUtils {
    public static final String DESCRIPTION = "new todo";
    public static final Boolean IS_COMPLETED = false;
    public static final long ID = 1;

    public static AddTodoRequest getMockAddTodoRequest() {
        return new AddTodoRequest(DESCRIPTION, new Date(), IS_COMPLETED);
    }

    public static Todo getMockTodo() {
        return new Todo(ID, DESCRIPTION, new Date(), IS_COMPLETED, new Customer());
    }
}
