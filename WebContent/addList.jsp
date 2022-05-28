<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.javaex.dao.GuestBookDao" %>
<%@ page import="com.javaex.vo.GuestBookVo" %>
<%@ page import="java.util.List" %>

<%


GuestBookDao guestBookDao = new GuestBookDao();


List<GuestBookVo> guestBookList = guestBookDao.guestBookList();




%>


<!DOCTYPE html>
<html>
	<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
</head>
<body>
	<form action="add.jsp" method="get">
		<table border="1">
			<tr>
				<td>이름<input type="text" name="name"></td>
				<td>비밀번호<input type="password" name="password"></td>	
			</tr>
			<tr>
				<td colspan="2"><textarea name="content" style="width:500px; height:200px;"></textarea></td>
			</tr>
			<tr>
				<td colspan="2"><button type="submit">확인</button></td>
			</tr>
		</table>
		<br>
	</form>
	
	<% for(int i = 0; i < guestBookList.size(); i++ ) { %>
		<table border="1">
				<tr>
					<td style="width:30px;"><%= guestBookList.get(i).getNo() %></td>
					<td style="width:100px;"><%= guestBookList.get(i).getName() %></td>
					<td style="width:270px;"><%= guestBookList.get(i).getRegDate() %></td>	
					<td style="width:100px;"><a href="deleteForm.jsp?no=<%= guestBookList.get(i).getNo() %>">[삭제]</a></td>	
				</tr>
				<tr>
					<td rowspan="2" colspan="4">첫번째 방명록내용<br>
						<%= guestBookList.get(i).getContent() %>
					</td>	
			</table>
			<br>
	<% } %>
	

						
</body>
</html>