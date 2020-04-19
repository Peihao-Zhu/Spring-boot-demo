package com.zph.springbootapi.service;

import com.zph.springbootapi.domain.Book;

import java.util.List;

public interface BookService {


    List<Book> findAllBooks();

    Book getBookById(Long id);

    Book saveBook(Book book);

    Book update(Book book);

    void deleteBookById(Long id);

    void  deleteAllBooks();
}
