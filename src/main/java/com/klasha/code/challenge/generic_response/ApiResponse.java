package com.klasha.code.challenge.generic_response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApiResponse<T> {
    private boolean error;
    private String msg;
    private T data;
}
