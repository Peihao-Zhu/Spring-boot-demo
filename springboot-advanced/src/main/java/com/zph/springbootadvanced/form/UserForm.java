package com.zph.springbootadvanced.form;

import com.zph.springbootadvanced.domain.User;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.BeanUtils;

import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;


public class UserForm {

    //正则表达式
    public static final String PHONE_REG= "^[1](([3][0-9])|([4][5-9])|([5][0-3,5-9])|([6][5,6])|([7][0-8])|([8][0-9])|([9][1,8,9]))[0-9]{8}$";

    @NotBlank
    private String username;
    @Length(min=6,message = "密码至少需要6位")
    private String password;
    @Email
    private String email;
    @Pattern(regexp = PHONE_REG,message = "请输入正确的手机号")
    private String phone;
    @NotBlank
    private String confirmPassword;

    public UserForm() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public boolean confirmPassword(){
        if(this.password.equals(this.confirmPassword))
            return true;
        return false;
    }

    public User convertToUser(){
        User user=new UserFormConvert().convert(this);
        return user;
    }

    private class UserFormConvert implements FormConvert<UserForm, User>{
        @Override
        public User convert(UserForm userForm) {
            User user=new User();
            BeanUtils.copyProperties(userForm,user);
            return user;
        }
    }

}
