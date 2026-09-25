package com.payvix.common.exception;

import lombok.Getter;

import java.util.UUID;

@Getter
public class ResourceNotFoundException extends RuntimeException{


    private final String resourceName;
    private final Object identifier;

    public ResourceNotFoundException(String resourceName, Object identifier) {
        super(resourceName +" not found "+ identifier);
       this.identifier=identifier;
       this.resourceName=resourceName;
    }

}
