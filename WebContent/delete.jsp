<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.javaex.dao.GuestBookDao" %>
<%@ page import="com.javaex.vo.GuestBookVo" %>
<%

	int no = Integer.parseInt(request.getParameter("no"));
	String password = request.getParameter("password");
	
	
	GuestBookVo guestBookVo = new GuestBookVo(no, password);
	
	
	GuestBookDao guestBookDao = new GuestBookDao();
	
	int count = guestBookDao.guestBookDelete(guestBookVo);
	System.out.println(count);
	
	System.out.println(guestBookDao.guestBookDelete(guestBookVo));
	
	response.sendRedirect("./addList.jsp"); 
	
%>