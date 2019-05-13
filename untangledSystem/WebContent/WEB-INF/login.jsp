<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>不纠结APP后台管理系统</title>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/login.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script type="text/javascript">
	function checkForm(){
		 var adminName=$("#adminName").val();
		 var password=$("#password").val();
		
		 if(adminName==""){
			 $("#error").html("用户名不能为空！");
			 return false;
		 }
		 if(password==""){
			 $("#error").html("密码不能为空！");
			 return false;
		 }
		
		 return true;
	}
</script>
</head>
<div id="app">
	<div class="login_warp">
		<div class="loginbox fl">
			<div class="login_header">
				<h3>不纠结APP后台管理系统</h3>
			</div>
			<form class="login_content" id="loginForm" method="post" action="${pageContext.request.contextPath}/login" onsubmit="return checkForm()">
				<div v-show="cur==0" class="Cbody_item">
					<div class="form_item"><input type="text" id="adminName" name="adminName"  placeholder="用户名"></div>
					<div class="form_item"><input type="password" id="password" name="password" placeholder="密码"></div>					
					<div class="clear"></div>
					<div class="form_item">
						<input type="submit" name="" value="登录">						
						<font id="error"  color="red">${error }</font>
						<%String message = (String) request.getAttribute("message");
						if(message != null){%>
						<font color="red"><%=message %></font>
					<%} %>
					</div>					
				</div>						
			</form>
		</div>
		<div class="loginrslider fl"></div>
	</div>
</div>
</body>
</html>