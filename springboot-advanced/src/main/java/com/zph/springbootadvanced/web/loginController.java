package com.zph.springbootadvanced.web;


import com.zph.springbootadvanced.dao.UserRepository;
import com.zph.springbootadvanced.domain.User;
import com.zph.springbootadvanced.exception.BookNotFoundException;
import com.zph.springbootadvanced.form.UserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
public class loginController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("userForm",new UserForm());
        return "register";
    }

    /**
     * 登陆页面跳转
     * @return
     */
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    /**
     *
     * @param username
     * @param password
     * @param httpSession
     * @return
     */
    @PostMapping("/login")
    public String loginPost(@RequestParam String username,
                            @RequestParam String password,
                            HttpSession httpSession){
        User user=userRepository.findUserByUsernameAndPassword(username,password);
        if(user!=null){
            httpSession.setAttribute("user",user);
            return "index";
        }
        return "login";

    }
    @PostMapping("/register")
    public String registerPost(@Valid UserForm userForm, BindingResult result) {
        //已经将userForm注入model中去了
        if(!userForm.confirmPassword()){
            result.rejectValue("confirmPassword","confirmError","两次密码不一致");
        }
        if(result.hasErrors()) {
            return "register";
        }
        User user=userForm.convertToUser();
        userRepository.save(user);
        return "redirect:/login";
    }


    @GetMapping("/exception")
    public String testException(){
        throw new BookNotFoundException("自定义异常");
        //return "/error/404";
    }



    @GetMapping("/logout")
    public String logout(HttpSession session)
    {
        session.removeAttribute("user");
        return "login";
    }
}
