package com.dev.scheduleTransfer.Dto;


class Error {
    String message;
    Boolean hasError;

    public Error(String message, Boolean hasError) {
        this.message = message;
        this.hasError = hasError;
    }
}

public class ScheduledTransactionControllerResDto<T> {
    public  Error error;
    public T data;

    @Override
    public String toString() {
        return "{" +
                "error:" + error +
                ", data:" + data +
                '}';
    }
}
