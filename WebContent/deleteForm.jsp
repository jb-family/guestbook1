<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.javaex.dao.GuestBookDao" %>
<%@ page import="com.javaex.vo.GuestBookVo" %>
<%@ page import="java.util.List" %>

<%
	int no = Integer.parseInt(request.getParameter("no"));

%>



<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
</head>
<body>
	
	<form method="get" action="./delete.jsp">
	비밀번호 <input type="password" name ="password"><button type="submit">확인</button>
	<input type="hidden" name="no" value= "<%= no %>">
	</form>
	<a href="./addList.jsp">메인으로 돌아가기</a>
</body>
</html>