package com.nurullah.utils;

import com.nurullah.dto.AddTodoRequest;
import com.nurullah.dto.LoginRequest;
import com.nurullah.dto.SignupRequest;
import com.nurullah.model.Customer;

import java.util.Date;
import java.util.List;

public class CustomerTestUtils {
    public static final String FULLNAME = "Nurullah Er";
    public static final String USERNAME = "nurullaher";
    public static final String PASSWORD = "12345";
    public static final long ID = 1;

    public static SignupRequest getMockSignupRequest() {
        return new SignupRequest(FULLNAME, USERNAME, PASSWORD);
    }
    public static LoginRequest getMockLoginRequest() {
        return new LoginRequest(USERNAME, PASSWORD);
    }

    public static Customer getMockCustomer() {
        return new Customer(ID, FULLNAME, USERNAME, PASSWORD, List.of());
    }



}
