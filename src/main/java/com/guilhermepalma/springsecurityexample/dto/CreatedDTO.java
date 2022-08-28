package com.guilhermepalma.springsecurityexample.dto;

import com.guilhermepalma.springsecurityexample.utis.Utils;
import lombok.Data;

import java.util.List;

@Data
public class CreatedDTO<T> {

    private List<T> data;

    private Long errors;
    private List<String> logErrors;

    private Long total;


    public CreatedDTO(List<T> data) {
        this.data = data;
        if(!Utils.isNullOrEmpty(data)){
            this.total = (long) data.size();
        }
    }
}
