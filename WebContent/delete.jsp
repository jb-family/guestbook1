<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.javaex.dao.GuestBookDao" %>
<%@ page import="com.javaex.vo.GuestBookVo" %>
<%

	int no = Integer.parseInt(request.getParameter("no"));
	String password = request.getParameter("password");
	
	
	GuestBookDao guestBookDao = new GuestBookDao();
	GuestBookVo guestBookVo = guestBookDao.guestBookList(no); 
	
	System.out.println("guestBookVo.getPassword() : " + guestBookVo.getPassword());
	
	if(guestBookVo.getPassword().equals(password)) {
		
	guestBookDao.guestBookDelete(guestBookVo);
	response.sendRedirect("./addList.jsp");
	}else {
		
	response.sendRedirect("./addList.jsp"); 
	}
	
	
	
%>