package dev.practice.springstore.exceptions;

public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException() {
        super("Oops. Product not Found!");
    }

    public ProductNotFoundException(String message) {
        super(message);
    }
}
