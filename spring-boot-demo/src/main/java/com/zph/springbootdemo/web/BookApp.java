package com.zph.springbootdemo.web;

import com.zph.springbootdemo.domain.Book;
import com.zph.springbootdemo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class BookApp {

    @Autowired
    private BookService bookService;

    /**
     * 获取书单清单
     * @return
     */
    @GetMapping("/books")
    public Page<Book> getAll(@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "5") int size){
        Sort sort=Sort.by(Sort.Direction.DESC,"id");
        Pageable pageable= PageRequest.of(page,size,sort);
        //return bookService.findAll();
        return bookService.findAllByPage(pageable);
    }

    /**
     * 新增一个书单
     * @return
     */
    @PostMapping("/books")
    public Book post(Book book){
        return bookService.postOne(book);
    }

    /**
     * 获取一条书单信息
     * @param id
     * @return
     */
    @GetMapping("/books/{id}")
    public Book getOne(@PathVariable long id){
        //System.out.println(id);
        Book book=bookService.getOne(id);
        //System.out.println(book.getId()+" "+book.getAuthor()+" "+book.getName()+" "+book.getDescription()+" "+book.getStatus());
        return book;
    }

    /**
     * 更新一个书单
     * @return
     */
    @PutMapping("/books")
    public Book update(Book book){
        return bookService.postOne(book);
    }

    /**
     * 根据id 删除一个表单
     * @param id
     */
    @DeleteMapping("/books/{id}")
    public void deleteOne(@PathVariable long id){
        bookService.deleteOne(id);
    }

    /**
     * 根据作者姓名和书本状态查询
     // * @param author
     //* @param status
     * @return
     */
    //@PostMapping("/books/by")
    //public List<Book> findByAuthor(@RequestParam String author,@RequestParam int status){
        //return  bookService.findByAuthor(author);
    //    return bookService.findByAuthorAndStatus(author,status);
    //}

    /*
    @PostMapping("/books/by")
    public List<Book> findBy(@RequestParam int len){
        return bookService.findByJPQL(len);
    }

*/

    @PostMapping("/books/by")
    public  int updateBy(@RequestParam long id,@RequestParam int status,@RequestParam long uid){
        return bookService.deleteAndUpdate(id,status,uid);
    }
}
