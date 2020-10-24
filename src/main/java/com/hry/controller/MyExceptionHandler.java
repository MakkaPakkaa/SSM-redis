package com.hry.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

//使用 @ControllerAdvice 实现全局异常处理，只需要定义类
@ControllerAdvice
public class MyExceptionHandler {
	//@ExceptionHandler 注解用来指明异常的处理类型
	@ExceptionHandler(Exception.class)
	public String handlException(Exception ex ,Model model) {
		model.addAttribute("exception", ex);
		return "error";
	}
}
