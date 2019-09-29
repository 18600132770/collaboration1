package com.huag.collaboration.exception;

/**
 * @author huag
 * @date 2019/9/26 15:24
 */
public class UserNotExistException extends RuntimeException{

    public UserNotExistException(){
        super("用户不存在！");
    }

}
