package com.zph.springbootdemo.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book,Long> {

    //进行分页
    Page<Book>findAll(Pageable pageable);

    List<Book> findByAuthor(String author);

    List<Book> findByAuthorAndStatus(String author,int status);

    List<Book> findByDescriptionEndsWith(String des);

    // Book是实体类的名称 ?是占位符，后面的1是下面函数的第一个参数
    //@Query("select b from Book b where length(b.name) >?1 ")
    @Query(value = "select * from book where LENGTH(name)>?1",nativeQuery =true)
    List<Book> findByJPQL(int len);

    @Modifying
    @Query("update Book b set b.status=?1 where b.id=?2")
    int updateByJPQL(int statue, long id);

    @Modifying
    @Query("delete from Book b where b.id=?1")
    int deleteByJPQL( long id);
}
