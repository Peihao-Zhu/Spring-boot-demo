package com.zph.springbootadvanced.web;

import com.zph.springbootadvanced.domain.Book;
import com.zph.springbootadvanced.exception.BookNotFoundException;
import com.zph.springbootadvanced.service.BookService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {

    private  final Logger logger= LoggerFactory.getLogger(BookController.class);
    @Autowired
    private BookService bookService;

    @GetMapping("/")
    public String getBook(Model model){
        List<Book> books=bookService.getAll();

        model.addAttribute("book",books);
        return "book";
    }
    @GetMapping("/{id}")
    public String getBook(@PathVariable long id, Model model){
         Book book=bookService.getBookById(id);

         model.addAttribute("book",book);
         return "book";
    }

    @GetMapping("/input")
    public String input(Model model){
        model.addAttribute("book",new Book());
        return "input";
    }

    @PostMapping("/input")
    public String post( Book book,Model model){
        System.out.println(book.toString());
        Book book1=bookService.save(book);
        System.out.println(book1.toString());
        model.addAttribute("book",book1);
        return "book";
    }


}
