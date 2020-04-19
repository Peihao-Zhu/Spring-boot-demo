package com.zph.springbootadvanced.service;

import com.zph.springbootadvanced.dao.BookRepository;
import com.zph.springbootadvanced.domain.Book;
import com.zph.springbootadvanced.exception.BookNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;

import java.security.PublicKey;
import java.util.List;
import java.util.Optional;

    @Service
    public class BookService {

        @Autowired
        private  BookRepository bookRepository;

        public List<Book> getAll(){
            return bookRepository.findAll();
        }
        /**
         * 通过id查找书本
         * @param id
         * @return
         */
        public Book getBookById(long id){
            Optional<Book> book=bookRepository.findById(id);
            return book.get();
//        if(book.isPresent())
//             return  book.get();
//        else{
//            return null;
//        }

        }

        public Book save(Book book){
            return bookRepository.save(book);
        }
}
