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
import com.entities.RecommendInfo;
import com.entities.Story;


public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DaoUtils daoUtils= new DaoUtils();
	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    HttpSession session = request.getSession();
        //登录成功，查出用户数量
        long user_count =daoUtils.countUser();
        request.setAttribute("user_count", user_count);
        //查出纠结记录
        List<Play> plays =daoUtils.findPlays();
        session.setAttribute("plays", plays);
        //查出所有的文章
        List<Story> storys=daoUtils.findStorys();
        session.setAttribute("storys", storys);
        //查出所有的推荐内容
        List<RecommendInfo> recommends=daoUtils.findRecommends();
        session.setAttribute("recommends", recommends);
        request.getRequestDispatcher("admin/index.html").forward(request, response);
	}

	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
