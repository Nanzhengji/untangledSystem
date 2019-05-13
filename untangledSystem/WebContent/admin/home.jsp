<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>不纠结APP后台管理系统</title>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>

<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
</head>
<body style="background:url(images/loginbg.jpg);">

<div class="panel panel-success" style="width:1000px;margin:0 auto;">
  <!-- Default panel contents -->
  <div class="panel-heading"><h2>注册用户量</h2></div>  
  <div class="panel-body" style="width:800px;height:80px;">
  <div style="text-align: center;">
	<% Long user_count =(Long)request.getAttribute("user_count");
			if(user_count != null && user_count != 0){%>
				<span style="text-align: center;">注册用户量：&nbsp;&nbsp;<strong>${user_count}</strong></span>
				<%}else{ %>
				<span style="text-align: center;">注册用户量：&nbsp;&nbsp;<strong>0</strong></span>
				<%} %>
				</div>
</div> 
 </div>
</body>
</html>


			
