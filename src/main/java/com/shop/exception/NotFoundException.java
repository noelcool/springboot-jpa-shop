package com.shop.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException() {
        super("NOT FOUND EXCEPTION");
    }

    public NotFoundException(String msg) {
        super(msg);
    }
}
