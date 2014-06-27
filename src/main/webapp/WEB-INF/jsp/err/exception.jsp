<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Error Happened!</title>
</head>
<body>
	This page occur some errors, Please contact the manager!
	<br /> Phone : 028-12345678

	<div style="display: none;">
		<c:out value="${errorMsg}"></c:out>
	</div>

</body>
</html>