package com.zph.springbootdemo.web;

import com.zph.springbootdemo.domain.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@Controller
@RestController
@RequestMapping("/v1")
public class HelloController {



    private Book book;

    //@RequestMapping(value = "/say",method = RequestMethod.GET)
    @GetMapping("/say")
    public String hello(){
        return "Hello Spring Boot";
    }

    @GetMapping("/books")
    //@ResponseBody
    public Object getAll(@RequestParam int page,@RequestParam(defaultValue = "10") int size){
        Map<String,Object> book=new HashMap<>();
        book.put("name","nihao");
        book.put("author","傻子");

        List<Map>contents=new ArrayList<>();
        contents.add(book);

        Map<String,Object>pagemap=new HashMap<>();
        pagemap.put("page",page);
        pagemap.put("size",size);
        pagemap.put("content",contents);

        return pagemap;
    }

    /**
     * 正则表达式:{参数名:正则表达式}
     * @param id
     * @return
     */
    //@GetMapping("/books/{id}/{username:[a-z_]+}")
    @GetMapping("/books/{id}")
    public Object getOne(@PathVariable long id){
        System.out.println("----id:"+id);
        return null;
    }

    //URL是post命令
    @PostMapping("/books")
    public Object post(@RequestParam String name,
                       @RequestParam String author){
        Map<String,Object> book=new HashMap<>();
        book.put("name",name);
        book.put("author",author);
        return book;
    }
}
