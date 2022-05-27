package com.shop.exception;

public class OutOfStockException extends RuntimeException {

    public OutOfStockException() {
        super("재고가 부족합니다");
    }
    public OutOfStockException(String msg) {
        super(msg);
    }
}
