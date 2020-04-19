package com.zph.springbootapi.dto;

import com.zph.springbootapi.domain.Book;
import com.zph.springbootapi.util.CustomBeanUtils;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class BookDTO {

    @NotBlank
    private String author;
    @NotBlank
    private String name;
    @Length(max=20)
    private String description;
    @NotNull
    private Integer status;

    public BookDTO() {
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
    * 转换传输对象
    * @param book
    */
    public void convertToBook(Book book){
        new BookConvert().convert(this,book);
    }

    public Book convertToBook(){
       return new BookConvert().convert(this);
    }
    //内部聚合类
    private class  BookConvert implements convert<BookDTO , Book>
    {
        @Override
    public Book convert(BookDTO bookDTO, Book book) {
        String[] nullProprieties= CustomBeanUtils.getNullProprieties(bookDTO);
        //可以指定那些属性不被拷贝
        BeanUtils.copyProperties(bookDTO,book,nullProprieties);
        return book;
    }

    @Override
    public Book convert(BookDTO bookDTO) {
        Book book=new Book();
        BeanUtils.copyProperties(bookDTO,book);
        return book;
    }

    public BookConvert() {
    }
}
}
