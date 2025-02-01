package com.dev.scheduleTransfer.Controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@ToString
public class ErrorResponse {
    private String message;
    private Boolean hasError;
}