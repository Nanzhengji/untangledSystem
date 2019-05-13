package com.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dao.DaoUtils;
import com.entities.Story;


public class AddRecommendServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DaoUtils daoUtils = new DaoUtils();
	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    request.setCharacterEncoding("UTF-8");
        HttpSession session=request.getSession();
        String sid=request.getParameter("id");
        int id=Integer.valueOf(sid);
       //查出该文章，设置到推荐表中
        Story story =daoUtils.findStoryByTitle(id);
        daoUtils.addRecommend(story.getStory_title(),story.getStory_content(),story.getClassify());
        List<String> ok=new ArrayList<String>();
        ok.add(story.getStory_title());
        request.setAttribute("ok",ok);
        List<Story> storys=daoUtils.findStoryByClassify("");
        session.setAttribute("storys", storys);
        request.getRequestDispatcher("/admin/showStory.jsp").forward(request, response);

	}

	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
