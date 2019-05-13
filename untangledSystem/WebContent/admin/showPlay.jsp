<%@ page language="java" pageEncoding="UTF-8"%>
    <%@ page import="java.util.*" %>
    <%@ page import="com.entities.Play" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head><!-- 最新版本的 Bootstrap 核心 CSS 文件 -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
</head>
<body style="background:url(images/loginbg.jpg);">

<div class="panel panel-success" style="width:1000px;margin:0 auto;">
  <!-- Default panel contents -->
  <div class="panel-heading"><h2>纠结记录</h2></div>  
  <div class="panel-body" style="width:800px;height:80px;">
	<form class="input-group" action="${pageContext.request.contextPath}/findPlay" method="post" id="findForm">
		&nbsp;纠结事件：&nbsp;<input  type="text" name="play_title"  id="play_title"  size="20"/>&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="submit"  class="btn btn-default"  value="搜索"></input>
		</form>
</div> 
 <table  class="table table-bordered" > 
 	<tr><td>纠结事件</td></tr>    
    <%List<Play> plays=(ArrayList)session.getAttribute("plays");
    if(plays != null){
        for(Play play:plays){ %>
    	<tr>    		
    		<td  ><%=play.getPlay_title() %></td>
    	</tr>
    <% } }else{ %>
    <tr><td>暂无用户发表纠结记录</td></tr>
    <%} %>
</table>
</div>

</body>
</html>