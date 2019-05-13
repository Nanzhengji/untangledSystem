package com.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dao.DaoUtils;
import com.entities.RecommendInfo;

/**
 * Servlet implementation class DeleteRecommendServlet
 */
public class DeleteRecommendServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DaoUtils daoUtils = new DaoUtils();
	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    request.setCharacterEncoding("utf-8");
        String sid=request.getParameter("id");
        HttpSession session=request.getSession();
        int id =Integer.valueOf(sid);
	    daoUtils.deleteRecByTitle(id);
	    List<RecommendInfo> recommends=daoUtils.findRecommendByClassify("");
        session.setAttribute("recommends", recommends);

        request.getRequestDispatcher("admin/recommend.jsp").forward(request, response);

	}

	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
