<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<!-- 最新版本的 Bootstrap 核心 CSS 文件 -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.9.1.min.js"></script>

<head>
	<script type="text/javascript">
		function checkForm(){
			 var story_title=$("#story_title").val();
			 var story_content=$("#story_content").val();			
			 if(story_title==""){
				 $("#error").html("标题不能为空！");
				 return false;
			 }
			 if(story_content==""){
				 $("#error").html("内容不能为空！");
				 return false;
			 }
			return true;
		}
	</script>
</head>

<body style="background:url(images/loginbg.jpg);">

<div class="panel panel-success" style="width:1000px;margin:0 auto;">
   <div class="panel-heading"><h2 >添加文章</h2></div>  
  <div class="panel-body" style="width:800px;">   
	<form method="post" action="${pageContext.request.contextPath}/addStory" onsubmit="return checkForm()" >
		<table>
			<tr>
				<td  style="font-size:18px;margin-buttom:20px;">标题：</td>
				<td><input class="text" type="text" id="story_title" name="story_title" style="font-size:16px;"/></td>
			</tr>	
			<tr>
			<td  style="font-size:18px;margin-buttom:20px;">分类：</td>
				<td>
				<select name="classify">
				  <option value ="明星八卦">明星八卦</option>
				  <option value ="热点新闻">热点新闻</option>
				  <option value="健康养生">健康养生</option>
				  <option value="搞笑趣闻">搞笑趣闻</option>
				</select>
				</td>
			</tr>				
			<tr>
				<td  style="font-size:18px;">内容：</td>
				<td><textarea rows="10" cols="50" id="story_content" name="story_content" style="font-size:16px;"></textarea></td>
			</tr>
			<tr>
				<td></td>			
				<td ><input class="btn btn-success" type="submit" name="submit" value="发布文章" style="font-size:18px;margin-top:10px;"/>
				<font id="error"  color="red">${error }</font>
				<% String ok=(String)request.getAttribute("ok");
					if(ok != null){
					%>
					<font color="red">发布成功，再来一篇...</font>
				<% 	}%>
				</td>
			</tr>
		</table>
	</form>
</div>  
</div>
	
</body>
</html>