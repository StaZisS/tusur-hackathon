package org.hits.backend.hackathon_tusur.public_interface.exception;

import lombok.Getter;

@Getter
public class ExceptionInApplication extends RuntimeException {
    private final ExceptionType type;

    public ExceptionInApplication(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }

}
