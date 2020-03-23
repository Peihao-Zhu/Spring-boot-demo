package com.zph.springbootdemo.service;

import com.zph.springbootdemo.domain.Book;
import com.zph.springbootdemo.domain.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    /**
     * 获取所有书单列表
     * @return
     */
    public List<Book> findAll(){

        return bookRepository.findAll();
    }

    /**
     * 分页查询所有书单
     * @return
     */
    public Page<Book> findAllByPage(Pageable pageable){

        return bookRepository.findAll(pageable);
    }

    /**
     * 新增/更新 一个书单信息 根据book的id在原数据库中是否存在
     * @param book
     * @return
     */
    public Book postOne(Book book){

        return bookRepository.save(book);
    }

    /**
     * 获取一条读书清单
     * @return
     */
    public Book getOne(long id){
        //System.out.println(id);
        //System.out.println(bookRepository.getOne(id));
        Optional<Book> book=bookRepository.findById(id);

        if(book.isPresent())
            return book.get();
        else
            return null;
    }

    /**
     * 删除一个书单
     * @param id
     */
    public void deleteOne(long id){
        bookRepository.deleteById(id);
    }

    /**
     * 返回作者的书
     * @param author
     * @return
     */
    public List<Book> findByAuthor(String author){

        return bookRepository.findByAuthor(author);
    }

    /**
     * 根据作者和状态返回书单
     * @param author
     * @param status
     * @return
     */
    public List<Book> findByAuthorAndStatus(String author,int status){

        return bookRepository.findByAuthorAndStatus(author,status);
    }

    public List<Book> findByDescriptionEndsWith(String des){
        return bookRepository.findByDescriptionEndsWith(des);
    }

    public  List<Book> findByJPQL(int len){
        return bookRepository.findByJPQL(len);
    }

    /**
     * 根据id 更新它的状态
     * @param statue
     * @param id
     * @return
     */
    @Transactional
    public  int updateByJPQL(int statue, long id){
        return bookRepository.updateByJPQL(statue,id);
    }

    /**
     * 根据id 先删除,在更新
     * @param id
     * @param status
     * @return
     */
    @Transactional
    public int deleteAndUpdate(long id,int status,long uid){

        int dcount=bookRepository.deleteByJPQL(id);

        int ucount=bookRepository.updateByJPQL(status,uid);
        return dcount+ucount;
    }
}
