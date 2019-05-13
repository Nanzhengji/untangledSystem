package com.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.DaoUtils;

public class AddStoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DaoUtils daoUtils = new DaoUtils();

	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}


	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    request.setCharacterEncoding("utf-8");
	    String classify=request.getParameter("classify");
	    String title=request.getParameter("story_title");
	    String content=request.getParameter("story_content");
	    daoUtils.addStory(title,content,classify);
        request.setAttribute("ok", "发布成功，再来一条");
        request.getRequestDispatcher("/admin/addStory.jsp").forward(request, response);
	}

}
