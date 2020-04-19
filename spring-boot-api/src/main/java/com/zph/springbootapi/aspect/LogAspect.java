package com.zph.springbootapi.aspect;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Aspect
@Component
public class LogAspect {
    //在controller设计一个切面，来记录请求数据和返回数据

    private final Logger logger= LoggerFactory.getLogger(this.toString());

    @Pointcut("execution(* com.zph.springbootapi.api.*.*(..))")
    public void log(){

    }

    @Before("log()")
    public void doBefore(JoinPoint joinPoint){
        ServletRequestAttributes attributes= (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request=attributes.getRequest();
        //获得类名和类方法
        String classMethod=joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName();
        RequestLog requestLog=new RequestLog(
                request.getRequestURI(),
                request.getRemoteAddr(),
                classMethod,
                joinPoint.getArgs()
        );
        //System.out.println(requestLog.toString());
        logger.info("--------Request----------{}",requestLog);
    }

    @After("log()")
    public void doAfter(){
        //logger.info("--------doAfter 2----------");
    }

    @AfterReturning(returning = "result",pointcut = "log()")
    public void doAfterReturning(Object result){

        logger.info("--------Return ----------{}",result);
    }

    @AfterThrowing(pointcut = "log()", throwing="ex")
    public void doAfterThrowing(Exception ex){
        //logger.info("--------doAfterThrowing ----------  :{}",ex.getMessage());
    }
//    @Around("log()")
//    public void doAround(){
//        //logger.info("--------doAround ----------  ");
//    }

    private class RequestLog{
        private String url;
        private String ip;
        private String classMethod;
        private Object[] args;

        public RequestLog(String url, String ip, String classMethod, Object[] args) {
            this.url = url;
            this.ip = ip;
            this.classMethod = classMethod;
            this.args = args;
        }

        @Override
        public String toString() {
            return "RequestLog{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }

}
