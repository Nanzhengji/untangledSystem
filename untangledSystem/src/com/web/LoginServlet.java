package com.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
	}

	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String name=request.getParameter("adminName");
	    String password=request.getParameter("password");

	    if ("admin".equals(name) && "admin".equals(password)) {
	        response.sendRedirect("/untangledSystem/home.php");

	    } else {
	        String message = "用户名或密码错误";
	        request.setAttribute("message", message);
	        request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
	    }
	}

}
