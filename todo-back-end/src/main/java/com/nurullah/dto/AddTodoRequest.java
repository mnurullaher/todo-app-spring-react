package com.nurullah.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AddTodoRequest {

    private String description;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date deadline;

    private boolean completed;

}
