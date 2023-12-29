package com.springboot.restJPA.response;

import java.util.List;

public class ListServiceResponse<Type> extends ServiceResponse<List<Type>> {
    public ListServiceResponse(){
        super();
    }
}
