package com.guilhermepalma.springsecurityexample.dto;

import com.guilhermepalma.springsecurityexample.utis.Utils;
import lombok.Data;

import java.util.List;

@Data
public class ListView<T> {

    private List<T> data;
    private Long total;

    public ListView(List<T> data) {
        this.data = data;
        if(!Utils.isNullOrEmpty(data)){
            this.total = (long) data.size();
        }
    }
}
