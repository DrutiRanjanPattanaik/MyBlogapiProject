package com.blogapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(long id){
        super("Resource Not Found For id: "+id);
    }
//     here super() meaning is different. it will automatically print the msg in postman response section
//    private String resourceName; //eg. post
//    private String fieldName; // eg. id
//    private String fieldValue; // eg. 1000
//    public ResourceNotFoundException(String resourceName, String fieldName, String fieldValue) {
//        super();
//        this.resourceName = resourceName;
//        this.fieldName = fieldName;
//        this.fieldValue = fieldValue;
//    }
// post with id=1000 not found, meaning like this
}