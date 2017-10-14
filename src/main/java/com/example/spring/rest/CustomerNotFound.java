package com.example.spring.rest;

public class CustomerNotFound extends RuntimeException
{
    public CustomerNotFound(Long id) {
        super("Could not find Customer with id # " + id);
    }

}
