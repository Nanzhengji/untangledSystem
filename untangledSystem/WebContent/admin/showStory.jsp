<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="java.util.*" %>
    <%@ page import="com.entities.Story" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head><!-- 最新版本的 Bootstrap 核心 CSS 文件 -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
</head>
</head>
<body style="background:url(images/loginbg.jpg);">

<div class="panel panel-success" style="width:1000px;margin:0 auto;">
  <div class="panel-heading"><h2>精选文章</h2></div>  
  <div class="panel-body" style="width:800px;height:80px;">
	<form class="input-group" action="${pageContext.request.contextPath}/findStory" method="post">
		&nbsp;文章类别：&nbsp;<input type="text" name="story_classify"  id="story_classify"  size="20"/>&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="submit"  class="btn btn-default"  value="搜索"></input>
		</form>
</div> 
 <table class="table table-bordered">    
    	<tr>    		
    		<th >分类</th>
    		<th  >文章标题</th>
    		<th >文章内容</th>
    		<th >发布时间</th>
    		<th >操作</th>
    	</tr>   
    <% List<Story> storys=(ArrayList)session.getAttribute("storys");
       List<String> ok= (ArrayList)request.getAttribute("ok");
       if(storys != null && storys.size() != 0){
        for(Story story:storys){%>
    	<tr>    		
    		<td ><%=story.getClassify() %></td>
    		<td><%=story.getStory_title() %></td>
    		<td ><%=story.getStory_content() %></td>
    		<td ><%=story.getCreate_time() %></td>
    		<td >
    		<% if(ok != null){
			    for(int i=0;i<ok.size();i++){
			if(ok.get(i).equals(story.getStory_title())){%>
				<span class="btn btn-success">今日推荐</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<% }else{%>
				<a id="a1" class="btn btn-success" onclick="addRecommend('<%=story.getId()%>')">设为今日推荐</a>&nbsp;&nbsp;&nbsp;&nbsp;
				<% }
				}
				}else{%>
				<a id="a2" class="btn btn-success" onclick="addRecommend('<%=story.getId()%>')">设为今日推荐</a>&nbsp;&nbsp;&nbsp;&nbsp;    		
    	<%} %>    		
    		<a id="a3" class="btn btn-success"  onclick="delStory('<%=story.getId()%>')">删除</a>
			</td>
    	</tr>
    <%  }
    } %>
   
</table>
</div>

</body>
<script type="text/javascript">
	 function delStory(id){
		//先弹窗提示一下，再执行删除
		confirm("确定要删除吗？");
		window.location.href='/untangledSystem/deleteStory?id='+id;		
	}

	function addRecommend(id){
		confirm("要设为今日推荐吗？");
		window.location.href='/untangledSystem/addRecommend?id='+id;
	} 	
	
</script>
</html>