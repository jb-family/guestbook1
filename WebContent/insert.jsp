<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.javaex.dao.GuestBookDao" %>
<%@ page import="com.javaex.vo.GuestBookVo" %>
<%@ page import="java.util.List" %>

<%
	String name = request.getParameter("name");
	String password = request.getParameter("password");
	String content = request.getParameter("content");


	GuestBookVo guestBookVo = new GuestBookVo(name, password, content);
	System.out.println(guestBookVo);
	
	GuestBookDao guestBookDao = new GuestBookDao();
	
	int count = guestBookDao.guestBookInsert(guestBookVo);
	
	List<GuestBookVo> guestBookList = guestBookDao.guestBookList();
	
	System.out.println(guestBookList);
	
	response.sendRedirect("./addList.jsp");
%>

    
    
    