package com.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dao.DaoUtils;
import com.entities.Play;


public class FindPlayServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DaoUtils daoUtils= new DaoUtils();

	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    request.setCharacterEncoding("utf-8");
	    HttpSession session=request.getSession();
	    String title=request.getParameter("play_title");
        List<Play> plays=daoUtils.findPlayByTitle(title);
        session.setAttribute("plays", plays);

        request.getRequestDispatcher("admin/showPlay.jsp").forward(request, response);

	}

}
