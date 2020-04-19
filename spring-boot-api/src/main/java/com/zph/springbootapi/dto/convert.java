package com.zph.springbootapi.dto;

public interface convert<S,T> {

    T convert(S s,T t);
    T convert(S s);


}
