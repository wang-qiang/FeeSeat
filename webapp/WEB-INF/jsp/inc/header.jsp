<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="resources/js/jquery-1.7.2.js"></script>
<script type="text/javascript" src="resources/js/jquery.Jcrop.min.js"></script>
<link type="text/css" rel="stylesheet"
    href="resources/css/jquery.Jcrop.css" />
<script type="text/javascript" src="resources/js/ajaxfileupload.js"></script>
<link type="text/css" rel="stylesheet"
    href="resources/css/ajaxfileupload.css" />
</head>
<body>
	<fieldset>
		<div style="height: 50px; background: #eee;">
			header...<a href="<%=path%>/logout" style="text-align: right;">退出登录</a>
		</div>
	</fieldset>
</body>
</html>