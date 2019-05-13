package springmvc.views;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.View;

@Component
public  class MyView implements View{

    @Override
    public String getContentType() {

		return "text/html";
	}

	@Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		System.out.println("MyView");
		//������������,��message��Ϣ�������׿��ͨ��http�����ȡ
		response.setCharacterEncoding("utf-8");
		response.setHeader("Content-type", "text/html;charset=UTF-8");
		response.getWriter().println(request.getAttribute("message"));

	}

}





