package com.zph.springbootdemo.web;

import com.zph.springbootdemo.domain.Book;
import com.zph.springbootdemo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class BookController {

    @Autowired
    private BookService bookService;

    /**
     * 获取书单列表
     * @param model
     * @return
     */
    @GetMapping("/books")
    public String list(Model model,
                       @PageableDefault(size=5,sort = "id",direction = Sort.Direction.DESC) Pageable pageable
                       //会自动将page，size,sort等参数封装到Pageable中
                       //如果输入的page为-1等，Pageable会自动将其设为0。这样就能防止输入错误，产生报错
                       ){
        Page<Book> bookpage=bookService.findAllByPage(pageable);
        model.addAttribute("page",bookpage);
        return "books";
    }

    /**
     * 获取书单详情
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/books/{id}")
    public  String detail(@PathVariable long id, Model model){
        //获取一个对象
        Book book=bookService.getOne(id);
       if(book==null)
           book=new Book();
        //System.out.println(book.getId()+" "+book.getAuthor()+" "+book.getName()+" "+book.getDescription()+" "+book.getStatus());
        //把对象传到模版对象上
        model.addAttribute("book",book);
        return "book";
    }

    /**
     * 跳转页面
     * @return
     */
    @GetMapping("/books/input")
    public  String inputPage(Model model){
        model.addAttribute("book",new Book());
        return "input";
    }

    /**
     * 跳转到更新页面
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/books/input/{id}")
    public String inputEditPage(@PathVariable long id,Model model){
        Book book=bookService.getOne(id);
        model.addAttribute("book",book);
        return "input";
    }

    /**
     * 提交一个书单信息
     * @param book
     * @return
     */
    @PostMapping("/books")
    public String post(Book book, RedirectAttributes redirectAttributes) {
        Book book1 = bookService.postOne(book);
        if(book1 != null) {
            redirectAttributes.addFlashAttribute("message","《"+book1.getName()+"》获取成功");
    }
        //redirect
        return "redirect:/books";
    }

    /**
     * POST --->redirect--->GET
     * model只能在一个request中起作用，但是redirect会前后各产生一次request
     * 那么在post中添加的信息，在get的model是找不到的
     *
     */

    @GetMapping("/books/delete/{id}")
    public String delete(@PathVariable long id,
                         final RedirectAttributes redirectAttributes){
        bookService.deleteOne(id);
        redirectAttributes.addFlashAttribute("message","删除成功");
        return "redirect:/books";
    }
}
