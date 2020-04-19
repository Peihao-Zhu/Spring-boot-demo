package com.zph.springbootapi.handler;

import com.zph.springbootapi.exception.InvalidRequestException;
import com.zph.springbootapi.exception.NotFoundException;
import com.zph.springbootapi.resource.ErrorResource;
import com.zph.springbootapi.resource.FieldResource;
import com.zph.springbootapi.resource.InvalidErrorResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ApiExceptionHandler {

    private final Logger logger= LoggerFactory.getLogger(this.toString());

    /**
     * 处理资源找不到异常
     * @param e
     * @return
     */
    @ExceptionHandler(NotFoundException.class)
    @ResponseBody
    public ResponseEntity<?> handleNotfound(RuntimeException e){
        ErrorResource errorResource=new ErrorResource(e.getMessage());
        ResponseEntity result=new ResponseEntity<Object>(errorResource, HttpStatus.NOT_FOUND);
        logger.warn("-----------Return--------{}",result);
        return result;

    }

    /**
     * 处理参数验证失败 异常
     * @param e
     * @return
     */
    @ExceptionHandler(InvalidRequestException.class)
    @ResponseBody
    public ResponseEntity<?>handleInvalidRequest(InvalidRequestException e){
        Errors errors=e.getErrors();

        List<FieldResource>fieldResources=new ArrayList<>();
        List<FieldError> fieldErrors=errors.getFieldErrors();

        for(FieldError fieldError:fieldErrors){
            FieldResource fieldResource=new FieldResource(
                    fieldError.getObjectName(),
                    fieldError.getField(),
                    fieldError.getCode(),
                    fieldError.getDefaultMessage());
            fieldResources.add(fieldResource);
        }
        InvalidErrorResource invalidErrorResource=new InvalidErrorResource(e.getMessage(),fieldResources);

        ResponseEntity result=new ResponseEntity<Object>(invalidErrorResource, HttpStatus.BAD_REQUEST);
        logger.warn("-------------Return--------{}",result);
        return result;
    }

    /**
     * 处理全局异常
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<?>handleException(RuntimeException e){
        //只返回一个状态，服务器异常状态
        ResponseEntity result=new ResponseEntity<Object>( HttpStatus.INTERNAL_SERVER_ERROR);
        logger.error("----------Return--------{}",e);
        return result;
    }



}

