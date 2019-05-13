<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.entities.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head><!-- 最新版本的 Bootstrap 核心 CSS 文件 -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
</head>
<body style="background:url(images/loginbg.jpg);">

<div class="panel panel-success" style="width:1000px;margin:0 auto;">
  <!-- Default panel contents -->
  <div class="panel-heading"><h2>今日推荐</h2></div>  
  <div class="panel-body" style="width:800px;height:80px;">
	<form action="${pageContext.request.contextPath}/findRecommend" method="post" id="findForm">
		&nbsp;分类：&nbsp;<input type="text" name="rec_classify"  id="rec_classify"  size="20"/>&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="submit" class="btn btn-default" value="搜索"></input>
		</form>
</div> 
<table class="table table-bordered">    
    	<tr>    		
    		<th >分类</th>
    		<th >文章标题</th>
    		<th >文章内容</th>    		
    		<th >操作</th>
    	</tr> 
    <%List<RecommendInfo> recommends=(ArrayList)session.getAttribute("recommends");
    if(recommends != null){
        for(RecommendInfo recommendInfo:recommends){ %>
    	<tr>    		
    		<td ><%=recommendInfo.getClassify() %></td>
    		<td ><%=recommendInfo.getRec_title() %></td>
    		<td ><%=recommendInfo.getRec_content() %></td>
    		<td >   
    		<a class="btn btn-success"  onclick="deleteRecommend('<%=recommendInfo.getId()%>')">删除</a>  	   	
			</td>
    	</tr>
    <%  }
    } %>
   
</table>
</div>

</body>
<script type="text/javascript">
function deleteRecommend(id){
	confirm("您确定要删除吗？")
	window.location.href='/untangledSystem/deleteRecommend?id='+id;
}

</script>
</html>