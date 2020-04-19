package com.zph.springbootapi.api;

import com.zph.springbootapi.domain.Book;
import com.zph.springbootapi.dto.BookDTO;
import com.zph.springbootapi.exception.InvalidRequestException;
import com.zph.springbootapi.exception.NotFoundException;
import com.zph.springbootapi.service.BookService;
import com.zph.springbootapi.service.BookServiceImpl;
import com.zph.springbootapi.util.CustomBeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class BookApi {
    @Autowired
    private BookServiceImpl bookService;

    /**
     * 获取书单列表
     * @return
     */
    @GetMapping("/books")
    public ResponseEntity<?> findAllBooks(){
        List<Book> books=bookService.findAllBooks();
        if(books.isEmpty()){
            throw new NotFoundException("书单列表不存在");
        }
        System.out.println(books.toString());
        //显示状态吗，请求成功显示OK
        return new ResponseEntity<Object>(books, HttpStatus.OK);
    }

    /**
     * 根据id找书单
     * @param id
     * @return
     */
    @GetMapping("/books/{id}")
    public ResponseEntity<?> getBook(@PathVariable Long id) {
        Book book=bookService.getBookById(id);
        if(book==null){
            throw new NotFoundException(String.format("book by id %s not found",id));
        }
        System.out.println(book.toString());
        return new ResponseEntity<Object>(book, HttpStatus.OK);
    }

    /**
     * 新增一条书单
     * @param bookDTO
     * @return
     */
    @PostMapping("/books")
    //@RequestBody可以返回一个Json对象
    //ResponseEntity<?>是返回一个状态
    public ResponseEntity<?> saveBook(@Valid @RequestBody BookDTO bookDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new InvalidRequestException("Invalid parameter",bindingResult);
        }

        Book book1=bookService.saveBook(bookDTO.convertToBook());
        //新增有专门的状态 201 created
        return new ResponseEntity<Object>(book1, HttpStatus.CREATED);
    }

    /**
     * 更新一个书单
     * @param id
     * @param bookDTO
     * @return
     */
    @PutMapping("/books/{id}")
    public ResponseEntity<?> updateBook(@PathVariable Long id,@Valid @RequestBody BookDTO bookDTO, BindingResult bindingResult){

        Book currentBook=bookService.getBookById(id);
        if(currentBook==null){
            throw new NotFoundException(String.format("book by id %s not found",id));
        }
        if(bindingResult.hasErrors()){
            throw new InvalidRequestException("Invalid parameter",bindingResult);
        }

        //BeanUtils.copyProperties(bookDTO,currentBook);
        bookDTO.convertToBook(currentBook);
        System.out.println(currentBook.toString());
        Book book1=bookService.update(currentBook);
        return new ResponseEntity<Object>(book1, HttpStatus.OK);
    }


    /**
     * 删除一个书单
     * @param id
     * @return
     */
    @DeleteMapping("/books/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id){
        bookService.deleteBookById(id);
        //删除成功的状态
        return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
    }

    /**
     * 删除所有书单
     * @return
     */
    @DeleteMapping("/books")
    public ResponseEntity<?> deleteAllBooks(){
        bookService.deleteAllBooks();
        //删除成功的状态
        return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
    }

}
