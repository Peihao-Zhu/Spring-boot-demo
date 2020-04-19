package com.zph.springbootadvanced.form;

public interface FormConvert<S,T> {
    T convert(S s);
}
