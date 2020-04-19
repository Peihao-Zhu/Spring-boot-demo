package com.zph.springbootadvanced.dao;

import com.zph.springbootadvanced.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book,Long> {
}
