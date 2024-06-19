package com.springboot.restJPA.controller;

import com.springboot.restJPA.response.ServiceResponse;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

public abstract class BaseController {
    public ResponseEntity<ServiceResponse<?>> resultFromServiceResponse(ServiceResponse<?> serviceResponse){
        return ResponseEntity.status(serviceResponse.getCode()).body(serviceResponse);
    }
}
