<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Result ...</title>
<style type="text/css">
.formContent {
	text-align: center;
	margin: 0px auto;
}
</style>
</head>
<body>
	<%@include file="./inc/header.jsp"%>
	<fieldset>
		<div class="formContent">
			<table border="1" style="width: 400px;">
				<thead>
					<tr>
						<td>ID</td>
						<td>用户名</td>
						<td>密码</td>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="usr" items="${userLst }" varStatus="status">
						<tr <c:if test="${status.count%2==0}">bgcolor="#CCCCFE"</c:if>>
							<td>${usr.id }</td>
							<td>${usr.name }</td>
							<td>${usr.pass }</td>
						</tr>

						<c:forEach var="area" items="${usr.areaLst }" varStatus="loop">
							<tr>
								<td>---></td>
								<td colspan="2">${area.areaDesc }</td>
							</tr>
						</c:forEach>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</fieldset>
	<%@include file="./inc/footer.jsp"%>
</body>
</html>