package com.zph.springbootadvanced.handler;

import com.zph.springbootadvanced.web.BookController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

//拦截器，这个注解很关键
@ControllerAdvice
public class ControllerExceptionHandler {
    private  final Logger logger= LoggerFactory.getLogger(BookController.class);
    //@ExceptionHandler({Exception.class,BookNotFoundException.class})

    /**
     * 异常处理
     * @param request
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler({Exception.class})
    public ModelAndView handleException(HttpServletRequest request, Exception e) throws Exception {
        logger.error("Request:URL:{} , Exception:{}",request.getRequestURL(),e.getMessage());

        //指定状态码的异常，单独处理
        if(AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class)!=null){
            throw e;
        }
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.addObject("url",request.getRequestURL());
        modelAndView.addObject("exception",e);
        modelAndView.setViewName("error/error");
        return modelAndView;
    }

}
