package com.guilhermepalma.springsecurityexample.dto;

import lombok.Data;

@Data
public class View<T> {

    private T data;

}
